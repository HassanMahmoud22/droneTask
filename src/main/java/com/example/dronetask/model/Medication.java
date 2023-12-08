package com.example.dronetask.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="medications")
@Data
public class Medication {

    @Column(name = "medication_name")
    @NotNull()
    private String name;
    @Column(name ="weight")
    private Double weight;
    @Column(name="medication_code")
    @Id
    private String code;
    @Column(name ="picture_url")
    private String image;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_id")
    @JsonIgnore
    @JsonBackReference
    private Drone droneId;
    public Medication() {

    }
    public Medication(String name, Double weight, String code, String image, Drone droneId) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.droneId = droneId;
    }

}
