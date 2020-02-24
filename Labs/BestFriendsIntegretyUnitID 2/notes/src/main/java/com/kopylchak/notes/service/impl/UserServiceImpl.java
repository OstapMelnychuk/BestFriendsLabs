package com.kopylchak.notes.service.impl;

import static com.kopylchak.notes.constants.ErrorMessages.*;

import com.kopylchak.notes.dto.*;
import com.kopylchak.notes.entities.Folder;
import com.kopylchak.notes.entities.Note;
import com.kopylchak.notes.entities.User;
import com.kopylchak.notes.exceptions.*;
import com.kopylchak.notes.repositories.FolderRepository;
import com.kopylchak.notes.repositories.NoteRepository;
import com.kopylchak.notes.repositories.UserRepository;
import com.kopylchak.notes.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private FolderRepository folderRepository;
    private NoteRepository noteRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           FolderRepository folderRepository,
                           NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.folderRepository = folderRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public UserResponseDto findUserById(long userId) {
        return modelMapper.map(userRepository.findById(userId)
                .orElseThrow(() -> new UserDoesntExistException(String.format(USER_DOESNT_EXIST_BY_ID, userId))),
            UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(u -> modelMapper.map(u, UserResponseDto.class))
            .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserResponseDto saveUser(UserRequestDto userDto) {
        if (userRepository.existsByNick(userDto.getNick())) {
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS_WITH_NICK, userDto.getNick()));
        }

        User user = userRepository.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public UserResponseDto updateUser(long id, UserRequestDto userDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserDoesntExistException(String.format(USER_DOESNT_EXIST_BY_ID, id)));

        if (userDto.getNick().equals(user.getNick())) {
            return modelMapper.map(user, UserResponseDto.class);
        }

        user.setNick(userDto.getNick());
        if (userRepository.existsByNick(user.getNick())) {
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS_WITH_NICK, user.getNick()));
        }

        userRepository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<FolderResponseDto> findAllUserFolders(long userId) {
        return folderRepository.findAllByUserId(userId)
            .stream()
            .map(f -> modelMapper.map(f, FolderResponseDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public FolderResponseDto saveFolder(long userId, FolderRequestDto folderDto) {
        if (folderRepository.existsByName(folderDto.getName())) {
            throw new FolderAlreadyExistsException(String.format(FOLDER_ALREADY_EXISTS_WITH_NAME, folderDto.getName()));
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserDoesntExistException(String.format(USER_DOESNT_EXIST_BY_ID, userId)));

        Folder folder = modelMapper.map(folderDto, Folder.class);
        folder.setUser(user);

        return modelMapper.map(folderRepository.save(folder), FolderResponseDto.class);
    }

    @Override
    public void deleteFolder(long folderId) {
        try {
            folderRepository.deleteById(folderId);
        } catch (EmptyResultDataAccessException e) {
            throw new FolderDoesntExist(String.format(FOLDER_DOESNT_EXIST_BY_ID, folderId));
        }
    }

    @Override
    public FolderResponseDto updateFolder(long folderId, FolderRequestDto folderDto) {
        if (folderRepository.existsByName(folderDto.getName())) {
            throw new FolderAlreadyExistsException(String.format(FOLDER_ALREADY_EXISTS_WITH_NAME, folderDto.getName()));
        }

        Folder folder = folderRepository.findById(folderId)
            .orElseThrow(() -> new FolderDoesntExist(String.format(FOLDER_DOESNT_EXIST_BY_ID, folderId)));
        folder.setName(folderDto.getName());

        return modelMapper.map(folderRepository.save(folder), FolderResponseDto.class);
    }

    @Override
    public List<NoteResponseDto> findAllUserNotes(long userId, Pageable pageable) {
        return noteRepository.findAllByFolderUserId(userId, pageable)
            .stream()
            .map(n -> modelMapper.map(n, NoteResponseDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<NoteResponseDto> findAllUserNotesInFolder(long folderId, Pageable pageable) {
        return noteRepository.findAllByFolderId(folderId, pageable)
            .stream()
            .map(n -> modelMapper.map(n, NoteResponseDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public NoteResponseDto saveNote(long folderId, NoteRequestDto noteDto) {
        Folder folder = folderRepository.findById(folderId)
            .orElseThrow(() -> new FolderDoesntExist(String.format(FOLDER_DOESNT_EXIST_BY_ID, folderId)));

        Note note = modelMapper.map(noteDto, Note.class);
        note.setFolder(folder);

        return modelMapper.map(noteRepository.save(note), NoteResponseDto.class);
    }

    @Override
    public void deleteNote(long noteId) {
        try {
            noteRepository.deleteById(noteId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoteDoesntExistException(String.format(NOTE_DOESNT_EXIST_BY_ID, noteId));
        }
    }
}