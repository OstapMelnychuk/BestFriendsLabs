package com.savchuk.thirdLab.exception;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Component
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundException(NotFoundException ex, WebRequest request) {
        com.savchuk.thirdLab.exception.ErrorDetails errorDetails = new com.savchuk.thirdLab.exception.ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> myExceptionHandler(Exception ex, WebRequest request) {
        com.savchuk.thirdLab.exception.ErrorDetails errorDetails = new com.savchuk.thirdLab.exception.ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
