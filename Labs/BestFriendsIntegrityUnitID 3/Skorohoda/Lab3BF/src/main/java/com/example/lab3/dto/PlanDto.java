package com.example.lab3.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {
    private Long id;
    private boolean isDone;
    private Long requestId;
    private List<WorkerDto> workers;
}
