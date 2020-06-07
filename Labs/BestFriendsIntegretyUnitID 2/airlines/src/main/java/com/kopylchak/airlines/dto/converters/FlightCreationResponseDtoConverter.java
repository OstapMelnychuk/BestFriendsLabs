package com.kopylchak.airlines.dto.converters;

import com.kopylchak.airlines.dto.FlightCreationResponseDto;
import com.kopylchak.airlines.entity.Flight;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightCreationResponseDtoConverter implements Converter<Flight, FlightCreationResponseDto> {
    @Autowired
    public FlightCreationResponseDtoConverter(ModelMapper modelMapper) {
//        modelMapper.addConverter(this);
    }

    @Override
    public FlightCreationResponseDto convert(MappingContext<Flight, FlightCreationResponseDto> context) {
        Flight flight = context.getSource();

        return FlightCreationResponseDto.builder()
            .id(flight.getId())
            .plannedArrivalTime(flight.getPlannedArrivalTime())
            .plannedDepartureTime(flight.getPlannedDepartureTime())
            .actualArrivalTime(flight.getActualArrivalTime())
            .actualDepartureTime(flight.getActualDepartureTime())
            .gate(flight.getGate())
            .planeId(flight.getPlane().getId())
            .tripId(flight.getTrip().getId())
            .build();
    }
}
