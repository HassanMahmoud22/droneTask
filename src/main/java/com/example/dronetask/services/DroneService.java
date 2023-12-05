package com.example.dronetask.services;

import com.example.dronetask.dtos.AvailableDronesDTO;
import com.example.dronetask.dtos.DroneBatteryDTO;
import com.example.dronetask.dtos.DroneDTO;
import com.example.dronetask.models.Drone;
import org.springframework.stereotype.Service;
@Service
public interface DroneService {
    public DroneDTO registerDrone(Drone drone);
    public AvailableDronesDTO listAvailableDrones();
    public DroneBatteryDTO checkBattery(String serialNumber);
}
