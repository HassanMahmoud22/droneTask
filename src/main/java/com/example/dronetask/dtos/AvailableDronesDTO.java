package com.example.dronetask.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AvailableDronesDTO {
    private List<DroneResponseDTO> drones;
}
