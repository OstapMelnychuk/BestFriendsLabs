package com.savchuk.secondlab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.savchuk.secondlab.entity.EntityStatus.ACTIVE;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String description;
    private String data;
    private boolean status = ACTIVE.getStatus();

    @ManyToOne()
    private Customer customer;

    @ManyToOne()
    private Shop shop;
}
