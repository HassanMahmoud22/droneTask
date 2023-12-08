package com.example.dronetask.dto;

import lombok.Data;

@Data
public class DroneBatteryDTO {
    private String serialNumber;
    private int batteryCapacity;
    public DroneBatteryDTO() {

    }
    public DroneBatteryDTO(String serialNumber, int batteryCapacity) {
        this.serialNumber = serialNumber;
        this.batteryCapacity = batteryCapacity;
    }
}
