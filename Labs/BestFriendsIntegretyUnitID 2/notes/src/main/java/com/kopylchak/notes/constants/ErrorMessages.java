package com.kopylchak.notes.constants;

public final class ErrorMessages {
    public static final String USER_ALREADY_EXISTS_WITH_NICK = "User with nick %s already exists.";
    public static final String FOLDER_ALREADY_EXISTS_WITH_NAME = "Folder with name %s already exists.";
    public static final String USER_DOESNT_EXIST_BY_ID = "User with id %d doesn't exist.";
    public static final String NOTE_DOESNT_EXIST_BY_ID = "Note with id %d doesn't exist.";
    public static final String FOLDER_DOESNT_EXIST_BY_ID = "Folder with id %d doesn't exist.";

    private ErrorMessages() {
    }
}
