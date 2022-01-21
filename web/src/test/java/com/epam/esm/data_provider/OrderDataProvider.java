package com.epam.esm.data_provider;

import java.util.HashMap;
import java.util.Map;

public class OrderDataProvider {
    private static final String URL_GET_ORDER_BY_ID = "/api/orders/1";
    private static final String URL_GET_ALL_ORDERS = "/api/orders/?page=0&size=10";

    public static final String URL_REQUEST = "url";
    public static final String RESULT = "result";
    public static final String RESULT_SIZE = "resultSize";

    public Map<String, String> getOrderByIdPositiveTest() {
        String singleResult = "{\"" +
                "id\":1," +
                "\"cost\":9.99," +
                "\"purchaseDate\":\"2021-09-15T18:45:30\"," +
                "\"userDto\":" +
                "{\"id\":1," +
                "\"name\":\"User1\"," +
                "\"_links\":" +
                "{\"self\":{\"href\":\"http://localhost/api/users/1\"}}}," +
                "\"giftCertificateDtoList\":[" +
                "{\"id\":1," +
                "\"name\":\"Gif 1\"," +
                "\"description\":\"description 1\"," +
                "\"price\":9.99,\"duration\":5," +
                "\"createDate\":\"2021-09-15T18:45:30\"," +
                "\"lastUpdateDate\":\"2021-09-15T18:45:30\"," +
                "\"tagDtoList\":[" +
                "{\"id\":1," +
                "\"name\":\"tag1\"," +
                "\"_links\":" +
                "{\"self\":{\"href\":\"http://localhost/api/tags/1\"}," +
                "\"delete\":{\"href\":\"http://localhost/api/tags/1\"}}}," +
                "{\"id\":2," +
                "\"name\":\"tag2\"," +
                "\"_links\":" +
                "{\"self\":{\"href\":\"http://localhost/api/tags/2\"}," +
                "\"delete\":{\"href\":\"http://localhost/api/tags/2\"}}}," +
                "{\"id\":3," +
                "\"name\":\"tag3\"," +
                "\"_links\":" +
                "{\"self\":{\"href\":\"http://localhost/api/tags/3\"}," +
                "\"delete\":{\"href\":\"http://localhost/api/tags/3\"}}}]," +
                "\"_links\":" +
                "{\"self\":{\"href\":\"http://localhost/api/giftCertificates/1\"}," +
                "\"delete\":{\"href\":\"http://localhost/api/giftCertificates/1\"}," +
                "\"update\":{\"href\":\"http://localhost/api/giftCertificates/1\"}}}]," +
                "\"_links\":" +
                "{\"self\":{\"href\":\"http://localhost/api/orders/1\"}," +
                "\"delete\":{\"href\":\"http://localhost/api/orders/1\"}}}";
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_GET_ORDER_BY_ID);
        resultMap.put(RESULT, singleResult);
        return resultMap;
    }

    public Map<String, String> getAllOrdersTest() {
        String resultSize = "5";
        String listResult = "{" +
                "\"size\":10," +
                "\"totalElements\":1," +
                "\"totalPages\":1," +
                "\"number\":0," +
                "\"items\":[" +
                "{\"id\":1," +
                "\"cost\":9.99," +
                "\"purchaseDate\":\"2021-09-15T18:45:30\"," +
                "\"userDto\":" +
                "{\"id\":1," +
                "\"name\":\"User1\"," +
                "\"links\":" +
                "[{\"rel\":\"self\",\"href\":\"http://localhost/api/users/1\"}]}," +
                "\"giftCertificateDtoList\":" +
                "[{\"id\":1," +
                "\"name\":\"Gif 1\"," +
                "\"description\":\"description 1\"," +
                "\"price\":9.99," +
                "\"duration\":5," +
                "\"createDate\":\"2021-09-15T18:45:30\"," +
                "\"lastUpdateDate\":\"2021-09-15T18:45:30\"," +
                "\"tagDtoList\":" +
                "[{\"id\":1," +
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
                "\"links\":[" +
                "{\"rel\":\"self\",\"href\":\"http://localhost/api/giftCertificates/1\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/giftCertificates/1\"}," +
                "{\"rel\":\"update\",\"href\":\"http://localhost/api/giftCertificates/1\"}]}]," +
                "\"links\":" +
                "[{\"rel\":\"self\",\"href\":\"http://localhost/api/orders/1\"}," +
                "{\"rel\":\"delete\",\"href\":\"http://localhost/api/orders/1\"}]}]}";
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_GET_ALL_ORDERS);
        resultMap.put(RESULT, listResult);
        resultMap.put(RESULT_SIZE, resultSize);
        return resultMap;
    }
}
