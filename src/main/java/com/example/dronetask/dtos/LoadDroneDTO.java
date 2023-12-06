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
    @Size(min=3,max=100,message="Drone serial number must be between 5 and 100 characters")
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String serialNumber;
    @JsonProperty(required = true)
    @NotEmpty(message="Add medications before loading to drone")
    private List<MedicationDTO> medications;
}
