package com.savchuk.secondlab.controller;

import com.savchuk.secondlab.dto.CustomerRequestDto;
import com.savchuk.secondlab.dto.ShopRequestDto;
import com.savchuk.secondlab.entity.Customer;
import com.savchuk.secondlab.entity.Shop;
import com.savchuk.secondlab.service.CustomerService;
import com.savchuk.secondlab.service.ShopService;
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
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    private final ModelMapper modelMapper;

    public ShopController(ShopService shopService, ModelMapper modelMapper) {
        this.shopService = shopService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Find all Shops")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = ShopRequestDto.class)
    })
    @GetMapping
    public ResponseEntity<List<ShopRequestDto>> findAll() {
        List<Shop> shops = shopService.findAll();
        List<ShopRequestDto> shopsList = new ArrayList<>();
        for (Shop shop: shops)
        {
            shopsList.add(modelMapper.map(shop, ShopRequestDto.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(shopsList);
    }

    @ApiOperation(value = "Find Shop by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = ShopRequestDto.class)
    })
    @GetMapping("/name/{shop_id}")
    public ResponseEntity<ShopRequestDto> findOneByEmail(@PathVariable("shop_id") String name) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(shopService.findOneByName(name), ShopRequestDto.class));
    }

    @ApiOperation(value = "Find Shop by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = OK, response = ShopRequestDto.class)
    })
    @GetMapping("/{shop_id}")
    public ResponseEntity<ShopRequestDto> findOneById(@PathVariable("shop_id") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body
                (modelMapper.map(shopService.findOneById(id), ShopRequestDto.class));
    }

    @ApiOperation(value = "Delete Shop by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT),
            @ApiResponse(code = 404, message = NOT_FOUND)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTeamById(Long id) throws NotFoundException {
        shopService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create Shop")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = CREATED)
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ShopRequestDto shopRequestDto) {
        shopService.create(shopRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "Update Shop")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = NO_CONTENT)
    })
    @PutMapping("/{shop_id}")
    public ResponseEntity<Void> updateTeamById(@PathVariable("shop_id") Long id,
                                               @RequestBody ShopRequestDto shopRequestDto) throws NotFoundException {
        shopService.update(id, shopRequestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
