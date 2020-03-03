package com.example.demo.dto;

import com.example.demo.entity.Team;
import com.example.demo.entity.Tournament;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    Long id;
    String name;
    String email;
    List<Team> teams;
    List<Tournament> tournaments;
}
