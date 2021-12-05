package com.epam.esm.exception;

import com.epam.esm.validator.ValidationError;

import java.util.List;

public class IncorrectEntityException extends RuntimeException {
    private List<ValidationError> validationErrors;

    public IncorrectEntityException(String message, List<ValidationError> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

}
