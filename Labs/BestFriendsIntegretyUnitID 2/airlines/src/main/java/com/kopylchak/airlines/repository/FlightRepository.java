package com.kopylchak.airlines.repository;

import com.kopylchak.airlines.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
