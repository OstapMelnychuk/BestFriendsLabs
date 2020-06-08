package com.example.lab3.repository;

import com.example.lab3.entity.ApartmentTenant;
import com.example.lab3.entity.Dispatcher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispatcherRepository extends JpaRepository<Dispatcher, Long> {
}
