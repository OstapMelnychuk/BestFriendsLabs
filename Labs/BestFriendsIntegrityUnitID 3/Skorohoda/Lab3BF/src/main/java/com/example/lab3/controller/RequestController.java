package com.example.lab3.controller;

import com.example.lab3.constants.HttpStatuses;
import com.example.lab3.dto.ApartmentTenantDto;
import com.example.lab3.dto.RequestDto;
import com.example.lab3.entity.ApartmentTenant;
import com.example.lab3.entity.Request;
import com.example.lab3.service.RequestService;
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
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;
    private final ModelMapper modelMapper;

    public RequestController(RequestService requestService, ModelMapper modelMapper) {
        this.requestService = requestService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Requests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = RequestDto.class)
    })
    @GetMapping
    public ResponseEntity<List<RequestDto>> findAll() {
        List<RequestDto> requestDtos = new ArrayList<>();
        for (Request request : requestService.findAll()) {
            requestDtos.add(modelMapper.map(request, RequestDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(requestDtos);
    }

    @ApiOperation(value = "Find one request by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = RequestDto.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/{request_id}")
    public ResponseEntity<RequestDto> findOneById(@PathVariable("request_id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(requestService.findOneById(id),
                RequestDto.class));
    }

    @ApiOperation(value = "Delete one request by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteById(Long id) {
        requestService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Update one request by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody RequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(requestService.update(requestDto));
    }
}
