package com.epam.esm.exception;

import com.epam.esm.validator.ValidationError;

public class EntityNotFoundException extends RuntimeException {
    private ValidationError errorMessage;
    private Long id;

    public EntityNotFoundException(ValidationError errorMessage, Long id) {
        this.errorMessage = errorMessage;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public ValidationError getErrorMessage() {
        return errorMessage;
    }
}
