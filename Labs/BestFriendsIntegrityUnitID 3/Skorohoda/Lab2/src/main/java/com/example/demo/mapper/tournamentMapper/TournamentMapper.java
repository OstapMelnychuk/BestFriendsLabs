package com.example.demo.mapper.tournamentMapper;

import com.example.demo.dto.TeamDTO;
import com.example.demo.dto.TournamentDTO;
import com.example.demo.entity.Team;
import com.example.demo.entity.Tournament;
import com.example.demo.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TournamentMapper implements Mapper<Tournament, TournamentDTO> {
    private ModelMapper modelMapper;

    public Tournament toEntity(TournamentDTO dto) {
        return modelMapper.map(dto, Tournament.class);
    }

    public TournamentDTO toDto(Tournament entity) {
        return modelMapper.map(entity, TournamentDTO.class);
    }
}