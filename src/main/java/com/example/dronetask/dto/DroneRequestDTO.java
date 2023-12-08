package com.example.dronetask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import com.example.dronetask.constant.Constraints;
import lombok.Data;

@Data
public class DroneRequestDTO {

    public DroneRequestDTO() {
    }

    public DroneRequestDTO(String serialNumber, Double weightLimit, int batteryCapacity) {
        this.serialNumber = serialNumber;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
    }

    @NotNull(message = "Drone serial number mustn't be null")
    @Size(min = Constraints.MIN_SERIAL, max = Constraints.MAX_SERIAL, message = "Drone serial number must be in range " + Constraints.MIN_SERIAL + "and " + Constraints.MAX_SERIAL + " characters")
    @JsonProperty(required = true)
    @NotEmpty(message = "Drone serial number mustn't be empty")
    @NotBlank(message = "Drone serial number mustn't be blank")
    @Pattern(regexp = "^[^\\s@]+$", message = "allowed only letters and numbers")
    private String serialNumber;

    @DecimalMax(value = "500", message ="Drone cannot carry more than " + Constraints.WEIGHT_LIMIT_MAX + " gr")
    @DecimalMin(value = "1", message ="Drone must carry atleast " + Constraints.WEIGHT_LIMIT_MIN + " gr")
    @NotNull
    private Double weightLimit;

    @NotNull(message="Battery life must not be empty or null")
    @Max(value = Constraints.BATTERY_CAPACITY_MAX, message = "Battery Capacity cannot be more than " + Constraints.BATTERY_CAPACITY_MAX + "%")
    @Min(value = Constraints.BATTERY_CAPACITY_MIN, message = "Battery Capacity cannot be less than " + Constraints.BATTERY_CAPACITY_MIN + "%")
    private int batteryCapacity;
}
