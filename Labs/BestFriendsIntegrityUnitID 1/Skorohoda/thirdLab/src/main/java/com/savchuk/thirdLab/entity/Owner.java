package com.savchuk.thirdLab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static com.savchuk.thirdLab.entity.EntityStatus.ACTIVE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String secondName;
    private String description;
    private String nameOfPark;
    private String password;

    private String status = ACTIVE.getStatus();

    @OneToMany()
    private List<Instruction> instructions;
}
