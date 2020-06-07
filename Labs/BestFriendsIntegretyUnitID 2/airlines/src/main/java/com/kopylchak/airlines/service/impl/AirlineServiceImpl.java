package com.kopylchak.airlines.service.impl;

import com.kopylchak.airlines.dto.AirlineCreationDto;
import com.kopylchak.airlines.dto.AirlineUpdateDto;
import com.kopylchak.airlines.dto.AirlineResponseDto;
import com.kopylchak.airlines.dto.TripResponseDto;
import com.kopylchak.airlines.dto.filter.AirlineFilterDto;
import com.kopylchak.airlines.dto.filter.TripFilterDto;
import com.kopylchak.airlines.entity.Airline;
import com.kopylchak.airlines.repository.AirlineRepository;
import com.kopylchak.airlines.repository.TripRepository;
import com.kopylchak.airlines.service.AirlineService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirlineServiceImpl implements AirlineService {
    private AirlineRepository airlineRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AirlineServiceImpl(AirlineRepository airlineRepository, ModelMapper modelMapper) {
        this.airlineRepository = airlineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AirlineResponseDto> getAirlines(AirlineFilterDto filter) {
        return airlineRepository.findAll(
            AirlineRepository.nameContains(filter.getName())
                .and(AirlineRepository.addressContains(filter.getAddress()))).stream()
            .map(a -> modelMapper.map(a, AirlineResponseDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public AirlineResponseDto getAirline(String airlineId) {
        return modelMapper.map(airlineRepository.findById(airlineId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "There is no airline with given name.")), AirlineResponseDto.class);
    }

    @Override
    public AirlineResponseDto createAirline(AirlineCreationDto airlineDto) {
        if (airlineRepository.existsById(airlineDto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Airline with such name already exists.");
        }

        Airline airline = modelMapper.map(airlineDto, Airline.class);

        try {
            airlineRepository.save(airline);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Website link already registered.");
        }

        return modelMapper.map(airline, AirlineResponseDto.class);
    }

    @Override
    public AirlineResponseDto updateAirline(AirlineUpdateDto airlineDto, String airlineName) {
        Airline airline = airlineRepository.findById(airlineName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "There is no airline with given name"));

        try {
            modelMapper.map(airlineDto, airline);

            airlineRepository.save(airline);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Airline with such name or website already exists.");
        }

        return modelMapper.map(airline, AirlineResponseDto.class);
    }
}
