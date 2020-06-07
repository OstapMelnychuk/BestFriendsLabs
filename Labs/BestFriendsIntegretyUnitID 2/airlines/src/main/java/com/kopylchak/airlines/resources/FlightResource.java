package com.kopylchak.airlines.resources;

import com.kopylchak.airlines.dto.FlightCreationRequestDto;
import com.kopylchak.airlines.dto.FlightCreationResponseDto;
import com.kopylchak.airlines.dto.FlightResponseDto;
import com.kopylchak.airlines.entity.enums.FlightStatus;
import com.kopylchak.airlines.service.FlightService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class FlightResource {
    private FlightService flightService;

    @Autowired
    public FlightResource(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<FlightResponseDto>> getFlights() {
        return ResponseEntity.ok(flightService.getFlights());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<FlightCreationResponseDto> createFlight(FlightCreationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(dto));
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/{flightId}")
    public ResponseEntity<FlightCreationResponseDto> updateFlight(@PathVariable Long flightId,
                                                                  FlightCreationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.updateFlight(flightId, dto));
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/{flightId}")
    public ResponseEntity<Object> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('DISPATCHER')")
    @PostMapping("/{flightId}/brigade")
    public ResponseEntity<FlightResponseDto> setBrigade(@PathVariable Long flightId,
                                                        @RequestBody List<Long> employeeIds) {
        return ResponseEntity.ok(flightService.setBrigade(flightId, employeeIds));
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PatchMapping("{flightId}/status")
    public ResponseEntity<FlightResponseDto> updateFlightStatus(@PathVariable Long flightId,
                                                                @RequestBody FlightStatus flightStatus) {
        return ResponseEntity.ok(flightService.updateFlightStatus(flightId, flightStatus));
    }
}
