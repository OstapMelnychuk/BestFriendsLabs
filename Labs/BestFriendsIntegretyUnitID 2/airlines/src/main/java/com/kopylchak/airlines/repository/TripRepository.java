package com.kopylchak.airlines.repository;

import com.kopylchak.airlines.dto.filter.TripFilterDto;
import com.kopylchak.airlines.entity.Trip;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {
    @Query(value = "select t.id, t.departure_airport_id, t.arrival_airport_id, t.both_sides from trips as t\n" +
        "inner join airports as aa\n" +
        "on t.arrival_airport_id = aa.id\n" +
        "inner join airports as da\n" +
        "on t.departure_airport_id = da.id\n" +
        "where t.id in\n" +
        "(select trips_id from airlines_trips\n" +
        "WHERE airlines_name = :airlineName)\n" +
        "AND (:departureCountry IS NULL OR da.country LIKE CONCAT('%', :departureCountry, '%'))\n" +
        "AND (:departureCity IS NULL OR da.city LIKE CONCAT('%', :departureCity, '%'))\n" +
        "AND (:departureAddress IS NULL OR da.address LIKE CONCAT('%', :departureAddress, '%'))\n" +
        "AND (:arrivalCountry IS NULL OR aa.country LIKE CONCAT('%', :arrivalCountry, '%'))\n" +
        "AND (:arrivalCity IS NULL OR aa.city LIKE CONCAT('%', :arrivalCity, '%'))\n" +
        "AND (:arrivalAddress IS NULL OR aa.address LIKE CONCAT('%', :arrivalAddress, '%'))\n" +
        "AND (:bothSides IS NULL OR t.both_sides = :bothSides)", nativeQuery = true)
    List<Trip> findAll(String airlineName, String departureCountry, String departureCity,
                       String departureAddress, String arrivalCountry, String arrivalCity,
                       String arrivalAddress, boolean bothSides);

    @Query("SELECT a.trips FROM airlines a WHERE a.name = :airlineName")
    List<Trip> findAllByAirlineName(String airlineName);
}
