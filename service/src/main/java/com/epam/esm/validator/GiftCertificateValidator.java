package com.epam.esm.validator;

import com.epam.esm.impl.GiftCertificate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateValidator {
    private static final String GIFT_CERTIFICATE_NAME_SYMBOL_REGEXP = "^[a-zA-ZА-Яа-я\\s]{2,255}$";
    private static final int GIFT_CERTIFICATE_NAME_MIN_LENGTH = 2;
    private static final int GIFT_CERTIFICATE_NAME_MAX_LENGTH = 255;

    private static final String GIFT_CERTIFICATE_DESCRIPTION_SYMBOL_REGEXP = "^[a-zA-ZА-Яа-я,.:;!?\\s]{2,255}$";
    private static final int GIFT_CERTIFICATE_DESCRIPTION_MIN_LENGTH = 2;
    private static final int GIFT_CERTIFICATE_DESCRIPTION_MAX_LENGTH = 255;

    private static final int GIFT_CERTIFICATE_DURATION_MIN = 1;
    private static final int GIFT_CERTIFICATE_DURATION_MAX = 365;

    private static final BigDecimal GIFT_CERTIFICATE_MIN_PRICE = new BigDecimal("0.01");
    private static final BigDecimal GIFT_CERTIFICATE_MAX_PRICE = new BigDecimal("1000");

    public List<ValidationError> validateCertificate(GiftCertificate certificate) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (certificate.getName() == null) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_NAME_IS_EMPTY_OR_NULL);
        }
        if (certificate.getName().length() < GIFT_CERTIFICATE_NAME_MIN_LENGTH) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_NAME_LENGTH_IS_SHORT);
        }
        if (certificate.getName().length() > GIFT_CERTIFICATE_NAME_MAX_LENGTH) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_NAME_LENGTH_IS_LONG);
        }
        if (!certificate.getName().matches(GIFT_CERTIFICATE_NAME_SYMBOL_REGEXP)) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS);
        }

        if (certificate.getDescription() == null) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_DESCRIPTION_IS_EMPTY_OR_NULL);
        }
        if (certificate.getDescription().length() < GIFT_CERTIFICATE_DESCRIPTION_MIN_LENGTH) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_DESCRIPTION_LENGTH_IS_LONG);
        }
        if (certificate.getDescription().length() > GIFT_CERTIFICATE_DESCRIPTION_MAX_LENGTH) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_DESCRIPTION_LENGTH_IS_SHORT);
        }
        if (!certificate.getName().matches(GIFT_CERTIFICATE_DESCRIPTION_SYMBOL_REGEXP)) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_DESCRIPTION_HAVE_NOT_CORRECT_SYMBOLS);
        }

        if (certificate.getDuration() < GIFT_CERTIFICATE_DURATION_MIN) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_DURATION_LESS_THAN_ALLOWED_VALUE);
        }
        if (certificate.getDuration() > GIFT_CERTIFICATE_DURATION_MAX) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_DURATION_MORE_THAN_ALLOWED_VALUE);
        }

        if (certificate.getPrice().compareTo(GIFT_CERTIFICATE_MIN_PRICE) == -1) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_PRICE_IS_LESS_THAN_ALLOWED_VALUE);
        }
        if (certificate.getPrice().compareTo(GIFT_CERTIFICATE_MAX_PRICE) == 1) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_PRICE_IS_MORE_THAN_ALLOWED_VALUE);
        }
        return validationErrors;
    }
}
