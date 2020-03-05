package com.example.demo.service;

import com.example.demo.constants.ErrorMessage;
import com.example.demo.dto.TournamentRequestDTO;
import com.example.demo.entity.Team;
import com.example.demo.entity.Tournament;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.TournamentAlreadyRegisteredException;
import com.example.demo.mapper.tournamentMapper.TournamentMapper;
import com.example.demo.mapper.tournamentMapper.TournamentRequestMapper;
import com.example.demo.repository.TournamentRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TournamentRequestMapper tournamentRequestMapper;

    public TournamentService(TournamentRepository tournamentRepository, TournamentRequestMapper tournamentRequestMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentRequestMapper = tournamentRequestMapper;
    }

    public List<Tournament> findAll() {
        return tournamentRepository.findAll();
    }

    public Tournament findOneById(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorMessage.NO_SUCH_TOURNAMENT_FOUND_BY_ID + id));
    }

    public void delete(Long id) {
        if (!tournamentRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_TOURNAMENT_FOUND_BY_ID + id);
        }
        tournamentRepository.deleteById(id);
    }

    public void create(TournamentRequestDTO tournament) {
        if (tournamentRepository.findById(tournament.getId()).isPresent()) {
            throw new TournamentAlreadyRegisteredException(ErrorMessage.TOURNAMENT_ALREADY_REGISTERED
                    + tournament.getId());
        }
        Tournament tournament1 = tournamentRequestMapper.toEntity(tournament);
        tournament1.setUsers(new ArrayList<User>());
        tournamentRepository.save(tournament1);
    }

    public void update(TournamentRequestDTO tournament) {
        if (!tournamentRepository.findById(tournament.getId()).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_TOURNAMENT_FOUND_BY_ID);
        }
        Tournament tournament1 = tournamentRequestMapper.toEntity(tournament);
        Tournament tournament2 = tournamentRepository.findById(tournament.getId()).get();
        tournament1.setUsers(tournament2.getUsers());
        tournamentRepository.save(tournament1);
    }
}
