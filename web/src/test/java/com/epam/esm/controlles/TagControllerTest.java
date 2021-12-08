package com.epam.esm.controlles;

import com.epam.esm.configs.SpringConfig;
import com.epam.esm.impl.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@TestPropertySource("/mysql-test.properties")
@WebAppConfiguration
@Sql(value = {"/database-data-initialization.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/database-data-initialization.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TagControllerTest {
    private static final String SINGLE_TAG_RESULT = "{\"id\":1,\"name\":\"tag1\"}";
    private static final String SINGLE_TAG_REQUEST_URL = "/api/tags/1";
    private static final String LIST_TAGS_RESULT = "[{\"id\":1,\"name\":\"tag1\"},{\"id\":2,\"name\":\"tag2\"},"
            + "{\"id\":3,\"name\":\"tag3\"}]";
    private static final String LIST_TAGS_REQUEST_URL = "/api/tags/";

    private static final String ADD_TAG_REQUEST_URL = "/api/tags/";
    private static final String ADD_ERROR_CODE = "\"errorCode\":\"Error: 0001\"";

    private static final String DELETE_TAG_BY_ID_REQUEST_URL = "/api/tags/3";
    private static final String DELETE_TAG_BY_ID_GET_TAG_REQUEST_URL = "/api/tags/3";
    private static final String DELETE_ERROR_CODE = "\"errorCode\":\"Error: 0002\"";

    private static final String TEST_TAG_NAME = "TagAddTest";
    private static final String TEST_INCORRECT_TAG_NAME = "TagAddTest.";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void CreatingApplicationContextAndControllersTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("tagController"));
    }

    @Test
    public void findByIdRequestTest() throws Exception {
        this.mockMvc.perform(get(SINGLE_TAG_REQUEST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(SINGLE_TAG_RESULT))
                .andReturn();
    }

    @Test
    public void findAllRequestTest() throws Exception {
        this.mockMvc.perform(get(LIST_TAGS_REQUEST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(content().json(LIST_TAGS_RESULT))
                .andReturn();
    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addTagRequestTest() throws Exception {
        Tag tag = generateTag();
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post(ADD_TAG_REQUEST_URL)
                        .content(objectMapper.writeValueAsString(tag))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(tag.getName())));
    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addTagRequestNegativeTest() throws Exception {
        Tag tag = generateIncorrectTag();
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post(ADD_TAG_REQUEST_URL)
                        .content(objectMapper.writeValueAsString(tag))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(ADD_ERROR_CODE)));
    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTagByIdTest() throws Exception {
        this.mockMvc.perform(delete(DELETE_TAG_BY_ID_REQUEST_URL))
                .andDo(print());
        this.mockMvc.perform(get(DELETE_TAG_BY_ID_GET_TAG_REQUEST_URL))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(DELETE_ERROR_CODE)))
                .andReturn();
    }

    private Tag generateTag() {
        Tag tag = new Tag();
        tag.setName(TEST_TAG_NAME);
        return tag;
    }

    private Tag generateIncorrectTag() {
        Tag tag = new Tag();
        tag.setName(TEST_INCORRECT_TAG_NAME);
        return tag;
    }
}
