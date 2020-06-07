package com.kopylchak.airlines.repository;

import com.kopylchak.airlines.entity.Plane;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Long> {
    @Query(value = "SELECT COUNT(*) = 0 FROM planes p\n" +
        "INNER JOIN flights f\n" +
        "ON p.id = f.plane_id\n" +
        "WHERE ((:startDateTime <= f.planned_arrival_time) AND (:endDateTime >= f.planned_departure_time))\n" +
        "AND p.id = :planeId", nativeQuery = true)
    boolean checkIfPlaneIsAvailable(long planeId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
