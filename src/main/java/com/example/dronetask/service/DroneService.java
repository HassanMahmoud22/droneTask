package com.example.dronetask.service;

import com.example.dronetask.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DroneService {
    /**
     * Registers the drone to Database after its data being set.
     *
     * @param droneRequestDTO some data of drone entity since the other data are being set by application
     * @return the response data of the drone
     */
    public DroneResponseDTO registerDrone(DroneRequestDTO droneRequestDTO);

    /**
     * lists all available drones for loading (Their state is Loading)
     *
     * @return all drones in loading state
     */
    public List<DroneResponseDTO> listAvailableDronesForLoading();

    /**
     * Gets the Battery Capacity of given serial number of Drone
     *
     * @param serialNumber the serial number of the drone
     * @return the serial number and battery capacity of the drone
     */
    public DroneBatteryDTO getBatteryCapacity(String serialNumber);
}
