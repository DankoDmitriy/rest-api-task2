package com.epam.esm.data_provider;

import com.epam.esm.validator.ValidationError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationErrorsProvider {
    public List<ValidationError> getFindAllErrors() {
        return Arrays.asList(ValidationError.FIND_ALL);
    }

    public List<ValidationError> getEmptyErrors() {
        return new ArrayList<>();
    }

    public List<ValidationError> getErrorIncorrectTagNameSearch() {
        return Arrays.asList(
                ValidationError.SEARCH_TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
    }

    public List<ValidationError> getErrorGiftCertificateNameIsEmptyOrNull() {
        return Arrays.asList(
                ValidationError.GIFT_CERTIFICATE_NAME_IS_EMPTY_OR_NULL);
    }

    public List<ValidationError> getErrorGiftCertificateNameHasIncorrectSymbol() {
        return Arrays.asList(
                ValidationError.GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS);
    }

    public List<ValidationError> getErrorTagNameHasIncorrectSymbol() {
        return Arrays.asList(
                ValidationError.TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS);
    }
}
