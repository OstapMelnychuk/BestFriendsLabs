package com.kopylchak.airlines.resources;

import com.kopylchak.airlines.dto.TripResponseDto;
import com.kopylchak.airlines.service.TripService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
public class TripResource {
    private TripService tripService;

    public TripResource(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<TripResponseDto>> getTrips() {
        return ResponseEntity.ok(tripService.getTrips());
    }
}
