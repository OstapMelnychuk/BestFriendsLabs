package com.savchuk.secondlab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import static com.savchuk.secondlab.entity.EntityStatus.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String country;
    private String city;
    private boolean status = ACTIVE.getStatus();

    @OneToMany()
    private List<Order> orders;
}
