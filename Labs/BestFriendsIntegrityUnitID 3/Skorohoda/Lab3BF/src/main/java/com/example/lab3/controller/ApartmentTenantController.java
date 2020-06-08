package com.example.lab3.controller;

import com.example.lab3.constants.HttpStatuses;
import com.example.lab3.dto.ApartmentTenantDto;
import com.example.lab3.dto.ApartmentTenantRequestDto;
import com.example.lab3.dto.RequestDto;
import com.example.lab3.entity.ApartmentTenant;
import com.example.lab3.entity.Request;
import com.example.lab3.service.ApartmentTenantService;
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
@RequestMapping("/tenant")
public class ApartmentTenantController {
    private final ApartmentTenantService apartmentTenantService;
    private final ModelMapper modelMapper;

    public ApartmentTenantController(ApartmentTenantService apartmentTenantService, ModelMapper modelMapper) {
        this.apartmentTenantService = apartmentTenantService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Tenants")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = ApartmentTenantDto.class)
    })
    @GetMapping
    public ResponseEntity<List<ApartmentTenantDto>> findAll() {
        List<ApartmentTenantDto> apartmentTenantDtos = new ArrayList<>();
        for (ApartmentTenant apartmentTenant : apartmentTenantService.findAll()) {
            ApartmentTenantDto apartmentTenantDto = modelMapper.map(apartmentTenant, ApartmentTenantDto.class);
            apartmentTenantDto.setEmail(apartmentTenant.getUser().getEmail());
            apartmentTenantDtos.add(apartmentTenantDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(apartmentTenantDtos);
    }

    @ApiOperation(value = "Find one tenant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = ApartmentTenantDto.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/{apartment_tenant_id}")
    public ResponseEntity<ApartmentTenantDto> findOneById(@PathVariable("apartment_tenant_id") Long id) {
        ApartmentTenant apartmentTenant = apartmentTenantService.findById(id);
        ApartmentTenantDto apartmentTenantDto = modelMapper.map(apartmentTenant,
                ApartmentTenantDto.class);
        apartmentTenantDto.setEmail(apartmentTenant.getUser().getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(apartmentTenantDto);
    }

    @ApiOperation(value = "Delete the tenant by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = HttpStatuses.NO_CONTENT),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteOneById(Long id) {
        apartmentTenantService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Update the tenant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PutMapping
    public ResponseEntity<Long> updateTenantById(@RequestBody ApartmentTenantRequestDto apartmentTenantRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                apartmentTenantService.updateApartmentTenant(apartmentTenantRequestDto));
    }

    @ApiOperation(value = "Create new tenant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST)
    })
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ApartmentTenantRequestDto apartmentTenantRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentTenantService.
                create(apartmentTenantRequestDto));
    }

    @ApiOperation(value = "Find all Tenant`s Requests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = ApartmentTenantDto.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/requests/{apartment_tenant_id}")
    public ResponseEntity<List<RequestDto>> findOneRequestsById(@PathVariable("apartment_tenant_id") Long id) {
        List<RequestDto> requestDtos = new ArrayList<>();
        for (Request request : apartmentTenantService.getAllApartmentTenantRequests(id)){
            requestDtos.add(modelMapper.map(request, RequestDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(requestDtos);
    }

    @ApiOperation(value = "Create new Tenant`s Requests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK, response = Long.class),
            @ApiResponse(code = 404, message = HttpStatuses.NOT_FOUND)
    })
    @PostMapping("/requests/{apartment_tenant_id}")
    public ResponseEntity<Long> createTenantRequest(@PathVariable("apartment_tenant_id") Long id,
                                                             @RequestBody RequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(apartmentTenantService.createRequest(id, requestDto));
    }


}
