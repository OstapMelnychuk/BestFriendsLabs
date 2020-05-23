package com.savchuk.secondlab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

import static com.savchuk.secondlab.entity.EntityStatus.ACTIVE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private boolean status = ACTIVE.getStatus();

    @OneToMany()
    private List<Order> orders;
}
