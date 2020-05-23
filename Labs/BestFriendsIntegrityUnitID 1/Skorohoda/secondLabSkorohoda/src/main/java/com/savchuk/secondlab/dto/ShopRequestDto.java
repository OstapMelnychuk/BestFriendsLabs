package com.savchuk.secondlab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopRequestDto {
    private String name;
    private String country;
    private String city;
}
