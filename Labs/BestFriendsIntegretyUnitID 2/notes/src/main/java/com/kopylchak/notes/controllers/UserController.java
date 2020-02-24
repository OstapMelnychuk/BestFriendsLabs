package com.kopylchak.notes.controllers;

import com.kopylchak.notes.anotations.ApiPageable;
import com.kopylchak.notes.dto.*;
import com.kopylchak.notes.service.UserService;
import com.kopylchak.notes.validation.UserIdConstraint;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Validated
@RestController
@RequestMapping("users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Returns all users")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @ApiOperation(value = "Returns user by given id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = UserResponseDto.class),
        @ApiResponse(code = 400, message = "User id doesn't exist", response = UserResponseDto.class)
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @ApiOperation(value = "Creates user")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "User created", response = UserResponseDto.class),
        @ApiResponse(code = 400, message = "User data is incorrect", response = UserResponseDto.class)
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto));
    }

    @ApiOperation(value = "Updates user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User updated", response = UserResponseDto.class),
        @ApiResponse(code = 400, message = "User data is incorrect or user with given id doesn't exist",
            response = UserResponseDto.class)
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId,
                                                      @Valid @RequestBody UserRequestDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    @ApiOperation(value = "Delete user by given userId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User deleted"),
        @ApiResponse(code = 400, message = "User with given id doesn't exist")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@UserIdConstraint @PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Returns all user folders by given userId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "User with given id doesn't exist")
    })
    @GetMapping("/{userId}/folders")
    public ResponseEntity<List<FolderResponseDto>> findAllUserFolders(@UserIdConstraint @PathVariable Long userId) {
        return ResponseEntity.ok(userService.findAllUserFolders(userId));
    }

    @ApiOperation(value = "Saves folder by given userId")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Folder saved", response = FolderResponseDto.class),
        @ApiResponse(code = 400, message = "User with given id doesn't exist or folder data is invalid")
    })
    @PostMapping("/{userId}/folders")
    public ResponseEntity<FolderResponseDto> saveFolder(@PathVariable Long userId,
                                                        @RequestBody FolderRequestDto folderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveFolder(userId, folderDto));
    }

    @ApiOperation(value = "Deletes folder by given userId and folderId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Folder deleted"),
        @ApiResponse(code = 400, message = "User with given id doesn't exist or folder with given id doesn't exist")
    })
    @DeleteMapping("/{userId}/folders/{folderId}")
    public ResponseEntity<Object> deleteFolder(@UserIdConstraint @PathVariable Long userId,
                                               @PathVariable Long folderId) {
        userService.deleteFolder(folderId);

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Updates folder by given userId, folderId and folder data")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Folder updated"),
        @ApiResponse(code = 400, message = "User with given id doesn't exist, folder with given id doesn't exist or " +
            "folder data is invalid")
    })
    @PutMapping("/{userId}/folders/{folderId}")
    public ResponseEntity<FolderResponseDto> updateFolder(@UserIdConstraint @PathVariable Long userId,
                                                          @PathVariable Long folderId,
                                                          @RequestBody FolderRequestDto folderDto) {
        return ResponseEntity.ok(userService.updateFolder(folderId, folderDto));
    }

    @ApiOperation(value = "Creates note for given userId, folderId and note data")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Note created"),
        @ApiResponse(code = 400, message = "User with given id doesn't exist, folder with given id doesn't exist or " +
            "note data is invalid")
    })
    @PostMapping("/{userId}/folders/{folderId}/notes")
    public ResponseEntity<NoteResponseDto> saveNoteInFolder(@PathVariable Long userId,
                                                            @PathVariable Long folderId,
                                                            @RequestBody NoteRequestDto noteDto) {
        return ResponseEntity.ok(userService.saveNote(folderId, noteDto));
    }

    @ApiOperation(value = "Returns all user notes for given userId and folderId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "User with given id doesn't exist, folder with given id doesn't exist or")
    })
    @ApiPageable
    @GetMapping("/{userId}/folders/{folderId}/notes")
    public ResponseEntity<List<NoteResponseDto>> findAllUserNotesInFolder(@UserIdConstraint @PathVariable Long userId,
                                                                          @PathVariable Long folderId,
                                                                          @ApiIgnore Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUserNotesInFolder(folderId, pageable));
    }

    @ApiOperation(value = "Returns all user notes for given userId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "User with given id doesn't exist")
    })
    @ApiPageable
    @GetMapping("/{userId}/notes")
    public ResponseEntity<List<NoteResponseDto>> findAllUserNotes(@PathVariable Long userId,
                                                                  @ApiIgnore Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUserNotes(userId, pageable));
    }

    @ApiOperation(value = "Deletes note for given userId, folderId and noteId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Note deleted"),
        @ApiResponse(code = 400, message = "User with given id doesn't exist, folder with given id doesn't exist or " +
            "noteId is invalid")
    })
    @DeleteMapping("/{userId}/folders/{folderId}/notes/{noteId}")
    public ResponseEntity<NoteResponseDto> deleteUserNote(@UserIdConstraint @PathVariable Long userId,
                                                          @PathVariable Long folderId,
                                                          @PathVariable Long noteId) {
        userService.deleteNote(noteId);

        return ResponseEntity.ok().build();
    }
}