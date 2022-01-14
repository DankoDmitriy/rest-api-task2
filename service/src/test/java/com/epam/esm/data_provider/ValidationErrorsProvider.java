package com.epam.esm.data_provider;

import com.epam.esm.validator.ValidationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationErrorsProvider {
    public List<ValidationError> getFindAllErrors() {
        return Collections.singletonList(ValidationError.FIND_ALL);
    }

    public List<ValidationError> getEmptyErrors() {
        return new ArrayList<>();
    }

    public List<ValidationError> getErrorIncorrectTagNameSearch() {
        return Collections.singletonList(
                ValidationError.SEARCH_TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
    }

    public List<ValidationError> getErrorGiftCertificateNameIsEmptyOrNull() {
        return Collections.singletonList(
                ValidationError.GIFT_CERTIFICATE_NAME_IS_EMPTY_OR_NULL);
    }

    public List<ValidationError> getErrorGiftCertificateNameHasIncorrectSymbol() {
        return Collections.singletonList(
                ValidationError.GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS);
    }

    public List<ValidationError> getErrorTagNameHasIncorrectSymbol() {
        return Collections.singletonList(
                ValidationError.TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS);
    }
}
