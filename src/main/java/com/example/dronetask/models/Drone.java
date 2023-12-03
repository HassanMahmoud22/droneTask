package com.example.dronetask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name ="drones")
@Data
public class Drone {

    @Id
    @Column(name="serial_number", length = 100)
    private String serialNumber;

    @Column(name="model")
    @Enumerated(value = EnumType.STRING)
    private DroneModel droneModel;

    @Column(name="weight_limit")
    private Double weightLimit;

    @Column(name="battery_capacity")
    private Integer batterCapacity;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private DroneState state;

    @Column(name = "current_weight")
    private Double currentWeight;
}