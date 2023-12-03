package com.example.dronetask.dtos;

import com.example.dronetask.models.DroneModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public  class DroneDTO {

    @NotNull(message = "serial number must not be empty")
    @Size(min=5,max=100,message="Drone serial number must not be greater than {value} characters")
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String serialNumber;

    @JsonProperty(required = true)
    @NotNull(message="Drone model must not be empty")
    private DroneModel droneModel;

    @DecimalMax(value = "500", message =" Drone cannot carry more than 500 gr")
    private Double weightLimit;

    @JsonProperty(required = true)
    @NotNull(message="Battery life must not be empty or null")
    @Max(value=100,message="Battery Capacity cannot be more than 100%")
    @Min(value=0,message="Battery Capacity cannot be less than 0%")
    private Integer batterCapacity;

    @JsonIgnore
    private String droneState;

    private Double weightLoaded;
}