package com.epam.esm.validator;

import org.springframework.stereotype.Component;

import java.util.Map;

import static com.epam.esm.SearchParameters.*;

@Component
public class GiftCertificateSearchParamsValidator {
    private static final String TAG_NAME_REGEX = "^[a-zA-ZА-Яа-я\\s]{2,255}$";
    private static final String GIFT_CERTIFICATE_NAME_REGEX = "^[a-zA-ZА-Яа-я\\s]{2,255}$";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_REGEX = "^[a-zA-ZА-Яа-я,.:;!?\\s]{2,255}$";

    public boolean isTagNameValid(String name) {
        return name != null && name.matches(TAG_NAME_REGEX);
    }

    public boolean isGiftCertificateNameValid(String name) {
        return name != null && name.matches(GIFT_CERTIFICATE_NAME_REGEX);
    }

    public boolean isGiftCertificateDescriptionValid(String description) {
        return description != null && description.matches(GIFT_CERTIFICATE_DESCRIPTION_REGEX);
    }

    public boolean isSearchParams(Map<String, String[]> searchParams) {
        boolean result = true;
        String[] tag_name = searchParams.get(SEARCH_TAG_NAME);
        if (tag_name != null) {
            if (!isTagNameValid(tag_name[0])) {
                result = false;
            }
        }

        String[] gift_name = searchParams.get(SEARCH_GIFT_NAME);
        if (gift_name != null) {
            if (isGiftCertificateNameValid(gift_name[0])) {
                result = false;
            }
        }

        String[] gift_description = searchParams.get(SEARCH_GIFT_DESCRIPTION);
        if (gift_description != null) {
            if (isGiftCertificateNameValid(gift_description[0])) {
                result = false;
            }
        }
        return result;
    }
}
