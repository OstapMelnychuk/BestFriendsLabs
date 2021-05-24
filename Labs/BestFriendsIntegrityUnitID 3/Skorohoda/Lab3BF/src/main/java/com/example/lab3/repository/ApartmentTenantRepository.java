package com.example.lab3.repository;

import com.example.lab3.entity.ApartmentTenant;
import com.example.lab3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApartmentTenantRepository extends JpaRepository<ApartmentTenant, Long> {
    Optional<ApartmentTenant> findByUser(User user);
}
