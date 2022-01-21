package com.epam.esm.contollers;

import com.epam.esm.constant.ErrorMessagesConstant;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.LoginException;
import com.epam.esm.exception.RegistrationException;
import com.epam.esm.exception.UsedEntityException;
import com.epam.esm.model.impl.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class ControllerAdvice {
    private static final String ERROR_CODE_0001 = "Error: 0001";
    private static final String ERROR_CODE_0002 = "Error: 0002";
    private static final String ERROR_CODE_0003 = "Error: 0003";
    private static final String ERROR_CODE_0004 = "Error: 0004";
    private static final String ERROR_CODE_0005 = "Error: 0005";
    private static final String ERROR_CODE_0006 = "Error: 0006";
    private static final String ERROR_CODE_0007 = "Error: 0007";
    private static final String ERROR_CODE_0404 = "Error: 0404";
    private static final String ERROR_CODE_0404_MESSAGE = "The resource can not be found ";
    private static final String INPUT_DATA_IS_NOT_CORRECT = "input.parameter.is.not.correct";

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public ControllerAdvice(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerException(EntityNotFoundException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        stringLocaleMessage(exception.getErrorMessage()),
                        Collections.singletonList(exception.getId().toString()),
                        LocalDateTime.now().toString(),
                        ERROR_CODE_0002)
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponse> handlerException(LoginException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        stringLocaleMessage(exception.getErrorMessage()),
                        Collections.singletonList(exception.getUserName()),
                        LocalDateTime.now().toString(),
                        ERROR_CODE_0006)
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ExceptionResponse> handlerException(RegistrationException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        stringLocaleMessage(exception.getErrorMessage()),
                        Collections.singletonList(exception.getUserName()),
                        LocalDateTime.now().toString(),
                        ERROR_CODE_0007)
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsedEntityException.class)
    public ResponseEntity<ExceptionResponse> handlerException(UsedEntityException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        stringLocaleMessage(ErrorMessagesConstant.ENTITY_USED_IN_SYSTEM),
                        Collections.singletonList(exception.getId().toString()),
                        LocalDateTime.now().toString(),
                        ERROR_CODE_0003)
                , HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ExceptionResponse> handlerException(UnsupportedOperationException exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        exception.getMessage(),
                        null,
                        LocalDateTime.now().toString(),
                        ERROR_CODE_0004)
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handlerException(MethodArgumentNotValidException exception) {

        List<String> errors = new ArrayList<String>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ExceptionResponse response = new ExceptionResponse();
        response.setIncorrectParameters(errors);
        response.setErrorCode(ERROR_CODE_0005);
        response.setErrorTime(LocalDateTime.now().toString());
        response.setErrorMessage(stringToStringLocaleMessage(INPUT_DATA_IS_NOT_CORRECT));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String stringToStringLocaleMessage(String message) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerException(Exception exception) {
        return new ResponseEntity<>(
                new ExceptionResponse(
                        ERROR_CODE_0404_MESSAGE,
                        null,
                        LocalDateTime.now().toString(),
                        ERROR_CODE_0404)
                , HttpStatus.NOT_FOUND);
    }

    private String stringLocaleMessage(String error) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(error, null, locale);
    }
}
