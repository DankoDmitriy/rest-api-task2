package com.epam.esm.controlles;

import com.epam.esm.configs.SpringConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@TestPropertySource("/mysql-test.properties")
@WebAppConfiguration
public class GiftCertificateControllerTest {
    private static final String SINGLE_GIFT_CERTIFICATE_RESULT = "{\"id\":1,\"name\":\"Gif 1\",\"description\":\"description 1\",\"price\":9.99,\"duration\":5,\"createDate\":[2021,9,15,18,45,30],\"lastUpdateDate\":[2021,9,15,18,45,30],\"tagItems\":[{\"id\":1,\"name\":\"tag1\"},{\"id\":2,\"name\":\"tag2\"},{\"id\":3,\"name\":\"tag3\"}]}";
    private static final String SINGLE_GIFT_CERTIFICATE_REQUEST_URL = "/gift/1";
    private static final String LIST_GIFT_CERTIFICATE_RESULT = "[{\"id\":1,\"name\":\"Gif 1\",\"description\":\"description 1\",\"price\":9.99,\"duration\":5,\"createDate\":[2021,9,15,18,45,30],\"lastUpdateDate\":[2021,9,15,18,45,30],\"tagItems\":[{\"id\":1,\"name\":\"tag1\"},{\"id\":2,\"name\":\"tag2\"},{\"id\":3,\"name\":\"tag3\"}]},{\"id\":2,\"name\":\"Gif 2\",\"description\":\"second description\",\"price\":12.00,\"duration\":10,\"createDate\":[2021,12,5,18,45,30],\"lastUpdateDate\":[2021,12,5,18,45,30],\"tagItems\":[{\"id\":1,\"name\":\"tag1\"},{\"id\":2,\"name\":\"tag2\"}]}]";
    private static final String LIST_GIFT_CERTIFICATE_REQUEST_URL = "/gift";

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
}
