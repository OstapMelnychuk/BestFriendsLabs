package com.example.demo.controller;

import com.example.demo.constants.HttpStatuses;
import com.example.demo.dto.TournamentDTO;
import com.example.demo.dto.TournamentRequestDTO;
import com.example.demo.mapper.tournamentMapper.TournamentMapper;
import com.example.demo.service.TournamentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tournament")
public class TournamentController {
    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;

    public TournamentController(TournamentService tournamentService, TournamentMapper tournamentMapper) {
        this.tournamentService = tournamentService;
        this.tournamentMapper = tournamentMapper;
    }

    @ApiOperation(value = "Find all tournaments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = TournamentDTO.class)
    })
    @GetMapping
    public ResponseEntity<List<TournamentDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tournamentService.findAll().stream().map(tournamentMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Find tournament by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = TournamentDTO.class)
    })
    @GetMapping("/{tournament_id}")
    public ResponseEntity<TournamentDTO> findOneById(@PathVariable("tournament_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tournamentMapper.toDto(tournamentService.findOneById(id)));
    }

    @ApiOperation(value = "Delete tournament by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTeamById(Long id) {
        tournamentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Update tournament")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT)
    })
    @PutMapping
    public ResponseEntity<Void> updateTournament(@RequestBody TournamentRequestDTO tournament) {
        tournamentService.update(tournament);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Tournament")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 404, message = HttpStatuses.BAD_REQUEST)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TournamentRequestDTO team) {
        tournamentService.create(team);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
