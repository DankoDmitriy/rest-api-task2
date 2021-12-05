package com.epam.esm.contollers;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.impl.ExceptionResponse;
import com.epam.esm.validator.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(IncorrectEntityException.class)
    public ResponseEntity<ExceptionResponse> handlerException(IncorrectEntityException exception) {
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(
                        exception.getMessage(),
                        enumListToStringList(exception.getValidationErrors()),
                        LocalDateTime.now().toString(),
                        "Error: 0001")
                , HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerException(EntityNotFoundException exception) {
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(
                        exception.getMessage(),
                        Arrays.asList(exception.getId().toString()),
                        LocalDateTime.now().toString(),
                        "Error: 0002")
                , HttpStatus.OK);
    }

    private List<String> enumListToStringList(List<ValidationError> validationErrors) {
        List<String> strings = new ArrayList<>();
        for (ValidationError error : validationErrors) {
            strings.add(error.toString());
        }
        return strings;
    }
}
