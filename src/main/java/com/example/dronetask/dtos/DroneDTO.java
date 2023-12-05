package com.example.dronetask.dtos;

import com.example.dronetask.models.DroneModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public  class DroneDTO {


    private String serialNumber;

    private DroneModel droneModel;

    private Double weightLimit;

    private int batteryCapacity;

    private String state;

    private Double weightLoaded;
}