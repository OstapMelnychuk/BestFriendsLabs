package com.kopylchak.airlines.repository;

import com.kopylchak.airlines.entity.Airline;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, String>, JpaSpecificationExecutor<Airline> {
    static Specification<Airline> nameContains(String name) {
        return (airline, cq, cb) -> cb.like(cb.lower(airline.get("name")), "%" + name.toLowerCase() + "%");
    }

    static Specification<Airline> addressContains(String address) {
        return (airline, cq, cb) -> cb.like(cb.lower(airline.get("location").get("address")),
            "%" + address.toLowerCase() + "%");
    }
}
