package com.kopylchak.airlines.repository;

import com.kopylchak.airlines.entity.Employee;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT COUNT (e) FROM employees e WHERE e.id IN :ids")
    long findCountInIdList(long[] ids);

    default boolean existsAllByIds(long[] ids) {
        return findCountInIdList(ids) == ids.length;
    }

    @Query(value = "SELECT count(*) = 0 FROM flights f\n" +
        "INNER JOIN flights_employees fe\n" +
        "ON fe.flights_id = f.id\n" +
        "WHERE (:departureTime <= f.planned_arrival_time) AND (:arrivalTime >= f.planned_departure_time)" +
        "AND fe.employees_id IN(:employeesId)", nativeQuery = true)
    boolean areAvailable(LocalDateTime departureTime, LocalDateTime arrivalTime, List<Long> employeesId);

    Optional<Employee> findByPersonalInfoEmail(String email);

    boolean existsByRefreshTokenAndPersonalInfoEmail(String refreshToken, String email);

    List<Employee> findByAirlineName(String airlineName);
}