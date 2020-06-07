package com.kopylchak.airlines.service;

import com.kopylchak.airlines.dto.FlightCreationRequestDto;
import com.kopylchak.airlines.dto.FlightCreationResponseDto;
import com.kopylchak.airlines.dto.FlightResponseDto;
import com.kopylchak.airlines.entity.enums.FlightStatus;
import java.util.List;

public interface FlightService {
    List<FlightResponseDto> getFlights();

    FlightCreationResponseDto createFlight(FlightCreationRequestDto dto);

    FlightCreationResponseDto updateFlight(long flightId, FlightCreationRequestDto dto);

    void deleteFlight(long flightId);

    FlightResponseDto setBrigade(long flightId, List<Long> employeeIds);

    FlightResponseDto updateFlightStatus(long flightId, FlightStatus flightStatus);
}
