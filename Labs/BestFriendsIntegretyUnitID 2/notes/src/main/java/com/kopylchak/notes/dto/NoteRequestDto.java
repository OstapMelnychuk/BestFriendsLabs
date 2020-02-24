package com.kopylchak.notes.dto;

import com.kopylchak.notes.constants.AppConstants;
import com.kopylchak.notes.constants.ValidationErrorMessages;
import javax.validation.constraints.Max;
import lombok.Data;

@Data
public class NoteRequestDto {
    @Max(value = AppConstants.MAX_NOTE_LENGTH, message = ValidationErrorMessages.NOTE_CONTENT_MESSAGE)
    private String content;
}
