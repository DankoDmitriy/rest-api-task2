package com.epam.esm.data_provider;

import com.epam.esm.model.impl.GiftCertificate;
import com.epam.esm.model.impl.Tag;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GiftCertificateDataProvider {
    private static final String URL_GET_GIFT_CERTIFICATE_BY_ID = "/api/giftCertificates/1";

    private static final String URL_GET_GIFT_CERTIFICATE_BY_ID_NOT_FOUND = "/api/giftCertificates/100";
    private static final String NOT_FOUND_DATA_ERROR_CODE = "\"errorCode\":\"Error: 0002\"";

    private static final String URL_GET_ALL_GIFT_CERTIFICATES = "/api/giftCertificates/?page=0&size=10";

    private static final String URL_ADD_GIFT_CERTIFICATE = "/api/giftCertificates/";
    private static final String ADD_GIFT_CERTIFICATE_EXPECTED_ID = "10";
    private static final String ADD_ERROR_CODE = "\"errorCode\":\"Error: 0005\"";

    private static final String URL_DELETE_GIFT_CERTIFICATE_BY_ID = "/api/giftCertificates/2";
    private static final String DELETE_ERROR_CODE = "\"errorCode\":\"Error: 0002\"";

    private static final String UPDATE_GIFT_CERTIFICATE_EXPECTED_ID = "1";
    private static final String URL_UPDATE_GIFT_CERTIFICATE_BY_ID = "/api/giftCertificates/1";

    public static final String URL_REQUEST = "url";
    public static final String RESULT = "result";
    public static final String RESULT_SIZE = "resultSize";
    public static final String GIFT_CERTIFICATE = "certificate";

    public Map<String, String> getGiftCertificateByIdPositiveTest() {
        String singleResult = "{" +
                "\"id\":1," +
                "\"name\":\"Gif 1\"," +
                "\"description\":\"description 1\"," +
                "\"price\":9.99," +
                "\"duration\":5," +
                "\"createDate\":\"2021-09-15T18:45:30\"," +
                "\"lastUpdateDate\":\"2021-09-15T18:45:30\"," +
                "\"tagDtoList\":" +
                "[" +
                "{\"id\":1,\"name\":\"tag1\"}," +
                "{\"id\":2,\"name\":\"tag2\"}," +
                "{\"id\":3,\"name\":\"tag3\"}" +
                "]" +
                "}";
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_GET_GIFT_CERTIFICATE_BY_ID);
        resultMap.put(RESULT, singleResult);
        return resultMap;
    }

    public Map<String, String> getGiftCertificateByIdNotFoundTest() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_GET_GIFT_CERTIFICATE_BY_ID_NOT_FOUND);
        resultMap.put(RESULT, NOT_FOUND_DATA_ERROR_CODE);
        return resultMap;
    }

    public Map<String, String> getAllGiftCertificatesTest() {
        String resultSize = "2";
        String listResult = "{" +
                "\"size\":10," +
                "\"totalElements\":2," +
                "\"totalPages\":1," +
                "\"number\":0," +
                "\"items\":[" +
                "{\"" +
                "id\":1," +
                "\"name\":\"Gif 1\"," +
                "\"description\":\"description 1\"," +
                "\"price\":9.99," +
                "\"duration\":5," +
                "\"createDate\":\"2021-09-15T18:45:30\"," +
                "\"lastUpdateDate\":\"2021-09-15T18:45:30\"," +
                "\"tagDtoList\":[{" +
                "\"id\":1," +
                "\"name\":\"tag1\"," +
                "\"links\":[" +
                "{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/1\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/1\"}]}," +
                "{\"id\":2," +
                "\"name\":\"tag2\"," +
                "\"links\":[" +
                "{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/2\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/2\"}]}," +
                "{\"id\":3," +
                "\"name\":\"tag3\"," +
                "\"links\":[" +
                "{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/3\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/3\"}]}]," +
                "\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/api/giftCertificates/1\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/giftCertificates/1\"}," +
                "{\"rel\":\"update\",\"href\":\"http://localhost/api/giftCertificates/1\"}]}," +
                "{\"id\":2," +
                "\"name\":\"Gif 2\"," +
                "\"description\":\"second description\"," +
                "\"price\":12.00," +
                "\"duration\":10," +
                "\"createDate\":\"2021-09-15T18:45:30\"," +
                "\"lastUpdateDate\":\"2021-09-15T18:45:30\"," +
                "\"tagDtoList\":[" +
                "{\"id\":1," +
                "\"name\":\"tag1\"," +
                "\"links\":[" +
                "{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/1\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/1\"}]}," +
                "{\"id\":2," +
                "\"name\":\"tag2\"," +
                "\"links\":[" +
                "{\"rel\":\"self\",\"href\":\"http://localhost/api/tags/2\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/tags/2\"}]}]," +
                "\"links\":[" +
                "{\"rel\":\"self\",\"href\":\"http://localhost/api/giftCertificates/2\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/giftCertificates/2\"}," +
                "{\"rel\":\"update\",\"href\":\"http://localhost/api/giftCertificates/2\"}]}]}";
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_GET_ALL_GIFT_CERTIFICATES);
        resultMap.put(RESULT, listResult);
        resultMap.put(RESULT_SIZE, resultSize);
        return resultMap;
    }

    public Map<String, Object> addGiftCertificateTest() {
        GiftCertificateDto certificate = new GiftCertificateDto();
        certificate.setName("Gift 3");
        certificate.setDescription("description 3");
        certificate.setPrice(new BigDecimal(5.00));
        certificate.setDuration(100);
        TagDto tag = new TagDto();
        tag.setId(1L);
        tag.setName("tag1");
        certificate.setTagDtoList(Arrays.asList(tag));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_ADD_GIFT_CERTIFICATE);
        resultMap.put(RESULT, ADD_GIFT_CERTIFICATE_EXPECTED_ID);
        resultMap.put(GIFT_CERTIFICATE, certificate);
        return resultMap;
    }

    public Map<String, Object> addGiftCertificateNegativeTest() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Gift 3.,/");
        certificate.setDescription("");
        certificate.setPrice(new BigDecimal(-5.00));
        certificate.setDuration(368);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_ADD_GIFT_CERTIFICATE);
        resultMap.put(RESULT, ADD_ERROR_CODE);
        resultMap.put(GIFT_CERTIFICATE, certificate);
        return resultMap;
    }

    public Map<String, String> deleteGiftCertificateByIdTest() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_DELETE_GIFT_CERTIFICATE_BY_ID);
        resultMap.put(RESULT, DELETE_ERROR_CODE);
        return resultMap;
    }

    public Map<String, Object> updateGiftCertificateByIdTest() {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setName("Gift 1Update");
        certificate.setDescription("description 3Update");
        certificate.setPrice(new BigDecimal(155.00));
        certificate.setDuration(200);
        Tag tag1 = new Tag();
        tag1.setName("tag3");
        Tag tag2 = new Tag();
        tag2.setName("tag4");
        certificate.setTags(Arrays.asList(tag1, tag2));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(GIFT_CERTIFICATE, certificate);
        resultMap.put(URL_REQUEST, URL_UPDATE_GIFT_CERTIFICATE_BY_ID);
        return resultMap;
    }
}
