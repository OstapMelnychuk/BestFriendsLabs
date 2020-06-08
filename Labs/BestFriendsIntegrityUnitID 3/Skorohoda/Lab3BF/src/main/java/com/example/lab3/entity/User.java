package com.example.lab3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String email;
    private String password;
    private String roles;
    private String permissions;
    private boolean isActive = true;

    public List<String> getPermissionList(){
        if (this.permissions.length() > 0){
            return Arrays.asList(permissions.split(","));
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<String> getRolesList(){
        if (this.roles.length() > 0){
            return Arrays.asList(roles.split(","));
        }
        else {
            return new ArrayList<>();
        }
    }
}
