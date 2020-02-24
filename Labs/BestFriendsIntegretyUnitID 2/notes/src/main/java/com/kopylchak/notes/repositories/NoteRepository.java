package com.kopylchak.notes.repositories;

import com.kopylchak.notes.entities.Note;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
    List<Note> findAllByFolderId(long folderId, Pageable pageable);

    List<Note> findAllByFolderUserId(long userId, Pageable pageable);
}