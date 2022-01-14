package com.epam.esm.validator;

import com.epam.esm.model.impl.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagValidatorTest {
    private final TagValidator validator = new TagValidator();

    @ParameterizedTest
    @MethodSource("dataForValidateTagName")
    void validateTagNameTest(String inputString, List<ValidationError> expected) {
        List<ValidationError> actual = validator.validateTagName(inputString);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> dataForValidateTagName() {
        return Stream.of(
                Arguments.of("Tag1", new ArrayList<ValidationError>()),
                Arguments.of("",
                        Arrays.asList(
                                ValidationError.TAG_NAME_LENGTH_IS_SHORT,
                                ValidationError.TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS)),
                Arguments.of("asdasdadsasdasdasdasdasdasdasdasdddddddddddasdddddddddddddddddddddasdadsasdasdasdasdasd"
                                + "asdasdadsasdasdasdasdasdasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                + "asdasdadsasdasdasdasdasdasdasdasdddddddddddasdddddddddddddddddddddasdadsasdaasdasd"
                                + "asdasdadsasdasdasdasdasdasdasdasdddddddddddasdddddddddddddddddddddasdadsasdasd/asd",
                        Arrays.asList(
                                ValidationError.TAG_NAME_LENGTH_IS_LONG,
                                ValidationError.TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validateTagNameList")
    void validateTagNameListTest(List<Tag> tagList, List<ValidationError> expected) {
        List<ValidationError> actual = validator.validateTagNameList(tagList);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> validateTagNameList() {
        return Stream.of(
                Arguments.of(new ArrayList<Tag>(), new ArrayList<ValidationError>())
        );
    }
}
