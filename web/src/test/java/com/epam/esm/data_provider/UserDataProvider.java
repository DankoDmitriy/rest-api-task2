package com.epam.esm.data_provider;

import java.util.HashMap;
import java.util.Map;

public class UserDataProvider {
    private static final String URL_GET_USER_BY_ID = "/api/users/1";

    private static final String URL_GET_GIFT_CERTIFICATE_BY_ID_NOT_FOUND = "/api/users/100";
    private static final String NOT_FOUND_DATA_ERROR_CODE = "\"errorCode\":\"Error: 0002\"";

    private static final String URL_GET_ALL_USERS = "/api/users/";

    public static final String URL_REQUEST = "url";
    public static final String RESULT = "result";
    public static final String RESULT_SIZE = "resultSize";

    public Map<String, String> getUserByIdPositiveTest() {
        String singleResult = "{" +
                "\"id\":1," +
                "\"name\":\"User1\"" +
                "}";
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_GET_USER_BY_ID);
        resultMap.put(RESULT, singleResult);
        return resultMap;
    }

    public Map<String, String> getUserByIdNotFoundTest() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_GET_GIFT_CERTIFICATE_BY_ID_NOT_FOUND);
        resultMap.put(RESULT, NOT_FOUND_DATA_ERROR_CODE);
        return resultMap;
    }

    public Map<String, String> getAllUsersTest() {
        String resultSize = "3";
        String listResult = "[" +
                "{" +
                "\"id\":1," +
                "\"name\":\"User1\"" +
                "}," +
                "{" +
                "\"id\":2," +
                "\"name\":\"User2\"" +
                "}," +
                "{" +
                "\"id\":3," +
                "\"name\":\"User3\"" +
                "}" +
                "]";
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(URL_REQUEST, URL_GET_ALL_USERS);
        resultMap.put(RESULT, listResult);
        resultMap.put(RESULT_SIZE, resultSize);
        return resultMap;
    }
}