package com.kopylchak.airlines.dto;

import lombok.Data;

@Data
public class EmployeeResponseDto {
    private Long id;
    private OfficeLocationDto officeLocation;
    private String airlineName;
    private String positionName;
    private PersonalInfoResponseDto personalInfo;
}
