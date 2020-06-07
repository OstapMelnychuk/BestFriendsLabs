package com.kopylchak.airlines.service.impl;

import com.kopylchak.airlines.dto.EmployeeResponseDto;
import com.kopylchak.airlines.repository.AirlineRepository;
import com.kopylchak.airlines.repository.EmployeeRepository;
import com.kopylchak.airlines.service.EmployeeService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private AirlineRepository airlineRepository;
    private ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               AirlineRepository airlineRepository,
                               ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.airlineRepository = airlineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EmployeeResponseDto> getEmployees() {
        return employeeRepository.findAll().stream()
            .map(e -> modelMapper.map(e, EmployeeResponseDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDto> getEmployees(String airlineName) {
        if (!airlineRepository.existsById(airlineName)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("There is no airline with name %s", airlineName));
        }

        return employeeRepository.findByAirlineName(airlineName).stream()
            .map(e -> modelMapper.map(e, EmployeeResponseDto.class))
            .collect(Collectors.toList());
    }
}
