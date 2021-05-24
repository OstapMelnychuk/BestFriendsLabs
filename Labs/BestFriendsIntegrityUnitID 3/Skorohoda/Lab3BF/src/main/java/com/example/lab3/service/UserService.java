package com.example.lab3.service;

import com.example.lab3.dto.UserDto;
import com.example.lab3.entity.User;
import com.example.lab3.exception.AlreadyRegisteredException;
import com.example.lab3.exception.NotFoundException;
import com.example.lab3.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Long signUp(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new AlreadyRegisteredException("There is already registered user with email: " + userDto.getEmail());
        }
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return user.getId();
    }

    public Long updateUser(UserDto userDto) {
        if (!userRepository.existsByEmail(userDto.getEmail())){
            throw new NotFoundException("There is no registered user with email: " + userDto.getEmail());
        }

        User user = userRepository.findByEmail(userDto.getEmail()).get();
        user.setPermissions(userDto.getPermissions());
        user.setRoles(userDto.getRoles());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);
        return user.getId();
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)){
            throw new NotFoundException("There is no such user with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public User findOneById(Long id){
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("There is no such user with id: " + id));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
