package com.example.lab3.dto;

import com.example.lab3.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentTenantRequestDto {
    private String name;
    private String surname;
    private String address;
    private UserRequestDto userRequestDto;
}
