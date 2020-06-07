package com.kopylchak.airlines.dto;

import com.kopylchak.airlines.entity.Coordinates;
import lombok.Data;

@Data
public class OfficeLocationDto {
    private String country;
    private String city;
    private String address;
    private Coordinates coordinates;
}
