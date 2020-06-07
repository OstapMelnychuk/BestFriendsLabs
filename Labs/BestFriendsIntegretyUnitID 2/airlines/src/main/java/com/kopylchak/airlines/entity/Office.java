package com.kopylchak.airlines.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Entity(name = "offices")
@Builder
@Data
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private Location location;
    @ManyToOne
    private Airline airline;
    @OneToMany(mappedBy = "office", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Employee> employees;

    @Tolerate
    public Office() {
        // No args constructor.
    }
}
