package com.example.lab3.service;

import com.example.lab3.dto.ApartmentTenantRequestDto;
import com.example.lab3.dto.RequestDto;
import com.example.lab3.entity.ApartmentTenant;
import com.example.lab3.entity.Request;
import com.example.lab3.entity.User;
import com.example.lab3.exception.AlreadyRegisteredException;
import com.example.lab3.exception.NotFoundException;
import com.example.lab3.repository.ApartmentTenantRepository;
import com.example.lab3.repository.RequestRepository;
import com.example.lab3.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApartmentTenantService {
    private final ApartmentTenantRepository apartmentTenantRepository;
    private final RequestRepository requestRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ApartmentTenantService(ApartmentTenantRepository apartmentTenantRepository,
                                  RequestRepository requestRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.apartmentTenantRepository = apartmentTenantRepository;
        this.requestRepository = requestRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public List<ApartmentTenant> findAll() {
        return apartmentTenantRepository.findAll();
    }

    public ApartmentTenant findById(Long id) {
        if (apartmentTenantRepository.existsById(id)) {
            return apartmentTenantRepository.findById(id).get();
        }
        throw new NotFoundException("There is no such Apartment Tenant with id: " + id);
    }

    public void deleteById(Long id) {
        if (!apartmentTenantRepository.existsById(id)) {
            throw new NotFoundException("There is no such Apartment Tenant with id: " + id);
        }
        apartmentTenantRepository.deleteById(id);
    }

    public List<Request> getAllApartmentTenantRequests(Long id) {
        return apartmentTenantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no such Apartment Tenant with id: " + id))
                .getRequests();
    }

    public Long updateApartmentTenant(ApartmentTenantRequestDto apartmentTenantRequestDto) {
        if (!userRepository.existsByEmail(apartmentTenantRequestDto.getUserRequestDto().getEmail())) {
            throw new NotFoundException("There is no such Apartment Tenant with email: " +
                    apartmentTenantRequestDto.getUserRequestDto().getEmail());
        }
        ApartmentTenant apartmentTenant = apartmentTenantRepository.findByUser(userRepository
                .findByEmail(apartmentTenantRequestDto.getUserRequestDto().getEmail()).get())
                .orElseThrow(() -> new NotFoundException("There is no such Apartment Tenant"));
        apartmentTenant.setAddress(apartmentTenantRequestDto.getAddress());
        apartmentTenant.setName(apartmentTenantRequestDto.getName());
        apartmentTenant.setSurname(apartmentTenantRequestDto.getSurname());
        apartmentTenantRepository.save(apartmentTenant);
        return apartmentTenant.getId();
    }

    public Long create(ApartmentTenantRequestDto apartmentTenantRequestDto) {
        if (!userRepository.existsByEmail(apartmentTenantRequestDto.getUserRequestDto().getEmail())) {
            throw new AlreadyRegisteredException("There is no registered user with email: "
                    + apartmentTenantRequestDto.getUserRequestDto().getEmail());
        }
        ApartmentTenant apartmentTenant = modelMapper.map(apartmentTenantRequestDto, ApartmentTenant.class);
        apartmentTenant.setRequests(new ArrayList<>());
        apartmentTenant.setUser(userRepository.findByEmail(apartmentTenantRequestDto.getUserRequestDto().getEmail()).
                orElseThrow(() -> new NotFoundException("There is no such user with email: " +
                        apartmentTenantRequestDto.getUserRequestDto().getEmail())));
        apartmentTenantRepository.save(apartmentTenant);
        return apartmentTenant.getId();
    }

    public Long createRequest(Long apartmentTenantId, RequestDto requestDto) {
        if (!apartmentTenantRepository.existsById(apartmentTenantId)) {
            throw new NotFoundException("There is no such Apartment Tenant with id: " + apartmentTenantId);
        }
        Request request = modelMapper.map(requestDto, Request.class);
        request.setApartmentTenant(apartmentTenantRepository.findById(apartmentTenantId).get());
        requestRepository.save(request);
        return request.getId();
    }
}
