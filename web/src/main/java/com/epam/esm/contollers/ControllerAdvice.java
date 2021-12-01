package com.epam.esm.contollers;

import com.epam.esm.impl.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {
    //    TODO - Test controller, only for first start
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handlerException(Exception exception) {
        return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(exception.getMessage(),
                LocalDateTime.now().toString(),
                "TestCode"), HttpStatus.OK);
    }
}
