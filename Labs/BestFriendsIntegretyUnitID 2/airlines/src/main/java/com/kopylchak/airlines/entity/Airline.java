package com.kopylchak.airlines.entity;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.*;
import lombok.experimental.Tolerate;

@Entity(name = "airlines")
@Builder
@Data
public class Airline {
    @Id
    private String name;
    @Column(nullable = false)
    private Location location;
    @Column(unique = true)
    private String webSite;

    @OneToMany(mappedBy = "airline")
    private List<Employee> employees;
    @OneToMany
    private Set<Trip> trips;

    @Tolerate
    public Airline() {
        // No args constructor.
    }
}
