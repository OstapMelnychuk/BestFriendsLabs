package com.kopylchak.airlines.entity;

import com.kopylchak.airlines.entity.enums.FlightClass;
import javax.persistence.*;
import lombok.Data;

@Entity(name = "tickets")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private FlightClass flightClass;
    private PersonalInfo personalInfo;

    @ManyToOne(optional = false)
    private Flight flight;
}
