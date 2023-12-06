package com.example.dronetask.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DroneRequestDTO {

    @NotNull(message = "{validation.serialNumber.notEmpty}")
    @Size(min=5,max=100,message="Drone serial number must not be greater than 100 characters")
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String serialNumber;

    @DecimalMax(value = "500", message =" Drone cannot carry more than 500 gr")
    @DecimalMin(value = "1", message =" Drone must carry atleast 1 gr")
    @NotNull
    private Double weightLimit;

    @NotNull(message="Battery life must not be empty or null")
    @Max(value=100,message="Battery Capacity cannot be more than 100%")
    @Min(value=10,message="Battery Capacity cannot be less than 10%")
    private int batteryCapacity;
}
