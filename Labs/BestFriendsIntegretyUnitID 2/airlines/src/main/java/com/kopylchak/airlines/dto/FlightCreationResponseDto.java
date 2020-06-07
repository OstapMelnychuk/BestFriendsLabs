package com.kopylchak.airlines.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Builder
@Data
public class FlightCreationResponseDto {
    private Long id;
    private LocalDateTime plannedDepartureTime;
    private LocalDateTime plannedArrivalTime;
    private LocalDateTime actualDepartureTime;
    private LocalDateTime actualArrivalTime;
    private String gate;
    private Long planeId;
    private Long tripId;

    @Tolerate
    public FlightCreationResponseDto() {
        // No args constructor.
    }
}
