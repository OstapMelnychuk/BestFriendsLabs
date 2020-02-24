package com.kopylchak.notes.repositories;

import com.kopylchak.notes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNick(String nick);
}