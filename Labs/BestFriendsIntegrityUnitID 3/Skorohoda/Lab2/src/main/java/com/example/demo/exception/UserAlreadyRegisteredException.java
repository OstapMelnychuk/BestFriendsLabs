package com.example.demo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String message){
        super(message);
    }
}
