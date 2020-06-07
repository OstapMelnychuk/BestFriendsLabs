package com.kopylchak.airlines.dto;

import com.kopylchak.airlines.entity.Location;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.Data;

@Data
public class AirlineResponseDto {
    @Id
    private String name;
    @Column(nullable = false)
    private Location location;
    @Column(unique = true)
    private String webSite;
}
