package com.kopylchak.airlines.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;

@Entity(name = "positions")
@AllArgsConstructor
@Data
public class Position {
    @Id
    private String name;

    @Tolerate
    public Position() {
        // No args constructor.
    }
}
