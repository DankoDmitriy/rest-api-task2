package com.epam.esm.exception;

public class RegistrationException extends RuntimeException {
    private final String errorMessage;
    private final String userName;

    public RegistrationException(String errorMessage, String userName) {
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
