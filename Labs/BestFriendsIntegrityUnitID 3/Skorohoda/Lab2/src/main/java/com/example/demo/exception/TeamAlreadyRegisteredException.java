package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TeamAlreadyRegisteredException extends RuntimeException {
    public TeamAlreadyRegisteredException(String message) {
        super(message);
    }
}
