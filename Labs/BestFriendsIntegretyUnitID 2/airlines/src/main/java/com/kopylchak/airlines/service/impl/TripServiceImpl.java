package com.kopylchak.airlines.service.impl;

import com.kopylchak.airlines.dto.TripResponseDto;
import com.kopylchak.airlines.repository.TripRepository;
import com.kopylchak.airlines.service.TripService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {
    private TripRepository tripRepository;
    private ModelMapper modelMapper;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TripResponseDto> getTrips(String airlineName) {
        return tripRepository.findAllByAirlineName(airlineName).stream()
            .map(t -> modelMapper.map(t, TripResponseDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<TripResponseDto> getTrips() {
        return tripRepository.findAll().stream()
            .map(t -> modelMapper.map(t, TripResponseDto.class))
            .collect(Collectors.toList());
    }
}
