package com.example.demo.service;

import com.example.demo.constants.ErrorMessage;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UserAlreadyRegisteredException;
import com.example.demo.mapper.userMapper.UserMapper;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void create(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new UserAlreadyRegisteredException(ErrorMessage.USER_ALREADY_REGISTERED + user.getId());
        }
        System.out.println(user.getPassword());

    }

    public void update(UserDTO userDTO) {
        if (!userRepository.findById(userDTO.getId()).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_USER_FOUND_BY_ID + userDTO.getId());
        }
        userRepository.save(userMapper.toEntity(userDTO));
    }

    public void delete(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException(ErrorMessage.NOT_DELETED_BY_USER_ID + id);
        }
        userRepository.deleteById(id);
    }

    public User findOneById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorMessage.NO_SUCH_USER_FOUND_BY_ID + id));
    }

}
