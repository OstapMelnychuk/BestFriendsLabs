package com.example.demo.mapper.teamMapper;

import com.example.demo.dto.TeamDTO;
import com.example.demo.entity.Team;
import com.example.demo.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@Component
public class TeamMapper implements Mapper<Team, TeamDTO> {
    private ModelMapper modelMapper;

    public Team toEntity(TeamDTO dto) {
        return modelMapper.map(dto, Team.class);
    }

    public TeamDTO toDto(Team entity) {
        return modelMapper.map(entity, TeamDTO.class);
    }
}
