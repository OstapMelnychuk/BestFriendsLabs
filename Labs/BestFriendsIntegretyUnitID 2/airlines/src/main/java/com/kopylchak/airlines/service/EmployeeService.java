package com.kopylchak.airlines.service;

import com.kopylchak.airlines.dto.EmployeeResponseDto;
import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseDto> getEmployees();

    List<EmployeeResponseDto> getEmployees(String airlineName);
}
