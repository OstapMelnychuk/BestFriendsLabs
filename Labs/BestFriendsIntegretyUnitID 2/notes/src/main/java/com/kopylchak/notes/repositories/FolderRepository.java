package com.kopylchak.notes.repositories;

import com.kopylchak.notes.entities.Folder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    boolean existsByName(String name);

    List<Folder> findAllByUserId(long userId);
}