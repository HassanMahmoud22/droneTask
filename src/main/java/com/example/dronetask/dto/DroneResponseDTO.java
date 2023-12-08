package com.example.dronetask.dto;

import com.example.dronetask.model.DroneModel;
import com.example.dronetask.model.DroneState;
import lombok.Data;

@Data
public class DroneResponseDTO {

    private String serialNumber;
    private DroneModel droneModel;
    private Double weightLimit;
    private int batteryCapacity;
    private DroneState state;
    private Double weightLoaded;

    public DroneResponseDTO() {
    }

    public DroneResponseDTO(String serialNumber, DroneModel droneModel, Double weightLimit, int batteryCapacity, DroneState state, Double weightLoaded) {
        this.serialNumber = serialNumber;
        this.droneModel = droneModel;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.weightLoaded = weightLoaded;
    }
}
