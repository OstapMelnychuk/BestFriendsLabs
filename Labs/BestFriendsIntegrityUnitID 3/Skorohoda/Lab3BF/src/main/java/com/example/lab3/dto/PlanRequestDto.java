package com.example.lab3.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanRequestDto {
    private Long requestId;
    private List<Long> workersId;
}
