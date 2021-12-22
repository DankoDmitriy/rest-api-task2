package com.epam.esm.controlles;

import com.epam.esm.data_provider.GiftCertificateDataProvider;
import com.epam.esm.data_provider.UserDataProvider;
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
import java.util.Map;

import static com.epam.esm.data_provider.UserDataProvider.RESULT;
import static com.epam.esm.data_provider.UserDataProvider.RESULT_SIZE;
import static com.epam.esm.data_provider.UserDataProvider.URL_REQUEST;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final UserDataProvider provider = new UserDataProvider();

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
    public void getUserByIdPositiveTest() throws Exception {
        Map<String, String> dataForTest = provider.getUserByIdPositiveTest();
        this.mockMvc.perform(get(dataForTest.get(GiftCertificateDataProvider.URL_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(dataForTest.get(GiftCertificateDataProvider.RESULT)))
                .andReturn();
    }

    @Test
    public void getUserByIdNotFoundTest() throws Exception {
        Map<String, String> dataForTest = provider.getUserByIdNotFoundTest();
        this.mockMvc.perform(get(dataForTest.get(URL_REQUEST)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(dataForTest.get(RESULT))))
                .andReturn();
    }

    @Test
    public void getAllUsersTest() throws Exception {
        Map<String, String> dataForTest = provider.getAllUsersTest();
        this.mockMvc.perform(get(dataForTest.get(GiftCertificateDataProvider.URL_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(Integer.valueOf(dataForTest.get(RESULT_SIZE)))))
                .andExpect(content().json(dataForTest.get(GiftCertificateDataProvider.RESULT)))
                .andReturn();
    }
}
