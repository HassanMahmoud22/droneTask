package com.example.dronetask.model;

import com.example.dronetask.constant.Constraints;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name ="drones")
@Data
public class Drone {

    @Id
    @Column(name="serial_number", length = 100)
    @NotNull(message = "serial number must not be empty")
    @Size(min=5,max=100,message="Drone serial number must be between 5 and 100 characters")
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String serialNumber;
    //not one of the values accepted for Enum class: [Middleweight, Lightweight, Heavyweight, Cruiserweight]]
    @Column(name="model")
    @Enumerated(value = EnumType.STRING)
    @JsonProperty(required = true)
    @NotNull(message="Drone model must not be empty")
    private DroneModel droneModel;

    @Column(name="weight_limit")
    @Max(value = Constraints.HEAVY_WEIGHT_MAX, message =" Drone cannot carry more than " + Constraints.HEAVY_WEIGHT_MAX + "gr")
    @Min(value = 1, message =" Drone must carry at least 1 gr")
    @NotNull
    private Double weightLimit;

    @Column(name="battery_capacity")
    @JsonProperty(required = true)
    @NotNull(message="Battery life must not be empty or null")
    @Max(value=100,message="Battery Capacity cannot be more than 100%")
    @Min(value=10,message="Battery Capacity cannot be less than 10%")
    private int batteryCapacity;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    @JsonIgnore
    private DroneState state;

    @Column(name = "weight_loaded")
    private Double weightLoaded;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "droneId", cascade = CascadeType.ALL)
    private List<Medication> medications;

    public Drone() {
    }

    public Drone(String serialNumber, DroneModel droneModel, Double weightLimit, int batteryCapacity, DroneState state, Double weightLoaded, List<Medication> medications) {
        this.serialNumber = serialNumber;
        this.droneModel = droneModel;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.weightLoaded = weightLoaded;
        this.medications = medications;
    }
    public Drone(String serialNumber, DroneModel droneModel, Double weightLimit, int batteryCapacity, DroneState state, Double weightLoaded) {
        this.serialNumber = serialNumber;
        this.droneModel = droneModel;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.weightLoaded = weightLoaded;
    }
}