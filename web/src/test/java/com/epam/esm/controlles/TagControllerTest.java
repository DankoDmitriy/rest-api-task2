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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@TestPropertySource("/mysql-test.properties")
@WebAppConfiguration
/*TODO - Read about this annotation in tests - in spring boot.
 *https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/boot-features-testing.html
 */
//@Transactional
public class TagControllerTest {
    private static final String SINGLE_TAG_RESULT = "{\"id\":1,\"name\":\"tag1\"}";
    private static final String SINGLE_TAG_REQUEST_URL = "/tags/1";
    private static final String LIST_TAGS_RESULT = "[{\"id\":1,\"name\":\"tag1\"},{\"id\":2,\"name\":\"tag2\"},{\"id\":3,\"name\":\"tag3\"}]";
    private static final String LIST_TAGS_REQUEST_URL = "/tags";
    private static final String ADD_TAG_REQUEST_URL = "/tags";

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
    @Sql(value = "/create-tags-after.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/create-tags-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addTagRequestTest() throws Exception {
        Tag tag = new Tag();
        tag.setName("TagAddTest");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post(ADD_TAG_REQUEST_URL)
                        .content(objectMapper.writeValueAsString(tag))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(tag.getName())));
    }
}
