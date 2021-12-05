package com.epam.esm.validator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.esm.SearchParameters.*;

@Component
public class GiftCertificateSearchParamsValidator {
    private static final String TAG_NAME_REGEX = "^[a-zA-ZА-Яа-я\\s]{2,255}$";
    private static final String GIFT_CERTIFICATE_NAME_REGEX = "^[a-zA-ZА-Яа-я\\s]{2,255}$";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_REGEX = "^[a-zA-ZА-Яа-я,.:;!?\\s]{2,255}$";
    private static final String SORT_ASC_REGEX = "^ASC$";
    private static final String SORT_DESC_REGEX = "^DESC$";

    public List<ValidationError> validateSearchParams(Map<String, String[]> searchParams) {
        List<ValidationError> validationErrors = new ArrayList<>();

        String[] tagName = searchParams.get(SEARCH_TAG_NAME);
        if (tagName != null) {
            if (!tagName[0].matches(TAG_NAME_REGEX)) {
                validationErrors.add(ValidationError.SEARCH_TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
            }
        }
        String[] giftName = searchParams.get(SEARCH_GIFT_NAME);
        if (giftName != null) {
            if (!giftName[0].matches(GIFT_CERTIFICATE_NAME_REGEX)) {
                validationErrors.add(ValidationError.SEARCH_GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
            }
        }
        String[] giftDescription = searchParams.get(SEARCH_GIFT_DESCRIPTION);
        if (giftDescription != null) {
            if (!giftDescription[0].matches(GIFT_CERTIFICATE_DESCRIPTION_REGEX)) {
                validationErrors.add(
                        ValidationError.SEARCH_GIFT_CERTIFICATE_DESCRIPTION_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
            }
        }
        String[] giftSortByName = searchParams.get(SEARCH_SORT_BY_GIFT_NAME);
        if (giftSortByName != null) {
            if (!giftSortByName[0].toUpperCase().matches(SORT_ASC_REGEX) &&
                    !giftSortByName[0].matches(SORT_DESC_REGEX)) {
                validationErrors.add(
                        ValidationError.SEARCH_GIFT_CERTIFICATE_NAME_FOR_SORT_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
            }
        }
        String[] giftSortByCreateDate = searchParams.get(SEARCH_SORT_BY_GIFT_CREATE_DATE);
        if (giftSortByCreateDate != null) {
            if (!giftSortByCreateDate[0].toUpperCase().matches(SORT_ASC_REGEX) &&
                    !giftSortByCreateDate[0].matches(SORT_DESC_REGEX)) {
                validationErrors.add(
                        ValidationError.SEARCH_GIFT_CERTIFICATE_CREATE_DATE_FOR_SORT_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH);
            }
        }
        return validationErrors;
    }
}
