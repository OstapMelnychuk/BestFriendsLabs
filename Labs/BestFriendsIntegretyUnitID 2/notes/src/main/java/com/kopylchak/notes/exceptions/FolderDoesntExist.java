package com.kopylchak.notes.exceptions;

public class FolderDoesntExist extends RuntimeException {
    public FolderDoesntExist() {
        super();
    }

    public FolderDoesntExist(String message) {
        super(message);
    }
}
