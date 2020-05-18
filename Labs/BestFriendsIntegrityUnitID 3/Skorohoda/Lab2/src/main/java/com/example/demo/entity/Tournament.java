package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {
    @Id
    private Long id;
    private String name;
    private String country;
    private String city;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tournament_user",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;
}
