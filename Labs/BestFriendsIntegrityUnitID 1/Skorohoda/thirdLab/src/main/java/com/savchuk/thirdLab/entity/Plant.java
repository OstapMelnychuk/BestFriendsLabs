package com.savchuk.thirdLab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.savchuk.thirdLab.entity.EntityStatus.ACTIVE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String type;
    private String location;

    private String status = ACTIVE.getStatus();

    @ManyToOne()
    private Instruction instruction;
}
