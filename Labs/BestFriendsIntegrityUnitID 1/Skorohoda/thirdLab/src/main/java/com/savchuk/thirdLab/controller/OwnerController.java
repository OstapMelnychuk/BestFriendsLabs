package com.savchuk.thirdLab.controller;

import com.savchuk.thirdLab.dto.*;
import com.savchuk.thirdLab.entity.Instruction;
import com.savchuk.thirdLab.entity.Owner;
import com.savchuk.thirdLab.entity.Plant;
import com.savchuk.thirdLab.service.InstructionService;
import com.savchuk.thirdLab.service.OwnerService;
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
@RequestMapping("/owner")
public class OwnerController {
    private final OwnerService ownerService;
    private final InstructionService instructionService;
    private final ModelMapper modelMapper;

    public OwnerController(OwnerService ownerService, InstructionService instructionService, ModelMapper modelMapper) {
        this.ownerService = ownerService;
        this.instructionService = instructionService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Owners")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping
    public ResponseEntity<List<OwnerDto>> findAll() {
        List<Owner> owners = ownerService.findAll();
        List<OwnerDto> ownerList = new ArrayList<>();
        for (Owner owner : owners) {
            ownerList.add(modelMapper.map(owner, OwnerDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ownerList);
    }

    @ApiOperation(value = "Find owner by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/{owner_id}")
    public ResponseEntity<OwnerRequestDto> findOneById(@PathVariable("owner_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(ownerService.findOneById(id), OwnerRequestDto.class));
    }

    @ApiOperation(value = "Delete owner by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
            @ApiResponse(code = 404, message = NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteOwnerById(Long id) throws NotFoundException {
        ownerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Owner")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OwnerRequestDto ownerRequestDto) throws NotFoundException {
        ownerService.create(ownerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Create new Instruction")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping("/instruction")
    public ResponseEntity<Void> createNewInstruction(@RequestBody InstructionRequestDto instructionRequestDto) throws NotFoundException {
        ownerService.createNewInstruction(instructionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Submite Results")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
    })
    @GetMapping("/submite")
    public ResponseEntity<Void> submitResults(Long instructionID) throws NotFoundException {
        ownerService.submitResults(instructionID);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "UnApprove Results")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
    })
    @GetMapping("/unapprove")
    public ResponseEntity<Void> unApproveResults(@RequestBody Long instructionID) throws NotFoundException {
        ownerService.unApproveResults(instructionID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Check Results")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK),
            @ApiResponse(code = 204, message = NO_CONTENT)
    })
    @GetMapping("/result")
    public ResponseEntity<Instruction> checkResults(@RequestBody Long instructionID) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(ownerService.checkResults(instructionID), Instruction.class));
    }

    @ApiOperation(value = "Update owner")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK)
    })
    @PutMapping("/{owner_id}")
    public ResponseEntity<Void> update(@PathVariable("owner_id") Long id,
                                       @RequestBody OwnerRequestDto ownerRequestDto) throws NotFoundException {
        ownerService.update(id, ownerRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Check all results(for owners)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = PlantRequestDto.class)
    })
    @GetMapping("/instruction/{owner_id}")
    public ResponseEntity<List<InstructionDto>> checkAllResults(@PathVariable("owner_id") Long id) throws NotFoundException {
        List<InstructionDto> instructionRequestDtos = new ArrayList<>();
        for (Instruction instruction : ownerService.checkAllResults(id)) {
            InstructionDto instructionDto = new InstructionDto();
            instructionDto.setPlantsID(new ArrayList<>());
            for (Plant plant : instruction.getPlants()) {
                instructionDto.getPlantsID().add(plant.getId());
            }
            instructionDto.setData(instruction.getData());
            instructionDto.setDescription(instruction.getDescription());
            instructionDto.setName(instruction.getName());
            instructionDto.setForesterID(instruction.getForester().getId());
            instructionDto.setAnswerOfForester(instruction.getAnswerOfForester());
            instructionRequestDtos.add(instructionDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body
                (instructionRequestDtos);
    }
}
