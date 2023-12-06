package com.example.dronetask.services;

import com.example.dronetask.dtos.*;
import com.example.dronetask.models.Drone;
import org.springframework.stereotype.Service;

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
    public AvailableDronesDTO listAvailableDronesForLoading();

    /**
     * Gets the Battery Capacity of given serial number of Drone
     *
     * @param serialNumber the serial number of the drone
     * @return the serial number and battery capacity of the drone
     */
    public DroneBatteryDTO getBatteryCapacity(String serialNumber);


}
