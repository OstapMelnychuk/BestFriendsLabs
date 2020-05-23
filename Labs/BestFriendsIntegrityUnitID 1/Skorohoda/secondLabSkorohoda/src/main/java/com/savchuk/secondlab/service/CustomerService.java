package com.savchuk.secondlab.service;

import com.savchuk.secondlab.constans.ErrorMessage;
import com.savchuk.secondlab.dto.CustomerRequestDto;
import com.savchuk.secondlab.entity.Customer;
import com.savchuk.secondlab.entity.EntityStatus;
import com.savchuk.secondlab.repository.CustomerRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll().stream().filter
                (e -> e.isStatus() == EntityStatus.ACTIVE.getStatus()).collect(Collectors.toList());
    }

    public Customer findOneById(Long id) throws NotFoundException {
        return customerRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorMessage.NO_SUCH_CUSTOMER_FOUND_BY_ID + id));
    }

    public Customer findOneByEmail(String email) throws NotFoundException {
        return customerRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(ErrorMessage.NO_SUCH_CUSTOMER_FOUND_BY_EMAIl + email));
    }

    public void delete(Long id) throws NotFoundException {
        if (!customerRepository.findById(id).isPresent()) {
            throw new NotFoundException(ErrorMessage.NOT_DELETED_BY_CUSTOMER_ID + id);
        }
        customerRepository.changeStatus(id, EntityStatus.NONACTIVE.getStatus());
    }

    public void update(Long id, CustomerRequestDto customerRequestDto) throws NotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException(ErrorMessage.NO_SUCH_CUSTOMER_FOUND_BY_ID + id);
        }
        Customer oldCustomer = customerRepository.findById(id).get();
        oldCustomer.setEmail(customerRequestDto.getEmail());
        oldCustomer.setId(id);
        oldCustomer.setName(customerRequestDto.getName());
        oldCustomer.setPassword(customerRequestDto.getPassword());
        customerRepository.save(oldCustomer);
    }

    public void create(CustomerRequestDto customerRequestDTO) {
        if (customerRepository.existsByEmail(customerRequestDTO.getEmail())) {
            throw new RuntimeException(ErrorMessage.CUSTOMER_ALREADY_REGISTERED + customerRequestDTO.getEmail());
        }
        Customer newCustomer = modelMapper.map(customerRequestDTO, Customer.class);
        customerRepository.save(newCustomer);
    }
}
