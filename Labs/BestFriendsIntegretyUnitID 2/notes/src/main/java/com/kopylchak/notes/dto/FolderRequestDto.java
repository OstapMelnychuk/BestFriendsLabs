package com.kopylchak.notes.dto;

import com.kopylchak.notes.constants.AppConstants;
import com.kopylchak.notes.constants.ValidationErrorMessages;
import javax.validation.constraints.Max;
import lombok.Data;

@Data
public class FolderRequestDto {
    @Max(value = AppConstants.MAX_FOLDER_NAME_LENGTH, message = ValidationErrorMessages.FOLDER_NAME_MESSAGE)
    private String name;
}