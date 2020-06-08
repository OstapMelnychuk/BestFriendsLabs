package com.example.lab3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kindOfWork;
    private Integer desiredTime;
    private String scaleOfJob;
    @ManyToOne
    private ApartmentTenant apartmentTenant;
}
