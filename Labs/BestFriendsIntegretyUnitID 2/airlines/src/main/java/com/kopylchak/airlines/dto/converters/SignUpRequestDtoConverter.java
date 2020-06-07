package com.kopylchak.airlines.dto.converters;

import com.kopylchak.airlines.dto.SignUpRequestDto;
import com.kopylchak.airlines.entity.*;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class SignUpRequestDtoConverter implements Converter<SignUpRequestDto, Employee> {
    private ModelMapper modelMapper;

    public SignUpRequestDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addConverter(this);
    }

    @Override
    public Employee convert(MappingContext<SignUpRequestDto, Employee> context) {
        SignUpRequestDto dto = context.getSource();
        PersonalInfo personalInfo = modelMapper.map(dto, PersonalInfo.class);

        return Employee.builder()
            .office(Optional.ofNullable(dto.getOfficeId())
                .map(o -> Office.builder()
                    .id(dto.getOfficeId())
                    .build())
                .orElse(null))
            .airline(Optional.ofNullable(dto.getAirlineName())
                .map(n -> Airline.builder()
                    .name(dto.getAirlineName())
                    .build())
                .orElse(null))
            .position(dto.getPosition() == null ? null : new Position(dto.getPosition()))
            .roles(dto.getRoles().stream()
                .map(Role::new)
                .collect(Collectors.toList()))
            .personalInfo(personalInfo)
            .password(dto.getPassword())
            .build();
    }
}
