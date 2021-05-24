package com.example.lab3.service;

import com.example.lab3.dto.RequestDto;
import com.example.lab3.entity.Request;
import com.example.lab3.exception.NotFoundException;
import com.example.lab3.repository.RequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RequestService(RequestRepository requestRepository, ModelMapper modelMapper) {
        this.requestRepository = requestRepository;
        this.modelMapper = modelMapper;
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    public Request findOneById(Long id){
        if (requestRepository.existsById(id)){
            return requestRepository.findById(id).get();
        }
        throw new NotFoundException("There is no such request with id: " + id);
    }

    public void deleteById(Long id) {
        if (!requestRepository.existsById(id)) {
            throw new NotFoundException("There is no such request with id: " + id);
        }
        requestRepository.deleteById(id);
    }

    public Long update(RequestDto requestDto) {
        if (!requestRepository.existsById(requestDto.getId())) {
            throw new NotFoundException("There is no such request with id: " + requestDto.getId());
        }

        Request request = modelMapper.map(requestDto, Request.class);
        request.setApartmentTenant(requestRepository.findById(requestDto.getId()).get().getApartmentTenant());
        requestRepository.save(request);
        return request.getId();
    }
}
