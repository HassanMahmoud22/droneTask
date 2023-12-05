package com.example.dronetask.services;

import com.example.dronetask.dtos.*;
import com.example.dronetask.models.Drone;
import org.springframework.stereotype.Service;
@Service
public interface DroneService {
    public DroneResponseDTO registerDrone(DroneRequestDTO droneRequestDTO);
    public AvailableDronesDTO listAvailableDrones();
    public DroneBatteryDTO checkBattery(String serialNumber);
}
