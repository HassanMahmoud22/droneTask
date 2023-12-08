package com.example.dronetask.dto;

import com.example.dronetask.constant.Constraints;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class LoadDroneDTO {
    @NotNull(message = "Drone serial number mustn't be null")
    @Size(min = Constraints.MIN_SERIAL, max = Constraints.MAX_SERIAL, message = "Drone serial number must be in range " + Constraints.MIN_SERIAL + "and " + Constraints.MAX_SERIAL + " characters")
    @JsonProperty(required = true)
    @NotEmpty(message = "Drone serial number mustn't be empty")
    @NotBlank(message = "Drone serial number mustn't be blank")
    @Pattern (regexp = "^[^\\s@]+$", message = "allowed only letters and numbers")
    private String serialNumber;

    @JsonProperty(required = true)
    @NotEmpty(message="Add medications before loading to drone")
    @Valid
    private List<MedicationDTO> medications;

    public LoadDroneDTO() {
    }

    public LoadDroneDTO(String serialNumber, List<MedicationDTO> medications) {
        this.serialNumber = serialNumber;
        this.medications = medications;
    }
}
