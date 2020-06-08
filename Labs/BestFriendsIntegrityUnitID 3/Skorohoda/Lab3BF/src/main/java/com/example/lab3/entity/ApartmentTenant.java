package com.example.lab3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentTenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String address;

    @OneToMany(mappedBy = "apartmentTenant")
    private List<Request> requests;

    //@OneToOne(cascade = {CascadeType.ALL})
    @OneToOne
    private User user;

}
