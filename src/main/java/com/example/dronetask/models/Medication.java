package com.example.dronetask.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="medications")
@Data
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "medication_name")
    @NotNull()
    private String name;
    @Column(name ="weight")
    private Double weight;
    @Column(name="medication_code")
    private String code;
    @Column(name ="picture_url")
    private String image;
}