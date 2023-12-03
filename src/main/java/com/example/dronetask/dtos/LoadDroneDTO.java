package com.example.dronetask.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class LoadDroneDTO {
    @NotNull(message = "Drone serial number can't be null")
    @Size(min=3,max=100,message="Drone serial number must not be greater than {value} characters")
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String serialNumber;

    private String droneModel;

    @JsonProperty(required = false)
    private String droneState;

    @JsonProperty(required = true)
    @NotNull(message="Quantity must not be empty")
    private Integer quantity;

    @JsonProperty(required = true)
    @NotNull(message="Total weight must not be empty")
    private Double totalWeight;

    @JsonProperty(required = true)
    private String deliveryAddress;

    @JsonProperty(required = true)
    @NotEmpty(message="Add medications before loading to drone")
    private List<MedicationDTO> medications;
}
