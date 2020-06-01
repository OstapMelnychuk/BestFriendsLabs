package com.savchuk.thirdLab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionRequestDto {
    private String name;
    private String description;
    private String data;
    private Long ownerID;
    private Long foresterID;
    private String answerOfForester;
    private List<Long> plantsID;
}
