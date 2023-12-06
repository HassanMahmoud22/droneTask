package com.example.dronetask.models;

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
    private Drone droneId;
}
