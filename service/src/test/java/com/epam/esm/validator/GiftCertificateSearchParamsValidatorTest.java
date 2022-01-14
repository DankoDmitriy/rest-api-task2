package com.epam.esm.validator;

import com.epam.esm.model.impl.GiftCertificateSearchParams;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateSearchParamsValidatorTest {

    private final static String FILE_WITH_TEST_DATA = "src/test/resources/testDataForGiftCertificateSearchParamsValidatorTest.txt";

    private final GiftCertificateSearchParamsValidator validator = new GiftCertificateSearchParamsValidator();

    @ParameterizedTest
    @MethodSource("dataForValidateSearchParams")
    void validateSearchParams(GiftCertificateSearchParams searchParams, List<ValidationError> expected){
        List<ValidationError> actual = validator.validateSearchParams(searchParams);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> dataForValidateSearchParams() throws IOException {
        String testData = new String(Files.readAllBytes(Paths.get(FILE_WITH_TEST_DATA)));
        return Stream.of(
                Arguments.of(new GiftCertificateSearchParams(),
                        Arrays.asList(
                                ValidationError.FIND_ALL)),
                Arguments.of(
                        new GiftCertificateSearchParams(
                                Arrays.asList(testData),
                                testData,
                                testData,
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
