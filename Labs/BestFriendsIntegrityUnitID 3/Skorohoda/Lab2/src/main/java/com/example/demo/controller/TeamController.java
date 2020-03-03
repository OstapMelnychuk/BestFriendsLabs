package com.example.demo.controller;

import com.example.demo.constants.HttpStatuses;
import com.example.demo.dto.TeamDTO;
import com.example.demo.dto.TeamRequestDTO;
import com.example.demo.mapper.teamMapper.TeamMapper;
import com.example.demo.service.TeamService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    public TeamController(TeamService teamService, TeamMapper teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @ApiOperation(value = "Find all teams")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = TeamDTO.class)
    })
    @GetMapping
    public ResponseEntity<List<TeamDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.findAll().stream().map(teamMapper::toDto)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Find team by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = TeamDTO.class)
    })
    @GetMapping("/{team_id}")
    public ResponseEntity<TeamDTO> findOneById(@PathVariable("team_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(teamMapper.toDto(teamService.findOneById(id)));
    }

    @ApiOperation(value = "Delete team by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTeamById(Long id) {
        teamService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Update team")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT)
    })
    @PutMapping
    public ResponseEntity<Void> updateTeam(@RequestBody TeamRequestDTO team) {
        teamService.update(team);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Team")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 404, message = HttpStatuses.BAD_REQUEST)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TeamRequestDTO team) {
        teamService.create(team);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
