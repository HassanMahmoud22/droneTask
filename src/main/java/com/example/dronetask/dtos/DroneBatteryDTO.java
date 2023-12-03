package com.example.dronetask.dtos;

import lombok.Data;

@Data
public class DroneBatteryDTO {
    private String serialNumber;
    private int batteryCapacity;
}
