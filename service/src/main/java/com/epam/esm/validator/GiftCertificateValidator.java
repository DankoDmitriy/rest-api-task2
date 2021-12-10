package com.epam.esm.validator;

import com.epam.esm.impl.GiftCertificate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateValidator {
    private static final String GIFT_CERTIFICATE_NAME_SYMBOL_REGEXP = "^[a-zA-ZА-Яа-я0-9\\s]{2,255}$";
    private static final int GIFT_CERTIFICATE_NAME_MIN_LENGTH = 2;
    private static final int GIFT_CERTIFICATE_NAME_MAX_LENGTH = 255;

    private static final String GIFT_CERTIFICATE_DESCRIPTION_SYMBOL_REGEXP = "^[a-zA-ZА-Яа-я0-9,.:;!?\\s]{2,255}$";
    private static final int GIFT_CERTIFICATE_DESCRIPTION_MIN_LENGTH = 2;
    private static final int GIFT_CERTIFICATE_DESCRIPTION_MAX_LENGTH = 255;

    private static final int GIFT_CERTIFICATE_DURATION_MIN = 1;
    private static final int GIFT_CERTIFICATE_DURATION_MAX = 365;

    private static final BigDecimal GIFT_CERTIFICATE_MIN_PRICE = new BigDecimal("0.01");
    private static final BigDecimal GIFT_CERTIFICATE_MAX_PRICE = new BigDecimal("1000");

    public List<ValidationError> validateName(String name, List<ValidationError> validationErrors) {
        if (name == null) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_NAME_IS_EMPTY_OR_NULL);
        } else {
            if (name.length() < GIFT_CERTIFICATE_NAME_MIN_LENGTH) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_NAME_LENGTH_IS_SHORT);
            }
            if (name.length() > GIFT_CERTIFICATE_NAME_MAX_LENGTH) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_NAME_LENGTH_IS_LONG);
            }
            if (!name.matches(GIFT_CERTIFICATE_NAME_SYMBOL_REGEXP)) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS);
            }
        }
        return validationErrors;
    }

    public List<ValidationError> validateDescription(String description, List<ValidationError> validationErrors) {
        if (description == null) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_DESCRIPTION_IS_EMPTY_OR_NULL);
        } else {
            if (description.length() < GIFT_CERTIFICATE_DESCRIPTION_MIN_LENGTH) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_DESCRIPTION_LENGTH_IS_SHORT);
            }
            if (description.length() > GIFT_CERTIFICATE_DESCRIPTION_MAX_LENGTH) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_DESCRIPTION_LENGTH_IS_LONG);
            }
            if (!description.matches(GIFT_CERTIFICATE_DESCRIPTION_SYMBOL_REGEXP)) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_DESCRIPTION_HAVE_NOT_CORRECT_SYMBOLS);
            }
        }
        return validationErrors;
    }

    public List<ValidationError> validateDuration(Integer duration, List<ValidationError> validationErrors) {
        if (duration == null) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_DURATION_IS_EMPTY_OR_NULL);
        } else {
            if (duration < GIFT_CERTIFICATE_DURATION_MIN) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_DURATION_LESS_THAN_ALLOWED_VALUE);
            }
            if (duration > GIFT_CERTIFICATE_DURATION_MAX) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_DURATION_MORE_THAN_ALLOWED_VALUE);
            }
        }
        return validationErrors;
    }

    public List<ValidationError> validatePrice(BigDecimal price, List<ValidationError> validationErrors) {
        if (price == null) {
            validationErrors.add(ValidationError.GIFT_CERTIFICATE_PRICE_IS_EMPTY_OR_NULL);
        } else {
            if (price.compareTo(GIFT_CERTIFICATE_MIN_PRICE) == -1) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_PRICE_IS_LESS_THAN_ALLOWED_VALUE);
            }
            if (price.compareTo(GIFT_CERTIFICATE_MAX_PRICE) == 1) {
                validationErrors.add(ValidationError.GIFT_CERTIFICATE_PRICE_IS_MORE_THAN_ALLOWED_VALUE);
            }
        }
        return validationErrors;
    }

    public List<ValidationError> validateCertificate(GiftCertificate certificate) {
        List<ValidationError> validationErrors = new ArrayList<>();
        validateName(certificate.getName(), validationErrors);
        validateDescription(certificate.getDescription(), validationErrors);
        validateDuration(certificate.getDuration(), validationErrors);
        validatePrice(certificate.getPrice(), validationErrors);
        return validationErrors;
    }
}
