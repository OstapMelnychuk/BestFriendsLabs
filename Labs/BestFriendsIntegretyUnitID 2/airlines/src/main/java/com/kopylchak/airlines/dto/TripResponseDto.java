package com.kopylchak.airlines.dto;

import lombok.Data;

@Data
public class TripResponseDto {
    private String departureCountry;
    private String departureCity;
    private String arrivalCountry;
    private String arrivalCity;
    private Boolean bothSides;
}
