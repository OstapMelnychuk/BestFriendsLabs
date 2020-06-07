package com.kopylchak.airlines.dto;

import com.kopylchak.airlines.entity.PersonalInfo;
import java.util.List;
import lombok.Data;

@Data
public class SignUpRequestDto {
    private String password;
    private Long officeId;
    private String airlineName;
    private String position;
    private List<String> roles;
    private PersonalInfo personalInfo;
}
