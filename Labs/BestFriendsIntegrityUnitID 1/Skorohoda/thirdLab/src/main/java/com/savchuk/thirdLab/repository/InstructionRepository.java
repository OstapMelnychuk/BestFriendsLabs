package com.savchuk.thirdLab.repository;

import com.savchuk.thirdLab.entity.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Instruction SET status = :status WHERE id = :id")
    void changeStatus(Long id, String status);
}
