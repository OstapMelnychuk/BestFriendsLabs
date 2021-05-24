package com.example.lab3.controller;

import com.example.lab3.constants.HttpStatuses;
import com.example.lab3.dto.PlanDto;
import com.example.lab3.dto.PlanRequestDto;
import com.example.lab3.dto.RequestDto;
import com.example.lab3.dto.WorkerDto;
import com.example.lab3.entity.Plan;
import com.example.lab3.entity.Worker;
import com.example.lab3.service.PlanService;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan")
public class PlanController {
    private final PlanService planService;
    private final ModelMapper modelMapper;

    public PlanController(PlanService planService, ModelMapper modelMapper) {
        this.planService = planService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Plans")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = PlanDto.class)
    })
    @GetMapping
    public ResponseEntity<List<PlanDto>> findAll() {
        List<PlanDto> planDtos = new ArrayList<>();
        for (Plan plan : planService.findAll()){
            PlanDto planDto = modelMapper.map(plan, PlanDto.class);
            planDto.setWorkers(new ArrayList<>());
            for (Worker worker : plan.getWorkers()){
                planDto.getWorkers().add(modelMapper.map(worker, WorkerDto.class));
            }
            planDtos.add(planDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(planDtos);
    }

    @ApiOperation(value = "Find one Plan by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = PlanDto.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/{plan_id}")
    public ResponseEntity<PlanDto> findOneById(@PathVariable("plan_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(planService.findById(id), PlanDto.class));
    }

    @ApiOperation(value = "Create new Plan")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST)
    })
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PlanRequestDto planRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(planService.createPlan(planRequestDto));
    }

    @ApiOperation(value = "Delete one Plan by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deletePlanById(Long id) {
        planService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Update one Plan by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping
    public ResponseEntity<Long> update(Long id, @RequestBody PlanRequestDto planRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(planService.updatePlan(id, planRequestDto));
    }

    @ApiOperation(value = "Update status of the Plan by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping("/status/{plan_id}")
    public ResponseEntity<Long> updateStatus(@PathVariable("plan_id") Long id, boolean status) {
        return ResponseEntity.status(HttpStatus.OK).body(planService.changeStatus(id, status));
    }
}
