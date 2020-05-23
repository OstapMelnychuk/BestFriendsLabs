package com.savchuk.secondlab.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private String name;
    private String description;
    private String data;
    private Long shop_id;
    private Long customer_id;
}
