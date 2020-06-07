package com.kopylchak.airlines.service.impl;

import com.kopylchak.airlines.dto.EmployeeResponseDto;
import com.kopylchak.airlines.dto.FlightCreationRequestDto;
import com.kopylchak.airlines.dto.FlightCreationResponseDto;
import com.kopylchak.airlines.dto.FlightResponseDto;
import com.kopylchak.airlines.entity.Employee;
import com.kopylchak.airlines.entity.Flight;
import com.kopylchak.airlines.entity.enums.FlightStatus;
import com.kopylchak.airlines.repository.*;
import com.kopylchak.airlines.service.FlightService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightServiceImpl implements FlightService {
    private FlightRepository flightRepository;
    private AirlineRepository airlineRepository;
    private PlaneRepository planeRepository;
    private TripRepository tripRepository;
    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository,
                             AirlineRepository airlineRepository,
                             PlaneRepository planeRepository,
                             TripRepository tripRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.flightRepository = flightRepository;
        this.airlineRepository = airlineRepository;
        this.planeRepository = planeRepository;
        this.tripRepository = tripRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<FlightResponseDto> getFlights() {
        return flightRepository.findAll().stream()
            .map(f -> modelMapper.map(f, FlightResponseDto.class))
            .collect(Collectors.toList());
    }

    // TODO fetch airline for current user from database instead of passing as dto field.
    @Override
    public FlightCreationResponseDto createFlight(FlightCreationRequestDto dto) {
        validateFlightDto(dto);

        Flight flight = modelMapper.map(dto, Flight.class);

        flight.setFlightStatus(FlightStatus.CREATED);

        return modelMapper.map(flightRepository.save(flight), FlightCreationResponseDto.class);
    }

    @Override
    public FlightCreationResponseDto updateFlight(long flightId, FlightCreationRequestDto dto) {
        validateFlightDto(dto);

        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("There is no flight with id %d", flightId)));

        if (!flight.getFlightStatus().equals(FlightStatus.CREATED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Only flight with status CREATED can be updated.");
        }

        modelMapper.map(dto, flight);

        if (dto.getPlaneId() != null) {
            flight.setPlane(planeRepository.findById(dto.getPlaneId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("There is no plane with id %d", dto.getPlaneId()))));
        }

        if (dto.getTripId() != null) {
            flight.setTrip(tripRepository.findById(dto.getTripId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("There is no trip with id %d", dto.getTripId()))));
        }

        return modelMapper.map(flightRepository.save(flight), FlightCreationResponseDto.class);
    }

    @Override
    public void deleteFlight(long flightId) {
        if (!flightRepository.existsById(flightId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No flight width id %d", flightId));
        }

        flightRepository.deleteById(flightId);
    }

    @Override
    public FlightResponseDto setBrigade(long flightId, List<Long> employeeIds) {
        if (!employeeRepository.existsAllByIds(employeeIds.stream().mapToLong(id -> id).toArray())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more employee id is invalid.");
        }

        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Flight with id %d doesn't exist", flightId)));

        if (!employeeRepository.areAvailable(flight.getPlannedDepartureTime(), flight.getPlannedArrivalTime(),
            employeeIds)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more employees are busy and can not be " +
                "set for given flight. Try choose another employees.");
        }

        if (!flight.getFlightStatus().equals(FlightStatus.CREATED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Flight status should be CREATED to set brigade.");
        }

        flight.setEmployees(employeeIds.stream()
            .map(e -> Employee.builder()
                .id(e)
                .build())
            .collect(Collectors.toList()));

        flight.setFlightStatus(FlightStatus.PLANNED);

        flightRepository.save(flight);

        return modelMapper.map(flight, FlightResponseDto.class);
    }

    @Override
    public FlightResponseDto updateFlightStatus(long flightId, FlightStatus flightStatus) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("There is no flight with id %d.", flightId)));

        if (!FlightStatus.UPDATABLE_STATUSES.contains(flight.getFlightStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("You can't update flight if it has status '%s'.", flightStatus.toString()));
        }

        if (!FlightStatus.UPDATABLE_STATUSES.contains(flightStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                String.format("You can't set '%s' status for flight.", flightStatus.toString()));
        }


        flight.setFlightStatus(flightStatus);

        return modelMapper.map(flight, FlightResponseDto.class);
    }

    private void validateFlightDto(FlightCreationRequestDto dto) {
        if (dto.getAirlineName() != null && !airlineRepository.existsById(dto.getAirlineName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("There is no %s airline company, " +
                "please specify existing company", dto.getAirlineName()));
        }

        if (dto.getPlaneId() != null && dto.getPlannedArrivalTime() != null && dto.getPlannedDepartureTime() != null &&
            !planeRepository.checkIfPlaneIsAvailable(dto.getPlaneId(), dto.getPlannedDepartureTime(),
                dto.getPlannedArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plane is busy for selected time range, " +
                "please choose other plane for flight.");
        }
    }
}
