package com.kopylchak.airlines.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

@Entity(name = "trips")
@Data
@Builder
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Airport departureAirport;
    @ManyToOne
    private Airport arrivalAirport;
    @NotNull
    private Boolean bothSides;

    @Tolerate
    public Trip() {
        // No args constructor.
    }
}