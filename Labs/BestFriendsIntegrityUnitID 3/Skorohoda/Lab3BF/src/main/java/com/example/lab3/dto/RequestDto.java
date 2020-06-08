package com.example.lab3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private Long id;
    private String kindOfWork;
    private Integer desiredTime;
    private String scaleOfJob;
}
