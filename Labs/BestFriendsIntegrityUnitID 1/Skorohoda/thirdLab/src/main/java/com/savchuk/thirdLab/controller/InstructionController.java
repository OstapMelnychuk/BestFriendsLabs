package com.savchuk.thirdLab.controller;

import com.savchuk.thirdLab.dto.InstructionDto;
import com.savchuk.thirdLab.dto.InstructionRequestDto;
import com.savchuk.thirdLab.dto.PlantRequestDto;
import com.savchuk.thirdLab.entity.Instruction;
import com.savchuk.thirdLab.entity.Plant;
import com.savchuk.thirdLab.service.InstructionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.savchuk.thirdLab.constans.HttpStatus.*;

@RestController
@RequestMapping("/instruction")
public class InstructionController {
    private final InstructionService instructionService;
    private final ModelMapper modelMapper;

    public InstructionController(InstructionService instructionService, ModelMapper modelMapper) {
        this.instructionService = instructionService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Instructions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping
    public ResponseEntity<List<InstructionDto>> findAll() {
        List<Instruction> instructions = instructionService.findAll();
        List<InstructionDto> instructionDtoList = new ArrayList<>();
        for (Instruction instruction : instructions) {
            instructionDtoList.add(modelMapper.map(instruction, InstructionDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(instructionDtoList);
    }

    @ApiOperation(value = "Find instruction by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/{instruction_id}")
    public ResponseEntity<InstructionRequestDto> findOneById(@PathVariable("instruction_id") Long id) {
        Instruction instruction = instructionService.findOneById(id);
        InstructionRequestDto instructionRequestDto = modelMapper.map(instructionService.findOneById(id),
                InstructionRequestDto.class);
        instructionRequestDto.setPlantsID(new ArrayList<>());
        for (Plant plant : instruction.getPlants()) {
            instructionRequestDto.getPlantsID().add(plant.getId());
        }
        return ResponseEntity.status(HttpStatus.OK).body
                (instructionRequestDto);
    }

    @ApiOperation(value = "Delete instruction by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
            @ApiResponse(code = 404, message = NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteOwnerById(Long id) throws NotFoundException {
        instructionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Instruction")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody InstructionRequestDto instructionRequestDto) throws NotFoundException {
        instructionService.create(instructionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update instruction")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK)
    })
    @PutMapping("/{instruction_id}")
    public ResponseEntity<Void> update(@PathVariable("instruction_id") Long id,
                                       @RequestBody InstructionRequestDto instructionRequestDto) throws NotFoundException {
        instructionService.update(id, instructionRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Check all results(for owners)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/owner/{owner_id}")
    public ResponseEntity<List<Instruction>> checkAllResults(@PathVariable("owner_id") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body
                (instructionService.checkAllResults(id));
    }

    @ApiOperation(value = "Get all instructions(for forester)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/forester/{forester_id}")
    public ResponseEntity<List<Instruction>> getAllInstructions(@PathVariable("forester_id") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body
                (instructionService.getAllInstructions(id));
    }
}
