package com.epam.esm.controlles;

import com.epam.esm.model.impl.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/database-data-initialization.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/database-data-initialization.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TagControllerTest {
    private static final String URL_GET_TAG_BY_ID = "/api/tags/1";
    private static final String RESULT_GET_TAG_BY_ID = "{\"id\":1,\"name\":\"tag1\"}";

    private static final String URL_GET_ALL_TAGS = "/api/tags/?page=0&size=10";
    private static final String RESULT_GET_ALL_TAGS = "{\"size\":10," +
            "\"totalElements\":4," +
            "\"totalPages\":1," +
            "\"number\":0," +
            "\"items\":[" +
            "{\"id\":1," +
            "\"name\":\"tag1\"," +
            "\"links\":" +
            "[{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/1\"}," +
            "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/1\"}]}," +
            "{\"id\":2," +
            "\"name\":\"tag2\"," +
            "\"links\":" +
            "[{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/2\"}," +
            "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/2\"}]}," +
            "{\"id\":3," +
            "\"name\":\"tag3\"," +
            "\"links\":" +
            "[{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/3\"}," +
            "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/3\"}]}," +
            "{\"id\":4," +
            "\"name\":\"tag4\"," +
            "\"links\":" +
            "[{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/4\"}," +
            "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/4\"}]}]}";

    private static final String URL_ADD_TAG = "/api/tags/";
    private static final String RESULT_ADD_TAG_INCORRECT_NAME_ERROR_CODE = "\"errorCode\":\"Error: 0005\"";

    private static final String URL_POSITIVE_DELETE_TAG_BY_ID = "/api/tags/4";
    private static final String RESULT_POSITIVE_DELETE_TAG_BY_ID_ERROR_CODE = "\"errorCode\":\"Error: 0002\"";

    private static final String URL_NEGATIVE_DELETE_TAG_BY_ID_TAG_USED_IN_GIFT = "/api/tags/1";
    private static final String RESULT_NEGATIVE_DELETE_TAG_BY_ID_TAG_USED_IN_GIFT_ERROR_CODE =
            "\"errorCode\":\"Error: 0003\"";

    private static final String INPUT_DATA_CORRECT_TAG_NAME = "TagAddTest";
    private static final String INPUT_DATA_INCORRECT_TAG_NAME = "TagAddTest.";

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
        this.mockMvc.perform(get(URL_GET_TAG_BY_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(RESULT_GET_TAG_BY_ID))
                .andReturn();
    }

    @Test
    public void findAllRequestTest() throws Exception {
        this.mockMvc.perform(get(URL_GET_ALL_TAGS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(Object.class)))
                .andExpect(content().json(RESULT_GET_ALL_TAGS))
                .andReturn();
    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addTagPositiveTest() throws Exception {
        Tag tag = generateTag();
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post(URL_ADD_TAG)
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
    public void addTagTagNameIsNotCorrectTest() throws Exception {
        Tag tag = generateIncorrectTag();
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post(URL_ADD_TAG)
                        .content(objectMapper.writeValueAsString(tag))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(RESULT_ADD_TAG_INCORRECT_NAME_ERROR_CODE)));
    }


    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTagByIdPositiveTest() throws Exception {
        this.mockMvc.perform(delete(URL_POSITIVE_DELETE_TAG_BY_ID))
                .andDo(print());
        this.mockMvc.perform(get(URL_POSITIVE_DELETE_TAG_BY_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(RESULT_POSITIVE_DELETE_TAG_BY_ID_ERROR_CODE)))
                .andReturn();
    }

    @Test
    public void deleteTagByIdNegativeTest() throws Exception {
        this.mockMvc.perform(delete(URL_NEGATIVE_DELETE_TAG_BY_ID_TAG_USED_IN_GIFT))
                .andDo(print())
                .andExpect(
                        content().string(containsString(RESULT_NEGATIVE_DELETE_TAG_BY_ID_TAG_USED_IN_GIFT_ERROR_CODE)));
    }

    private Tag generateTag() {
        Tag tag = new Tag();
        tag.setName(INPUT_DATA_CORRECT_TAG_NAME);
        return tag;
    }

    private Tag generateIncorrectTag() {
        Tag tag = new Tag();
        tag.setName(INPUT_DATA_INCORRECT_TAG_NAME);
        return tag;
    }
}
