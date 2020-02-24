package com.kopylchak.notes.constants;

public final class AppConstants {
    public static final int MAX_NOTE_LENGTH = 10_000;
    public static final int MAX_FOLDER_NAME_LENGTH = 10_000;
    public static final int MIN_USER_NICK_LENGTH = 4;
    public static final int MAX_USER_NICK_LENGTH = 20;
    public static final String USER_NICK_REGEX = "[a-zA-Z]\\w+$";

    private AppConstants() {
    }
}
