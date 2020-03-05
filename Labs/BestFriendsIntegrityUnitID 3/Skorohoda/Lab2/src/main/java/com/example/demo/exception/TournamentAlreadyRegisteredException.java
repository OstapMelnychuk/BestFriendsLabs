package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TournamentAlreadyRegisteredException extends RuntimeException {
    public TournamentAlreadyRegisteredException(String message) {
        super(message);
    }
}
