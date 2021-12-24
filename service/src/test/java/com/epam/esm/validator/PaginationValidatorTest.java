package com.epam.esm.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationValidatorTest {
    private final PaginationValidator validator = new PaginationValidator();

    @ParameterizedTest
    @MethodSource("fullTest")
    void validateTest(Long rowsInDataBase, Integer pageNumber, Integer startPosition, boolean expected) {
        boolean actual = validator.validate(rowsInDataBase, pageNumber, startPosition);
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> fullTest() {
        return Stream.of(
                Arguments.of(100L, 10, 10, true),
                Arguments.of(100L, -1, 10, false),
                Arguments.of(10L, 1, 100, false)
        );
    }

}
