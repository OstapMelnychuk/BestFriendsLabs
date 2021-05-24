package com.example.lab3.repository;

import com.example.lab3.entity.User;
import com.example.lab3.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Optional<Worker> findByUser(User user);
}
