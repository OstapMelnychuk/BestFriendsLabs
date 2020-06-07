package com.kopylchak.airlines.entity;

import javax.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Embeddable
@Builder
@Data
public class PersonalInfo {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String secondName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String homeAddress;

    @Tolerate
    public PersonalInfo() {
        // No args constructor.
    }
}
