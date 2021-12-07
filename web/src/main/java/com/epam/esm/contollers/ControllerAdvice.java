package com.epam.esm.contollers;

import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.IncorrectEntityException;
import com.epam.esm.impl.ExceptionResponse;
import com.epam.esm.validator.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class ControllerAdvice {
    private static final String ERROR_CODE_0001 = "Error: 0001";
    private static final String ERROR_CODE_0002 = "Error: 0002";

    private ResourceBundleMessageSource messageSource;

    @Autowired
    public ControllerAdvice(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(IncorrectEntityException.class)
    public ResponseEntity<ExceptionResponse> handlerException(IncorrectEntityException exception) {
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(
                        enumToStringLocaleMessage(exception.getErrorMessage()),
                        enumListToStringList(exception.getValidationErrors()),
                        LocalDateTime.now().toString(),
                        ERROR_CODE_0001)
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerException(EntityNotFoundException exception) {
        return new ResponseEntity<ExceptionResponse>(
                new ExceptionResponse(
                        enumToStringLocaleMessage(exception.getErrorMessage()),
                        Arrays.asList(exception.getId().toString()),
                        LocalDateTime.now().toString(),
                        ERROR_CODE_0002)
                , HttpStatus.NOT_FOUND);
    }

    private List<String> enumListToStringList(List<ValidationError> validationErrors) {
        List<String> strings = new ArrayList<>();
        for (ValidationError error : validationErrors) {
            strings.add(enumToStringLocaleMessage(error));
        }
        return strings;
    }

    private String enumToStringLocaleMessage(ValidationError error) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(error.toString(), null, locale);
    }
}
