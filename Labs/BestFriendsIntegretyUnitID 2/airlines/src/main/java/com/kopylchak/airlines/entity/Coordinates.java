package com.kopylchak.airlines.entity;

import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Coordinates {
    private Double attitude;
    private Double longitude;
}
