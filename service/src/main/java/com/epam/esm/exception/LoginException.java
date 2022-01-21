package com.epam.esm.exception;

public class LoginException extends RuntimeException {
    private final String errorMessage;
    private final String userName;

    public LoginException(String errorMessage, String userName) {
        this.errorMessage = errorMessage;
        this.userName = userName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getUserName() {
        return userName;
    }
}
