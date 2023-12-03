package com.example.dronetask.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DroneMedicationDTO {
    private List<LoadDroneDTO> droneDTOList;
}
