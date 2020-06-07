package com.kopylchak.airlines.resources;

import com.kopylchak.airlines.dto.*;
import com.kopylchak.airlines.dto.filter.AirlineFilterDto;
import com.kopylchak.airlines.service.AirlineService;
import com.kopylchak.airlines.service.EmployeeService;
import com.kopylchak.airlines.service.TripService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airlines")
public class AirlinesResource {
    private AirlineService airlineService;
    private EmployeeService employeeService;
    private TripService tripService;

    @Autowired
    public AirlinesResource(AirlineService airlineService, EmployeeService employeeService, TripService tripService) {
        this.airlineService = airlineService;
        this.employeeService = employeeService;
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<AirlineResponseDto>> getAirlines(AirlineFilterDto filter) {
        return ResponseEntity.ok(airlineService.getAirlines(filter));
    }

    @GetMapping("/{airlineName}")
    public ResponseEntity<AirlineResponseDto> getAirline(@PathVariable String airlineName) {
        return ResponseEntity.ok(airlineService.getAirline(airlineName));
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping
    public ResponseEntity<AirlineResponseDto> createAirline(@Validated AirlineCreationDto airlineDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(airlineService.createAirline(airlineDto));
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PutMapping("/{airlineName}")
    public ResponseEntity<AirlineResponseDto> updateAirline(@PathVariable String airlineName,
                                                            AirlineUpdateDto airlineDto) {
        return ResponseEntity.ok(airlineService.updateAirline(airlineDto, airlineName));
    }

    @GetMapping("{airlineName}/trips")
    public ResponseEntity<List<TripResponseDto>> getTrips(@PathVariable String airlineName) {
        return ResponseEntity.ok(tripService.getTrips(airlineName));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'DISPATCHER', 'OWNER')")
    @GetMapping("{airlineName}/employees")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployees(@PathVariable String airlineName) {
        return ResponseEntity.ok(employeeService.getEmployees(airlineName));
    }
}
