package com.epam.esm.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftCertificateValidatorTest {

    private final static String FILE_WITH_TEST_DATA = "src/test/resources/testDataForGiftCertificateValidatorTest.txt";

    private final GiftCertificateValidator validator = new GiftCertificateValidator();

    @ParameterizedTest
    @MethodSource("dataForValidateName")
    void validateNameTest(String inputString, List<ValidationError> expected) {
        List<ValidationError> validationErrors = new ArrayList<>();
        List<ValidationError> actual = validator.validateName(inputString, validationErrors);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> dataForValidateName() throws IOException {
        String testData = new String(Files.readAllBytes(Paths.get(FILE_WITH_TEST_DATA)));
        return Stream.of(
                Arguments.of("Gig 1", new ArrayList<ValidationError>()),
                Arguments.of(null,
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_NAME_IS_EMPTY_OR_NULL)),
                Arguments.of("",
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_NAME_LENGTH_IS_SHORT,
                                ValidationError.GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS)),
                Arguments.of(testData,
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_NAME_LENGTH_IS_LONG,
                                ValidationError.GIFT_CERTIFICATE_NAME_HAVE_NOT_CORRECT_SYMBOLS)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("dataForValidateDescription")
    void validateDescriptionTest(String inputString, List<ValidationError> expected) {
        List<ValidationError> validationErrors = new ArrayList<>();
        List<ValidationError> actual = validator.validateDescription(inputString, validationErrors);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> dataForValidateDescription() throws IOException {
        String testData = new String(Files.readAllBytes(Paths.get(FILE_WITH_TEST_DATA)));
        return Stream.of(
                Arguments.of("description", new ArrayList<ValidationError>()),
                Arguments.of(null,
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_DESCRIPTION_IS_EMPTY_OR_NULL)),
                Arguments.of("",
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_DESCRIPTION_LENGTH_IS_SHORT,
                                ValidationError.GIFT_CERTIFICATE_DESCRIPTION_HAVE_NOT_CORRECT_SYMBOLS)),
                Arguments.of(testData,
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_DESCRIPTION_LENGTH_IS_LONG,
                                ValidationError.GIFT_CERTIFICATE_DESCRIPTION_HAVE_NOT_CORRECT_SYMBOLS)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("dataForValidateDuration")
    void validateDurationTest(Integer integer, List<ValidationError> expected) {
        List<ValidationError> validationErrors = new ArrayList<>();
        List<ValidationError> actual = validator.validateDuration(integer, validationErrors);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> dataForValidateDuration() {
        return Stream.of(
                Arguments.of(null,
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_DURATION_IS_EMPTY_OR_NULL)),
                Arguments.of(-1,
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_DURATION_LESS_THAN_ALLOWED_VALUE)),
                Arguments.of(400,
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_DURATION_MORE_THAN_ALLOWED_VALUE))
        );
    }

    @ParameterizedTest
    @MethodSource("dataForValidatePrice")
    void validatePriceTest(BigDecimal decimal, List<ValidationError> expected) {
        List<ValidationError> validationErrors = new ArrayList<>();
        List<ValidationError> actual = validator.validatePrice(decimal, validationErrors);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> dataForValidatePrice() {
        return Stream.of(
                Arguments.of(null,
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_PRICE_IS_EMPTY_OR_NULL)),
                Arguments.of(new BigDecimal(-5),
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_PRICE_IS_LESS_THAN_ALLOWED_VALUE)),
                Arguments.of(new BigDecimal(5000),
                        Arrays.asList(
                                ValidationError.GIFT_CERTIFICATE_PRICE_IS_MORE_THAN_ALLOWED_VALUE))
        );
    }
}
