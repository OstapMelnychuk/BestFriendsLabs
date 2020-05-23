package com.savchuk.secondlab.controller;

import com.savchuk.secondlab.dto.CustomerRequestDto;
import com.savchuk.secondlab.dto.OrderRequestDto;
import com.savchuk.secondlab.entity.Order;
import com.savchuk.secondlab.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Orders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = OrderRequestDto.class)
    })
    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> findAll() {
        List<Order> orders = orderService.findAll();
        List<OrderRequestDto> ordersList = new ArrayList<>();
        for (Order order: orders)
        {
            OrderRequestDto orderRequestDto = modelMapper.map(order, OrderRequestDto.class);
            orderRequestDto.setCustomer_id(order.getCustomer().getId());
            orderRequestDto.setShop_id(order.getShop().getId());
            ordersList.add(orderRequestDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ordersList);
    }

    @ApiOperation(value = "Find Order by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = OrderRequestDto.class)
    })
    @GetMapping("/name/{order_name}")
    public ResponseEntity<OrderRequestDto> findOneByName(@PathVariable("order_name") String name) throws NotFoundException {
        Order order = orderService.findOneByName(name);
        OrderRequestDto orderRequestDto = modelMapper.map(orderService.findOneByName(name), OrderRequestDto.class);
        orderRequestDto.setCustomer_id(order.getCustomer().getId());
        orderRequestDto.setShop_id(order.getShop().getId());
        return ResponseEntity.status(HttpStatus.OK).body
                (orderRequestDto);
    }

    @ApiOperation(value = "Find Order by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = OrderRequestDto.class)
    })
    @GetMapping("/{order_id}")
    public ResponseEntity<OrderRequestDto> findOneById(@PathVariable("order_id") Long id) {
        Order order = orderService.findOneById(id);
        OrderRequestDto orderRequestDto = modelMapper.map(orderService.findOneById(id), OrderRequestDto.class);
        orderRequestDto.setCustomer_id(order.getCustomer().getId());
        orderRequestDto.setShop_id(order.getShop().getId());
        return ResponseEntity.status(HttpStatus.OK).body
                (orderRequestDto);
    }

    @ApiOperation(value = "Delete Order by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
            @ApiResponse(code = 404, message = NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTeamById(Long id) throws NotFoundException {
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderRequestDto orderRequestDto) throws NotFoundException {
        orderService.create(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update Order")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT)
    })
    @PutMapping("/{order_id}")
    public ResponseEntity<Void> updateTeamById(@PathVariable("order_id") Long id,
                                               @RequestBody OrderRequestDto orderRequestDto) throws NotFoundException {
        orderService.update(id, orderRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
