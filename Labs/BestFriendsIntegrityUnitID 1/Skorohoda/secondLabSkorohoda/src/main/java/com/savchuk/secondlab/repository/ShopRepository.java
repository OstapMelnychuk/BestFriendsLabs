package com.savchuk.secondlab.repository;

import com.savchuk.secondlab.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByName(String name);
    @Modifying
    @Transactional
    @Query("UPDATE Shop SET status = :status WHERE id = :id")
    void changeStatus(Long id, boolean status);
    boolean existsById(Long id);
}
