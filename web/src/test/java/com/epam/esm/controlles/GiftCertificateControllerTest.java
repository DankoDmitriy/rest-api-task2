package com.epam.esm.controlles;

import com.epam.esm.data_provider.GiftCertificateDataProvider;
import com.epam.esm.model.impl.GiftCertificate;
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
import java.util.Map;

import static com.epam.esm.data_provider.GiftCertificateDataProvider.GIFT_CERTIFICATE;
import static com.epam.esm.data_provider.GiftCertificateDataProvider.RESULT;
import static com.epam.esm.data_provider.GiftCertificateDataProvider.URL_REQUEST;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
public class GiftCertificateControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final GiftCertificateDataProvider provider = new GiftCertificateDataProvider();

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void CreatingApplicationContextAndControllersTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("giftCertificateController"));
    }

    @Test
    public void getGiftCertificateByIdPositiveTest() throws Exception {
        Map<String, String> expectedResult = provider.getGiftCertificateByIdPositiveTest();
        this.mockMvc.perform(get(expectedResult.get(URL_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResult.get(RESULT)))
                .andReturn();
    }

    @Test
    public void getGiftCertificateByIdNotFoundTest() throws Exception {
        Map<String, String> expectedResult = provider.getGiftCertificateByIdNotFoundTest();
        this.mockMvc.perform(get(expectedResult.get(URL_REQUEST)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expectedResult.get(RESULT))))
                .andReturn();
    }

    @Test
    public void getAllGiftCertificatesTest() throws Exception {
        Map<String, String> expectedResult = provider.getAllGiftCertificatesTest();
        this.mockMvc.perform(get(expectedResult.get(URL_REQUEST)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(Object.class)))
                .andExpect(content().json(expectedResult.get(RESULT)))
                .andReturn();
    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addGiftCertificateTest() throws Exception {
        Map<String, Object> expectedResult = provider.addGiftCertificateTest();
        GiftCertificate certificate = (GiftCertificate) expectedResult.get(GIFT_CERTIFICATE);
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post((String) expectedResult.get(URL_REQUEST))
                        .content(objectMapper.writeValueAsString(certificate))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(certificate.getName())))
                .andExpect(content().string(containsString(certificate.getDescription())))
                .andExpect(content().string(containsString(certificate.getPrice().toString())))
                .andExpect(content().string(containsString(certificate.getDuration().toString())))
                .andExpect(content().string(containsString((String) expectedResult.get(RESULT))));
    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addGiftCertificateNegativeTest() throws Exception {
        Map<String, Object> expectedResult = provider.addGiftCertificateNegativeTest();
        GiftCertificate certificate = (GiftCertificate) expectedResult.get(GIFT_CERTIFICATE);
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post((String) expectedResult.get(URL_REQUEST))
                        .content(objectMapper.writeValueAsString(certificate))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString((String) expectedResult.get(RESULT))));
    }

//    @Test
//    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    public void deleteGiftCertificateByIdTest() throws Exception {
//        Map<String, String> expectedResult = provider.deleteGiftCertificateByIdTest();
//
//        this.mockMvc.perform(delete(expectedResult.get(URL_REQUEST)))
//                .andDo(print());
//        this.mockMvc.perform(get(expectedResult.get(URL_REQUEST)))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(containsString(expectedResult.get(RESULT))))
//                .andReturn();
//    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateGiftCertificateByIdTest() throws Exception {
        Map<String, Object> expectedResult = provider.updateGiftCertificateByIdTest();
        GiftCertificate certificate = (GiftCertificate) expectedResult.get(GIFT_CERTIFICATE);
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(patch((String) expectedResult.get(URL_REQUEST))
                        .content(objectMapper.writeValueAsString(certificate))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(certificate.getName())))
                .andExpect(content().string(containsString(certificate.getDescription())))
                .andExpect(content().string(containsString(certificate.getPrice().toString())))
                .andExpect(content().string(containsString(certificate.getDuration().toString())));
    }
}
