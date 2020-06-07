package com.kopylchak.airlines.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.Data;


@Embeddable
@Data
public class Location {
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String address;
    private Coordinates coordinates;
}
