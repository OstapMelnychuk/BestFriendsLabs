package com.kopylchak.airlines.service;

import com.kopylchak.airlines.dto.AirlineCreationDto;
import com.kopylchak.airlines.dto.AirlineResponseDto;
import com.kopylchak.airlines.dto.AirlineUpdateDto;
import com.kopylchak.airlines.dto.filter.AirlineFilterDto;
import java.util.List;

public interface AirlineService {
    List<AirlineResponseDto> getAirlines(AirlineFilterDto filter);

    AirlineResponseDto getAirline(String airlineName);

    AirlineResponseDto createAirline(AirlineCreationDto airlineDto);

    AirlineResponseDto updateAirline(AirlineUpdateDto airlineDto, String airlineName);
}
