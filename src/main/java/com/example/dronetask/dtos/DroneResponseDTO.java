package com.example.dronetask.dtos;

import com.example.dronetask.models.DroneModel;
import com.example.dronetask.models.DroneState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DroneResponseDTO {
    private String serialNumber;
    private DroneModel droneModel;
    private Double weightLimit;
    private int batteryCapacity;
    private DroneState state;
    private Double weightLoaded;
}
