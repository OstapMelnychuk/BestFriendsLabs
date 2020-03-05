package com.example.demo.mapper.userMapper;

import com.example.demo.dto.TeamDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Team;
import com.example.demo.entity.User;
import com.example.demo.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserMapper implements Mapper<User, UserDTO> {
    private ModelMapper modelMapper;

    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserDTO toDto(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }
}

