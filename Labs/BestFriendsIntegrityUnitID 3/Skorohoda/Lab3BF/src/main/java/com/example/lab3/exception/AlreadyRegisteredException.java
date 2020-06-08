package com.example.lab3.exception;

public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
