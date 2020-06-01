package com.savchuk.secondlab.service;

import com.savchuk.secondlab.constans.ErrorMessage;
import com.savchuk.secondlab.dto.OrderRequestDto;
import com.savchuk.secondlab.entity.Customer;
import com.savchuk.secondlab.entity.EntityStatus;
import com.savchuk.secondlab.entity.Order;
import com.savchuk.secondlab.repository.OrderRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final ShopService shopService;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper,
                        CustomerService customerService, ShopService shopService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.shopService = shopService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll().stream().filter
                (e -> e.isStatus() == EntityStatus.ACTIVE.getStatus()).collect(Collectors.toList());
    }

    public Order findOneById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(ErrorMessage.NO_SUCH_ORDER_FOUND_BY_ID + id));
    }

    public Order findOneByName(String name) throws NotFoundException {
        return orderRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(ErrorMessage.NO_SUCH_ORDER_FOUND_BY_NAME + name));
    }

    public void delete(Long id) throws NotFoundException {
        if (!orderRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NOT_DELETED_BY_ORDER_ID + id);
        }
        orderRepository.changeStatus(id, EntityStatus.NONACTIVE.getStatus());
    }

    public void update(Long id, OrderRequestDto orderRequestDto) throws NotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_CUSTOMER_FOUND_BY_ID + id);
        }
        Order oldOlder = orderRepository.findById(id).get();
        oldOlder.setDescription(orderRequestDto.getDescription());
        oldOlder.setId(id);
        oldOlder.setName(orderRequestDto.getName());
        oldOlder.setData(orderRequestDto.getData());
        oldOlder.setCustomer(customerService.findOneById(orderRequestDto.getCustomer_id()));
        oldOlder.setShop(shopService.findOneById(orderRequestDto.getShop_id()));
        orderRepository.save(oldOlder);
    }

    public void create(OrderRequestDto orderRequestDto) throws NotFoundException {
        Order newOrder = modelMapper.map(orderRequestDto, Order.class);
        newOrder.setCustomer(customerService.findOneById(orderRequestDto.getCustomer_id()));
        newOrder.setShop(shopService.findOneById(orderRequestDto.getShop_id()));
        orderRepository.save(newOrder);
    }
}
