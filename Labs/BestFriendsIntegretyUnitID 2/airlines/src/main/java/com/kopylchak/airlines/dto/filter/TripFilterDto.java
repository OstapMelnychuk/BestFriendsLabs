package com.kopylchak.airlines.dto.filter;

import lombok.Data;

@Data
public class TripFilterDto {
    private String departureCountry;
    private String departureCity;
        private String departureAddress;
    private String arrivalCountry;
    private String arrivalCity;
    private String arrivalAddress;
    private Boolean bothSides;
}
