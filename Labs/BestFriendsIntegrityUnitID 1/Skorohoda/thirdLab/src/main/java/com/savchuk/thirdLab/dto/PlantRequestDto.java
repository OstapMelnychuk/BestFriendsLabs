package com.savchuk.thirdLab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.savchuk.thirdLab.entity.EntityStatus.ACTIVE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantRequestDto {
    private String type;
    private String location;
    private String status = ACTIVE.getStatus();
    private Long instructionID;
}
