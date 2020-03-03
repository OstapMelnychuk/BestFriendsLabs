package com.example.demo.mapper.teamMapper;

import com.example.demo.dto.TeamRequestDTO;
import com.example.demo.entity.Team;
import com.example.demo.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TeamRequestMapper implements Mapper<Team, TeamRequestDTO> {
    private ModelMapper modelMapper;

    public Team toEntity(TeamRequestDTO dto) {
        return modelMapper.map(dto, Team.class);
    }

    public TeamRequestDTO toDto(Team entity) {
        return modelMapper.map(entity, TeamRequestDTO.class);
    }
}
