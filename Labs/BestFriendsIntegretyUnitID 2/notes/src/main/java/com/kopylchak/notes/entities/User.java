package com.kopylchak.notes.entities;

import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nick;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Folder> folders;
}