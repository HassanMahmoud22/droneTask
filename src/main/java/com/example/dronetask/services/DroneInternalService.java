package com.example.dronetask.services;

import com.example.dronetask.models.Drone;
import com.example.dronetask.models.DroneState;

public interface DroneInternalService {
    /**
     * Gets Drone by given serial number
     *
     * @param serialNumber The serial number of the Drone
     * @return The Drone needed if exists otherwise null
     */
    public Drone getDroneBySerialNumber(String serialNumber);

    public Drone getDroneBySerialNumberAndState(String serialNumber, DroneState state);

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

}
