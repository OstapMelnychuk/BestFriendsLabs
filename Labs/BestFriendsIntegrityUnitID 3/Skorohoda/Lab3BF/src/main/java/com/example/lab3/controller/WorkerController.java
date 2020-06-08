package com.example.lab3.controller;

import com.example.lab3.constants.HttpStatuses;
import com.example.lab3.dto.PlanDto;
import com.example.lab3.dto.WorkerDto;
import com.example.lab3.dto.WorkerRequestDto;
import com.example.lab3.entity.Plan;
import com.example.lab3.entity.Worker;
import com.example.lab3.service.WorkerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/worker")
public class WorkerController {
    private final WorkerService workerService;
    private final ModelMapper modelMapper;

    public WorkerController(WorkerService workerService, ModelMapper modelMapper) {
        this.workerService = workerService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Workers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = WorkerDto.class)
    })
    @GetMapping
    public ResponseEntity<List<WorkerDto>> findAll(){
        List<WorkerDto> workerDtos = new ArrayList<>();
        for (Worker worker : workerService.findAll()){
            WorkerDto workerDto = modelMapper.map(worker, WorkerDto.class);
            workerDto.setEmail(worker.getUser().getEmail());
            workerDtos.add(workerDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(workerDtos);
    }

    @ApiOperation(value = "Find all free workers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = WorkerDto.class)
    })
    @GetMapping("/free")
    public ResponseEntity<List<WorkerDto>> findAllFreeWorkers(){
        List<WorkerDto> workerDtos = new ArrayList<>();
        for (Worker worker : workerService.findAllFreeWorkers()){
            WorkerDto workerDto = modelMapper.map(worker, WorkerDto.class);
            workerDto.setEmail(worker.getUser().getEmail());
            workerDtos.add(workerDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(workerDtos);
    }

    @ApiOperation(value = "Create new worker")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST)
    })
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody WorkerRequestDto workerRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(workerService.create(workerRequestDto));
    }

    @ApiOperation(value = "Find the worker by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/{worker_id}")
    public ResponseEntity<WorkerDto> findOneById(@PathVariable("worker_id") Long id){
        Worker worker = workerService.findOneById(id);
        WorkerDto workerDto = modelMapper.map(worker, WorkerDto.class);
        workerDto.setEmail(worker.getUser().getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(workerDto);
    }

    @ApiOperation(value = "Update the worker")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody WorkerRequestDto workerRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(workerService.update(workerRequestDto));
    }

    @ApiOperation(value = "Delete the worker by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(Long id){
        workerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Find all Worker`s plans")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = PlanDto.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/plans/{worker_id}")
    public ResponseEntity<List<PlanDto>> findAllWorkerPlans(@PathVariable("worker_id") Long id){
        List<PlanDto> planDtos = new ArrayList<>();
        for (Plan plan : workerService.findAllWorkerPlans(id)){
            planDtos.add(modelMapper.map(plan, PlanDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(planDtos);
    }

    @ApiOperation(value = "Change worker`s status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping("/status/{worker_id}")
    public ResponseEntity<Long> changeStatus(@PathVariable("worker_id") Long id, boolean status){
        return ResponseEntity.status(HttpStatus.OK).body(workerService.changeStatus(id, status));
    }
}
