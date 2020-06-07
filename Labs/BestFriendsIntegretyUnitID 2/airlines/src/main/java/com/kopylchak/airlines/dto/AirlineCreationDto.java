package com.kopylchak.airlines.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AirlineCreationDto {
    @ApiModelProperty(name = "name", notes = "Name of company. Should be unique.")
    @NotEmpty
    private String name;

    @ApiModelProperty(name = "locationAddress", notes = "Address of airline.")
    @NotEmpty
    private String locationAddress;

    @ApiModelProperty(name = "webSite", notes = "Airline website link. Should be unique.")
    @NotEmpty
    private String webSite;
}
