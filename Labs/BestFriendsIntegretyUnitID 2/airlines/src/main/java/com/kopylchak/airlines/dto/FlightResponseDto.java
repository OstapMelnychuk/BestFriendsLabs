package com.kopylchak.airlines.dto;

import com.kopylchak.airlines.entity.enums.FlightStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class FlightResponseDto {
    private Long id;
    private LocalDateTime actualArrivalTime;
    private LocalDateTime plannedDepartureTime;
    private LocalDateTime actualDepartureTime;
    private String gate;
    private FlightStatus flightStatus;
    private TripResponseDto trip;
    private List<EmployeeResponseDto> employees;
}
