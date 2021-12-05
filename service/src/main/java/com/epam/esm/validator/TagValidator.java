package com.epam.esm.validator;

import com.epam.esm.impl.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagValidator {
    private static final String TAG_NAME_SYMBOL_REGEXP = "^[a-zA-ZА-Яа-я\\s]{2,255}$";
    private static final int TAG_NAME_MIN_LENGTH = 2;
    private static final int TAG_NAME_MAX_LENGTH = 255;

    public List<ValidationError> validateTagName(String tagName) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if (tagName == null) {
            validationErrors.add(ValidationError.TAG_NAME_IS_EMPTY_OR_NULL);
        }
        if (tagName.length() < TAG_NAME_MIN_LENGTH) {
            validationErrors.add(ValidationError.TAG_NAME_LENGTH_IS_SHORT);
        }
        if (tagName.length() > TAG_NAME_MAX_LENGTH) {
            validationErrors.add(ValidationError.TAG_NAME_LENGTH_IS_LONG);
        }
        if (!tagName.matches(TAG_NAME_SYMBOL_REGEXP)) {
            validationErrors.add(ValidationError.TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS);
        }
        return validationErrors;
    }

    public List<ValidationError> validateTagNameList(List<Tag> tagItems) {
        List<ValidationError> validationErrors = new ArrayList<>();
        for (Tag tagItem : tagItems) {
            validationErrors.addAll(validateTagName(tagItem.getName()));
        }
        return validationErrors;
    }
}
