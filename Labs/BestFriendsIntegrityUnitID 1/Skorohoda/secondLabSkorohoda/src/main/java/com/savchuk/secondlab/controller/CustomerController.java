package com.savchuk.secondlab.controller;

import com.savchuk.secondlab.dto.CustomerRequestDto;
import com.savchuk.secondlab.entity.Customer;
import com.savchuk.secondlab.service.CustomerService;
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

import static com.savchuk.secondlab.constans.HttpStatus.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = CustomerRequestDto.class)
    })
    @GetMapping
    public ResponseEntity<List<CustomerRequestDto>> findAll() {
        List<Customer> customers = customerService.findAll();
        List<CustomerRequestDto> customersList = new ArrayList<>();
        for (Customer customer: customers)
        {
            customersList.add(modelMapper.map(customer, CustomerRequestDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(customersList);
    }

    @ApiOperation(value = "Find customer by email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = CustomerRequestDto.class)
    })
    @GetMapping("/email/{customer_email}")
    public ResponseEntity<CustomerRequestDto> findOneByEmail(@PathVariable("customer_email") String email) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(customerService.findOneByEmail(email), CustomerRequestDto.class));
    }

    @ApiOperation(value = "Find customer by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = CustomerRequestDto.class)
    })
    @GetMapping("/{customer_id}")
    public ResponseEntity<CustomerRequestDto> findOneById(@PathVariable("customer_id") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(customerService.findOneById(id), CustomerRequestDto.class));
    }

    @ApiOperation(value = "Delete customer by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
            @ApiResponse(code = 404, message = NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTeamById(Long id) throws NotFoundException {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Customer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CustomerRequestDto customerRequestDto) {
        customerService.create(customerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update customer")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT)
    })
    @PutMapping("/{customer_id}")
    public ResponseEntity<Void> updateTeamById(@PathVariable("customer_id") Long id,
                                               @RequestBody CustomerRequestDto customerRequestDto) throws NotFoundException {
        customerService.update(id, customerRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
