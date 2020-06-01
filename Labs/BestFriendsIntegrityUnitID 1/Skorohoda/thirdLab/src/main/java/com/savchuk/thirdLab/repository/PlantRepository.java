package com.savchuk.thirdLab.repository;

import com.savchuk.thirdLab.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Plant SET status = :status WHERE id = :id")
    void changeStatus(Long id, String status);
}
