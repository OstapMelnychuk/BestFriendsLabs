package com.savchuk.thirdLab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.savchuk.thirdLab.entity.EntityStatus.ACTIVE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionDto {
    private Long id;
    private String name;
    private String description;
    private String data;
    private Long foresterID;
    private List<Long> plantsID;
    private String answerOfForester;
    private String status = ACTIVE.getStatus();
}
