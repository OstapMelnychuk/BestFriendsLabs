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
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isDone;

    @OneToOne
    Request request;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "worker_plan",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    List<Worker> workers;
}
