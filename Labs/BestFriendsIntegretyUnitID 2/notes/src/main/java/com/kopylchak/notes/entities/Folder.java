package com.kopylchak.notes.entities;

import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "folder")
    private List<Note> notes;

    @ManyToOne
    private User user;
}