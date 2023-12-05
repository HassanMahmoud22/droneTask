package com.example.dronetask.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name ="drones")
@Data
public class Drone {

    @Id
    @Column(name="serial_number", length = 100)
    @NotNull(message = "serial number must not be empty")
    @Size(min=5,max=100,message="Drone serial number must not be greater than 100 characters")
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
    @DecimalMax(value = "500", message =" Drone cannot carry more than 500 gr")
    @DecimalMin(value = "1", message =" Drone must carry atleast 1 gr")
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
    @JsonIgnore
    private Double weightLoaded;
}