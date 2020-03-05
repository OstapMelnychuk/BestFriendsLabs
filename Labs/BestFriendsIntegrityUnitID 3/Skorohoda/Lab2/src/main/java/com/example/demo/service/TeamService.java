package com.example.demo.service;

import com.example.demo.constants.ErrorMessage;
import com.example.demo.dto.TeamRequestDTO;
import com.example.demo.entity.Team;
import com.example.demo.entity.User;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.TeamAlreadyRegisteredException;
import com.example.demo.mapper.teamMapper.TeamRequestMapper;
import com.example.demo.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamRequestMapper teamRequestMapper;


    public TeamService(TeamRepository teamRepository, TeamRequestMapper teamRequestMapper) {
        this.teamRepository = teamRepository;
        this.teamRequestMapper = teamRequestMapper;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findOneById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NO_SUCH_TEAM_FOUND_BY_ID + id));
    }

    public Team findOneByName(String name) {
        return teamRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NO_SUCH_TEAM_FOUND_BY_NAME + name));
    }

    public void delete(Long id) throws NotFoundException {
        if (!teamRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NOT_DELETED_BY_TEAM_ID + id);
        }
        teamRepository.deleteById(id);
    }

    public void update(TeamRequestDTO team) {
        if (!teamRepository.findById(team.getId()).isPresent()) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_TEAM_FOUND_BY_ID);
        }
        Team team1 = teamRequestMapper.toEntity(team);
        Team team2 = teamRepository.findById(team.getId()).get();
        team1.setUsers(team2.getUsers());
        teamRepository.save(team1);
    }

    public void create(TeamRequestDTO team) {
        if (teamRepository.findById(team.getId()).isPresent()) {
            throw new TeamAlreadyRegisteredException(ErrorMessage.TEAM_ALREADY_REGISTERED + team.getId());
        }
        Team team1 = teamRequestMapper.toEntity(team);
        team1.setUsers(new ArrayList<User>());
        teamRepository.save(team1);
    }
}

