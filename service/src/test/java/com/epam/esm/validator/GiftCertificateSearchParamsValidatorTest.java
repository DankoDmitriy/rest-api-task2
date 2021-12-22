package com.epam.esm.validator;

import com.epam.esm.model.impl.GiftCertificateSearchParams;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSearchParamsValidatorTest {
    private final GiftCertificateSearchParamsValidator validator = new GiftCertificateSearchParamsValidator();

    @ParameterizedTest
    @MethodSource("dataForValidateSearchParams")
    void validateSearchParams(GiftCertificateSearchParams searchParams, List<ValidationError> expected) {
        List<ValidationError> actual = validator.validateSearchParams(searchParams);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> dataForValidateSearchParams() {
        return Stream.of(
                Arguments.of(new GiftCertificateSearchParams(),
                        Arrays.asList(
                                ValidationError.FIND_ALL)),
                Arguments.of(
                        new GiftCertificateSearchParams(
                                Arrays.asList("asdasdadsasdasdasdasdasdasdasdasdddddddddddasdddddddddddddddddasdasasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasdaa"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasdaa"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasdaa"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasdaa"
                                        + "asdasdadsasdasdasdasdasdasdasdasdddddddddddasddddddddddddddddddsdasd/asd"),
                                "asdasdadsasdasdasdasdasdasdasdsaddddasdddddddddddddddddasdasdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasdasdasdasdasdasdasdddddddddddasddddddddddddddddddsdas./asd",
                                "asdasdadsasdasdasdasdasaddddddddasdddddddddddddddddasdasdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasasdasdasdddddddddddasdddddddddddddddddddddasdadsassdasdasd"
                                        + "asdasdadsasdasdasdasdasdasdasdasdddddddddddasddddddddddddddddddsdasd/asd",
                                new HashSet<>(Arrays.asList(
                                        "ASC"))
                        ),
                        Arrays.asList(
                                ValidationError.SEARCH_TAG_NAME_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH,
                                ValidationError.SEARCH_GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH,
                                ValidationError.SEARCH_GIFT_CERTIFICATE_DESCRIPTION_HAVE_NOT_CORRECT_SYMBOLS_OR_LENGTH,
                                ValidationError.SEARCH_GIFT_CERTIFICATE_SORT_TYPE_IS_NOT_CORRECT))
        );
    }
}
