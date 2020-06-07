package com.kopylchak.airlines.dto.converters;

import com.kopylchak.airlines.dto.FlightCreationRequestDto;
import com.kopylchak.airlines.entity.Airline;
import com.kopylchak.airlines.entity.Flight;
import com.kopylchak.airlines.entity.Plane;
import com.kopylchak.airlines.entity.Trip;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightCreationRequestDtoConverter implements Converter<FlightCreationRequestDto, Flight> {
    @Autowired
    public FlightCreationRequestDtoConverter(ModelMapper modelMapper) {
        modelMapper.addConverter(this);
    }

    @Override
    public Flight convert(MappingContext<FlightCreationRequestDto, Flight> context) {
        FlightCreationRequestDto dto = context.getSource();

        Plane plane = Plane.builder()
            .id(dto.getPlaneId())
            .build();

        Trip trip = Trip.builder()
            .id(dto.getTripId())
            .build();

        Airline airline = Airline.builder()
            .name(dto.getAirlineName())
            .build();

        return Flight.builder()
            .plannedArrivalTime(dto.getPlannedArrivalTime())
            .plannedDepartureTime(dto.getPlannedDepartureTime())
            .gate(dto.getGate())
            .plane(plane)
            .trip(trip)
            .airline(airline)
            .build();
    }
}
