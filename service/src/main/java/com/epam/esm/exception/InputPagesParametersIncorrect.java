package com.epam.esm.exception;

import com.epam.esm.validator.ValidationError;

public class InputPagesParametersIncorrect extends RuntimeException {
    private final ValidationError errorMessage;

    public InputPagesParametersIncorrect(ValidationError errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ValidationError getErrorMessage() {
        return errorMessage;
    }
}
