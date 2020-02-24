package com.kopylchak.notes.exceptions;

public class UserDoesntExistException extends RuntimeException {
    public UserDoesntExistException() {
        super();
    }

    public UserDoesntExistException(String message) {
        super(message);
    }
}
