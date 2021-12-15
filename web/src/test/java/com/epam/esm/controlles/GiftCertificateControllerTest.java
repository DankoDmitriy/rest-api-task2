package com.epam.esm.controlles;

import com.epam.esm.model.impl.GiftCertificate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
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
//@ContextConfiguration(classes = {SpringConfig.class})
@TestPropertySource("/mysql-test.properties")
@WebAppConfiguration
@Sql(value = {"/database-data-initialization.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/database-data-initialization.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GiftCertificateControllerTest {
    private static final String SINGLE_GIFT_CERTIFICATE_REQUEST_URL = "/api/giftCertificates/1";
    private static final String SINGLE_GIFT_CERTIFICATE_RESULT = "{\"id\":1,\"name\":\"Gif 1\",\"description\":"
            + "\"description 1\",\"price\":9.99,\"duration\":5,\"createDate\":[2021,9,15,18,45,30],\"lastUpdateDate\""
            + ":[2021,9,15,18,45,30],\"tagItems\":[{\"id\":1,\"name\":\"tag1\"},{\"id\":2,\"name\":\"tag2\"},{\"id\":3,"
            + "\"name\":\"tag3\"}]}";

    private static final String LIST_GIFT_CERTIFICATE_REQUEST_URL = "/api/giftCertificates/";
    private static final String LIST_GIFT_CERTIFICATE_RESULT = "[{\"id\":1,\"name\":\"Gif 1\",\"description\":"
            + "\"description 1\",\"price\":9.99,\"duration\":5,\"createDate\":[2021,9,15,18,45,30],\"lastUpdateDate\""
            + ":[2021,9,15,18,45,30],\"tagItems\":[{\"id\":1,\"name\":\"tag1\"},{\"id\":2,\"name\":\"tag2\"},{\"id\":"
            + "3,\"name\":\"tag3\"}]},{\"id\":2,\"name\":\"Gif 2\",\"description\":\"second description\",\"price\""
            + ":12.00,\"duration\":10,\"createDate\":[2021,9,15,18,45,30],\"lastUpdateDate\":[2021,9,15,18,45,30],"
            + "\"tagItems\":[{\"id\":1,\"name\":\"tag1\"},{\"id\":2,\"name\":\"tag2\"}]}]";

    private static final String ADD_GIFT_CERTIFICATE_REQUEST_URL = "/api/giftCertificates/";
    private static final String ADD_GIFT_CERTIFICATE_EXPECTED_ID = "10";
    private static final String ADD_ERROR_CODE = "\"errorCode\":\"Error: 0001\"";

    private static final String DELETE_GIFT_CERTIFICATE_BY_ID_REQUEST_URL = "/api/giftCertificates/1";
    private static final String DELETE_GIFT_CERTIFICATE_BY_ID_GET_TAG_REQUEST_URL = "/api/giftCertificates/1";
    private static final String DELETE_ERROR_CODE = "\"errorCode\":\"Error: 0002\"";

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
        Assertions.assertNotNull(webApplicationContext.getBean("giftCertificateController"));
    }

    @Test
    public void getGiftCertificateByIdTest() throws Exception {
        this.mockMvc.perform(get(SINGLE_GIFT_CERTIFICATE_REQUEST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(SINGLE_GIFT_CERTIFICATE_RESULT))
                .andReturn();
    }

    @Test
    public void getAllGiftCertificatesTest() throws Exception {
        this.mockMvc.perform(get(LIST_GIFT_CERTIFICATE_REQUEST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(content().json(LIST_GIFT_CERTIFICATE_RESULT))
                .andReturn();
    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addGiftCertificateTest() throws Exception {
        GiftCertificate certificate = generateGiftCertificate();
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post(ADD_GIFT_CERTIFICATE_REQUEST_URL)
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
                .andExpect(content().string(containsString(ADD_GIFT_CERTIFICATE_EXPECTED_ID)));
    }

    @Test
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/database-data-initialization.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addGiftCertificateNegativeTest() throws Exception {
        GiftCertificate certificate = generateIncorrectGiftCertificate();
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post(ADD_GIFT_CERTIFICATE_REQUEST_URL)
                        .content(objectMapper.writeValueAsString(certificate))
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
    public void deleteGiftCertificateByIdTest() throws Exception {
        this.mockMvc.perform(delete(DELETE_GIFT_CERTIFICATE_BY_ID_REQUEST_URL))
                .andDo(print());
        this.mockMvc.perform(get(DELETE_GIFT_CERTIFICATE_BY_ID_GET_TAG_REQUEST_URL))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(DELETE_ERROR_CODE)))
                .andReturn();
    }

    private GiftCertificate generateGiftCertificate() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Gift 3");
        certificate.setDescription("description 3");
        certificate.setPrice(new BigDecimal(5.00));
        certificate.setDuration(100);
        return certificate;
    }

    private GiftCertificate generateIncorrectGiftCertificate() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Gift 3.,/");
        certificate.setDescription("");
        certificate.setPrice(new BigDecimal(-5.00));
        certificate.setDuration(368);
        return certificate;
    }
}
