package com.example.demo.controller;

import com.example.demo.constants.HttpStatuses;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.userMapper.UserMapper;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @ApiOperation(value = "Find all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = UserDTO.class)
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll().stream().map(userMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = UserDTO.class)
    })
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDTO> findOneById(@PathVariable("user_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(userService.findOneById(id)));
    }

    @ApiOperation(value = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTeamById(Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody User team) {
        userService.create(team);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT)
    })
    @PutMapping
    public ResponseEntity<Void> updateTeamById(@RequestBody UserDTO team) {
        userService.update(team);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
