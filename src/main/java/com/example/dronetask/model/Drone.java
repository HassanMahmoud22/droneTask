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
    @NotNull(message = "Drone serial number mustn't be null")
    @Size(min = Constraints.MIN_SERIAL, max = Constraints.MAX_SERIAL, message = "Drone serial number must be in range " + Constraints.MIN_SERIAL + "and " + Constraints.MAX_SERIAL + " characters")
    @JsonProperty(required = true)
    @NotEmpty(message = "Drone serial number mustn't be empty")
    @NotBlank(message = "Drone serial number mustn't be blank")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "allowed only letters and numbers")
    private String serialNumber;

    @Column(name="model")
    @Enumerated(value = EnumType.STRING)
    @JsonProperty(required = true)
    @NotNull(message="Drone model must not be empty")
    private DroneModel droneModel;

    @Column(name="weight_limit")
    @DecimalMax(value = "500", message ="Drone cannot carry more than " + Constraints.WEIGHT_LIMIT_MAX + " gr")
    @DecimalMin(value = "1", message ="Drone must carry atleast " + Constraints.WEIGHT_LIMIT_MIN + " gr")
    @NotNull
    private Double weightLimit;

    @Column(name="battery_capacity")
    @NotNull(message = "Battery life must not be empty or null")
    @Max(value = Constraints.BATTERY_CAPACITY_MAX, message = "Battery Capacity cannot be more than " + Constraints.BATTERY_CAPACITY_MAX + "%")
    @Min(value = Constraints.BATTERY_CAPACITY_MIN, message = "Battery Capacity cannot be less than " + Constraints.BATTERY_CAPACITY_MIN + "%")
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