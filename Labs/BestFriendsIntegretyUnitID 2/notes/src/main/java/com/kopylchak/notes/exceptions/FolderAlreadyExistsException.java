package com.kopylchak.notes.exceptions;

public class FolderAlreadyExistsException extends RuntimeException {
    public FolderAlreadyExistsException() {
        super();
    }

    public FolderAlreadyExistsException(String message) {
        super(message);
    }
}
