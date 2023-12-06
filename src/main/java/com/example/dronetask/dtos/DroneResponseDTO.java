package com.example.dronetask.dtos;

import com.example.dronetask.models.DroneModel;
import com.example.dronetask.models.DroneState;
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
