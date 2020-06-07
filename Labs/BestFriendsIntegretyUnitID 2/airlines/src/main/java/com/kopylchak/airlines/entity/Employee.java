package com.kopylchak.airlines.entity;

import com.kopylchak.airlines.service.SecurityService;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "employees")
@Builder
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @PastOrPresent
    @CreationTimestamp
    private LocalDate hireDate;
    @ManyToOne
    private Office office;
    @ManyToOne
    private Airline airline;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Position position;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private PersonalInfo personalInfo;
    @Column(nullable = false)
    private String password;
    private String refreshToken;

    @Tolerate
    public Employee() {
        // No args constructor.
    }
}
