package com.savchuk.thirdLab.controller;

import com.savchuk.thirdLab.dto.*;
import com.savchuk.thirdLab.entity.Forester;
import com.savchuk.thirdLab.entity.Instruction;
import com.savchuk.thirdLab.entity.Plant;
import com.savchuk.thirdLab.service.ForesterService;
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
import static com.savchuk.thirdLab.constans.HttpStatus.OK;

@RestController
@RequestMapping("/forester")
public class ForesterController {
    private final ForesterService foresterService;
    private final ModelMapper modelMapper;

    @Autowired
    public ForesterController(ForesterService foresterService, ModelMapper modelMapper) {
        this.foresterService = foresterService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Foresters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping
    public ResponseEntity<List<ForesterDto>> findAll() {
        List<Forester> foresters = foresterService.findAll();
        List<ForesterDto> foresterDtos = new ArrayList<>();
        for (Forester forester : foresters) {
            foresterDtos.add(modelMapper.map(forester, ForesterDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(foresterDtos);
    }

    @ApiOperation(value = "Find forester by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/{forester_id}")
    public ResponseEntity<ForesterRequestDto> findOneById(@PathVariable("forester_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(foresterService.findOneById(id), ForesterRequestDto.class));
    }

    @ApiOperation(value = "Delete forester by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
            @ApiResponse(code = 404, message = NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteById(Long id) throws NotFoundException {
        foresterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Forester")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ForesterRequestDto foresterRequestDto) throws NotFoundException {
        foresterService.create(foresterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update forester")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK)
    })
    @PutMapping("/{forester_id}")
    public ResponseEntity<Void> update(@PathVariable("forester_id") Long id,
                                       @RequestBody ForesterRequestDto foresterRequestDto) throws NotFoundException {
        foresterService.update(id, foresterRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Find all plants")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/plant")
    public ResponseEntity<List<PlantRequestDto>> findAllPlants() {
        List<Plant> plants = foresterService.findAllPlants();
        List<PlantRequestDto> plantsList = new ArrayList<>();
        for (Plant plant : plants) {
            plantsList.add(modelMapper.map(plant, PlantRequestDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(plantsList);
    }

    @ApiOperation(value = "Find plant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/plant/{plant_id}")
    public ResponseEntity<PlantRequestDto> findOnePlantById(@PathVariable("plant_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(foresterService.findOnePlantById(id), PlantRequestDto.class));
    }

    @ApiOperation(value = "Delete plant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
            @ApiResponse(code = 404, message = NOT_FOUND)
    })
    @DeleteMapping("/plant")
    public ResponseEntity<Void> deletePlantById(Long id) throws NotFoundException {
        foresterService.deletePlant(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Plant")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping("/plant")
    public ResponseEntity<Void> createPlant(@RequestBody PlantDto plantDto) throws NotFoundException {
        foresterService.createPlant(plantDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update plant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK)
    })
    @PutMapping("/plant/{plant_id}")
    public ResponseEntity<Void> updatePlantById(@PathVariable("plant_id") Long id,
                                                @RequestBody PlantRequestDto plantRequestDto) throws NotFoundException {
        foresterService.updatePlant(id, plantRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get all instructions(for forester)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/instruction/{forester_id}")
    public ResponseEntity<List<InstructionDto>> getAllInstructions(@PathVariable("forester_id") Long id) throws NotFoundException {
        List<InstructionDto> instructionRequestDtos = new ArrayList<>();
        for (Instruction instruction : foresterService.getAllInstructions(id)) {
            InstructionDto instructionDto = new InstructionDto();
            instructionDto.setPlantsID(new ArrayList<>());
            for (Plant plant : instruction.getPlants()) {
                instructionDto.getPlantsID().add(plant.getId());
            }
            instructionDto.setId(instruction.getId());
            instructionDto.setData(instruction.getData());
            instructionDto.setDescription(instruction.getDescription());
            instructionDto.setName(instruction.getName());
            instructionDto.setAnswerOfForester(instruction.getAnswerOfForester());
            instructionDto.setForesterID(instruction.getForester().getId());
            instructionDto.setStatus(instruction.getStatus());
            instructionRequestDtos.add(instructionDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body
                (instructionRequestDtos);
    }

    @ApiOperation(value = "Give answer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK)
    })
    @PutMapping("/instruction/{instruction_id}")
    public ResponseEntity<Void> updateInstruction(@PathVariable("instruction_id") Long id,
                                                  @RequestBody String answer) throws NotFoundException {
        foresterService.updateInstruction(id, answer);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

