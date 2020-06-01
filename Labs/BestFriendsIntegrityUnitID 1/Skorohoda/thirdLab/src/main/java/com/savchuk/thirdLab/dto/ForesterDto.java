package com.savchuk.thirdLab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForesterDto {
    private Long id;
    private String name;
    private String secondName;
    private String description;
    private Date dateOfEmployment;
    private String mail;
}
