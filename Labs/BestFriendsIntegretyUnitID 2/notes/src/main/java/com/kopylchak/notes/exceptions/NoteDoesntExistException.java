package com.kopylchak.notes.exceptions;

public class NoteDoesntExistException extends RuntimeException {
    public NoteDoesntExistException() {
        super();
    }

    public NoteDoesntExistException(String message) {
        super(message);
    }
}
