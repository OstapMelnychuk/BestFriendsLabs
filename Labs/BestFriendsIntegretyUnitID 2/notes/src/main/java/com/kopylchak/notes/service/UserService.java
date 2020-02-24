package com.kopylchak.notes.service;

import com.kopylchak.notes.dto.*;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDto findUserById(long userId);

    List<UserResponseDto> findAllUsers();

    UserResponseDto saveUser(UserRequestDto user);

    UserResponseDto updateUser(long userId, UserRequestDto user);

    void deleteUser(long userId);

    List<FolderResponseDto> findAllUserFolders(long userId);

    FolderResponseDto saveFolder(long userId, FolderRequestDto folder);

    void deleteFolder(long folderId);

    FolderResponseDto updateFolder(long folderId, FolderRequestDto folderDto);

    List<NoteResponseDto> findAllUserNotes(long userId, Pageable pageable);

    List<NoteResponseDto> findAllUserNotesInFolder(long folderId, Pageable pageable);

    NoteResponseDto saveNote(long folderId, NoteRequestDto noteDto);

    void deleteNote(long noteId);
}