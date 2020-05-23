package com.savchuk.secondlab.repository;

import com.savchuk.secondlab.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String name);
    @Modifying
    @Transactional
    @Query("UPDATE Customer SET status = :status WHERE id = :id")
    void changeStatus(Long id, boolean status);
    boolean existsByEmail(String email);
}
