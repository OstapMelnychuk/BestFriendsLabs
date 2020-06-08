package com.example.lab3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerRequestDto {
    private String name;
    private String surname;
    private String phoneNumber;
    private UserRequestDto userRequestDto;
}
