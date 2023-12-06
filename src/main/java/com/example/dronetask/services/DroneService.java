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

    /**
     * Gets Drone by given serial number
     *
     * @param serialNumber The serial number of the Drone
     * @return The Drone needed if exists otherwise null
     */
    public Drone getDroneBySerialNumber(String serialNumber);

    /**
     * updates Drone in Database
     *
     * @param drone The Drone to be updated
     */
    public void updateDrone(Drone drone);

    /**
     * check if the Drone doesn't have any free space then change its state to loaded
     *
     * @param drone The Drone to be checked
     */
    public void updateDroneStateIfFilled(Drone drone);

    /**
     * checks if drone has enough free space to can load medications weights
     *
     * @param drone                   The drone which its free space checked
     * @param totalMedicationsWeights The total weight of medications needed to be loaded
     * @return true if there is free space, false if there is no free space
     */
    public boolean isDroneHaveSpace(Drone drone , double totalMedicationsWeights);

    /**
     * checks if drone exists and in loading state
     *
     * @param drone The drone which its state being checked
     * @return true if it's not null and its state is loading, otherwise return false
     */
    public boolean isDroneSuitable(Drone drone);
}
