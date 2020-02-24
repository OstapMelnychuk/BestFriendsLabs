package com.kopylchak.notes.dto;

import static com.kopylchak.notes.constants.AppConstants.*;
import static com.kopylchak.notes.constants.ValidationErrorMessages.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotNull(message = USER_NICK_CAN_NOT_BE_NULL)
    @Size(min = MIN_USER_NICK_LENGTH, max = MAX_USER_NICK_LENGTH, message = USER_NICK_LENGTH_SHOULD_BE)
    @Pattern(regexp = USER_NICK_REGEX, message = USER_NICK_SHOULD_MATCH)
    private String nick;
}