package com.kopylchak.airlines.service;

import com.kopylchak.airlines.dto.TripResponseDto;
import java.util.List;

public interface TripService {
    List<TripResponseDto> getTrips(String airlineName);

    List<TripResponseDto> getTrips();
}
