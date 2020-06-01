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
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String description;
    private String data;
    private String answerOfForester = "";

    private String status = ACTIVE.getStatus();

    @ManyToOne()
    private Owner owner;

    @ManyToOne()
    private Forester forester;

    @OneToMany()
    private List<Plant> plants;
}
