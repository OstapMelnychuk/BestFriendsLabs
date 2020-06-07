package com.kopylchak.airlines.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;

@Entity(name = "roles")
@AllArgsConstructor
@Data
public class Role {
    @Id
    @Column(unique = true)
    private String name;

    @Tolerate
    public Role() {
        // No args constructor.
    }
}
