package com.kopylchak.airlines.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class FlightCreationRequestDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime plannedDepartureTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime plannedArrivalTime;
    private String gate;
    private Long planeId;
    private Long tripId;
    private String airlineName;
}
