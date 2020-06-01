package com.savchuk.thirdLab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private Long id;
    private String name;
    private String secondName;
    private String description;
    private String nameOfPark;
}
