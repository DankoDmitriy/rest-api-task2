package com.epam.esm.validator;

import com.epam.esm.model.impl.GiftCertificateSearchParams;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateSearchParamsValidator {
    private static final String TAG_NAME_REGEX = "^[a-zA-ZА-Яа-я0-9\\s]{2,255}$";
    private static final String GIFT_CERTIFICATE_NAME_REGEX = "^[a-zA-ZА-Яа-я0-9\\s]{2,255}$";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_REGEX = "^[a-zA-ZА-Яа-я,.:;!?\\s]{2,255}$";

    public List<ValidationError> validateSearchParams(GiftCertificateSearchParams searchParams) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (searchParams.getTags() == null
                && searchParams.getGiftCertificateName() == null
                && searchParams.getGiftCertificateDescription() == null
                && searchParams.getSort() == null) {
            validationErrors.add(ValidationError.FIND_ALL);
        } else {

            if (searchParams.getTags() != null) {
                List<String> tags = searchParams.getTags();
                for (String tag : tags) {
                    if (!tag.matches(TAG_NAME_REGEX)) {
                        validationErrors.add(ValidationError.SEARCH_TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
                    }
                }
            }

            if (searchParams.getGiftCertificateName() != null) {
                if (!searchParams.getGiftCertificateName().matches(GIFT_CERTIFICATE_NAME_REGEX)) {
                    validationErrors.add(ValidationError.SEARCH_GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
                }
            }

            if (searchParams.getGiftCertificateDescription() != null) {
                if (!searchParams.getGiftCertificateDescription().matches(GIFT_CERTIFICATE_DESCRIPTION_REGEX)) {
                    validationErrors.add(
                            ValidationError.SEARCH_GIFT_CERTIFICATE_DESCRIPTION_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
                }
            }

            if (searchParams.getSort() != null) {
                for (String s : searchParams.getSort()) {
                    if (!searchParams.getSortTypes().contains(s)) {
                        validationErrors.add(
                                ValidationError.SEARCH_GIFT_CERTIFICATE_SORT_TYPE_IS_NOT_CORRECT);
                    }
                }
            }
        }
        return validationErrors;
    }
}
