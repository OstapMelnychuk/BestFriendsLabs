package com.kopylchak.notes.constants;

import static com.kopylchak.notes.constants.AppConstants.*;

public final class ValidationErrorMessages {
    public static final String USER_DOESNT_EXIST = "User doesn't exist by given id.";
    public static final String USER_NICK_CAN_NOT_BE_NULL = "User nick can not be null.";
    public static final String USER_NICK_LENGTH_SHOULD_BE = "User nick length should contains from " +
        MIN_USER_NICK_LENGTH + " to " + MAX_USER_NICK_LENGTH + ".";
    public static final String USER_NICK_SHOULD_MATCH = "User nick should matches " + USER_NICK_REGEX + ".";
    public static final String NOTE_CONTENT_MESSAGE = "Note content should not contain more then " + MAX_NOTE_LENGTH +
        " characters.";
    public static final String FOLDER_NAME_MESSAGE = "Folder name should not contain more then " +
        MAX_FOLDER_NAME_LENGTH + " characters.";

    private ValidationErrorMessages() {
    }
}
