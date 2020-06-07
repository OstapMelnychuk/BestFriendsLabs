package com.kopylchak.airlines.entity;

import javax.persistence.*;
import lombok.Data;

@Entity(name = "airports")
@Data
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private Location location;
}
