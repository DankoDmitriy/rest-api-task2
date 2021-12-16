package com.epam.esm.exception;

import com.epam.esm.validator.ValidationError;

import java.util.List;

public class IncorrectEntityException extends RuntimeException {
    private ValidationError errorMessage;
    private List<ValidationError> validationErrors;

    public IncorrectEntityException(ValidationError errorMessage, List<ValidationError> validationErrors) {
        this.errorMessage = errorMessage;
        this.validationErrors = validationErrors;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public ValidationError getErrorMessage() {
        return errorMessage;
    }
}
