package com.kopylchak.notes.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NoteResponseDto {
    private Long id;
    private String content;
    private LocalDateTime creationTime;
    private LocalDateTime updateTime;
}