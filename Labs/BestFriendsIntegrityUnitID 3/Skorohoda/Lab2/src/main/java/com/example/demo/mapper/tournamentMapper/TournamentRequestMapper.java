package com.example.demo.mapper.tournamentMapper;

import com.example.demo.dto.TournamentDTO;
import com.example.demo.dto.TournamentRequestDTO;
import com.example.demo.entity.Tournament;
import com.example.demo.mapper.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TournamentRequestMapper implements Mapper<Tournament, TournamentRequestDTO> {
    private ModelMapper modelMapper;

    public Tournament toEntity(TournamentRequestDTO dto) {
        return modelMapper.map(dto, Tournament.class);
    }

    public TournamentRequestDTO toDto(Tournament entity) {
        return modelMapper.map(entity, TournamentRequestDTO.class);
    }
}