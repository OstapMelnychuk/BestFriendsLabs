package com.savchuk.thirdLab.controller;

import com.savchuk.thirdLab.dto.PlantDto;
import com.savchuk.thirdLab.dto.PlantRequestDto;
import com.savchuk.thirdLab.entity.Plant;
import com.savchuk.thirdLab.service.PlantService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.savchuk.thirdLab.constans.HttpStatus.*;

@RestController
@RequestMapping("/plant")
public class PlantController {
    private final PlantService plantService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlantController(PlantService plantService, ModelMapper modelMapper) {
        this.plantService = plantService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all plants")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping
    public ResponseEntity<List<PlantRequestDto>> findAll() {
        List<Plant> plants = plantService.findAll();
        List<PlantRequestDto> plantsList = new ArrayList<>();
        for (Plant plant: plants)
        {
            plantsList.add(modelMapper.map(plant, PlantRequestDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(plantsList);
    }

    @ApiOperation(value = "Find plant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/{plant_id}")
    public ResponseEntity<PlantRequestDto> findOneById(@PathVariable("plant_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(plantService.findOneById(id), PlantRequestDto.class));
    }

    @ApiOperation(value = "Delete plant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
            @ApiResponse(code = 404, message = NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deletePlantById(Long id) throws NotFoundException {
        plantService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Plant")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PlantDto plantDto) throws NotFoundException {
        plantService.create(plantDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update plant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK)
    })
    @PutMapping("/{plant_id}")
    public ResponseEntity<Void> updatePlantById(@PathVariable("plant_id") Long id,
                                               @RequestBody PlantRequestDto plantRequestDto) throws NotFoundException {
        plantService.update(id, plantRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
