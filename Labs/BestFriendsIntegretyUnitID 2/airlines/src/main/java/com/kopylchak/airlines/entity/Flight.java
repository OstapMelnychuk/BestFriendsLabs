package com.kopylchak.airlines.entity;

import com.kopylchak.airlines.entity.enums.FlightStatus;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "flights")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDateTime actualArrivalTime;
    @Column(nullable = false)
    private LocalDateTime plannedArrivalTime;
    @Column(nullable = false)
    private LocalDateTime plannedDepartureTime;
    private LocalDateTime actualDepartureTime;
    @Column(nullable = false)
    private String gate;
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;

    @ManyToMany
    private List<Employee> employees;
    @OneToMany
    private List<Ticket> tickets;
    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Plane plane;
    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Trip trip;
    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Airline airline;
}