package com.savchuk.thirdLab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForesterRequestDto {
    private String name;
    private String secondName;
    private String description;
    private Date dateOfEmployment;
    private String password;
    private String mail;
}
