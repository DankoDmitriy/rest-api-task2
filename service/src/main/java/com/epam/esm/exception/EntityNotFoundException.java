package com.epam.esm.exception;

public class EntityNotFoundException extends RuntimeException {
    private final String errorMessage;
    private final Long id;

    public EntityNotFoundException(String errorMessage, Long id) {
        this.errorMessage = errorMessage;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
