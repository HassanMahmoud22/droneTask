package com.example.dronetask.simulation;

import com.example.dronetask.model.Drone;
import com.example.dronetask.model.DroneState;

public class Simulation {

    /**
     * Simulates Drone Activity by decreasing current Battery Capacity
     * and sets its state to next state
     *
     * @param drone      The drone which its activities will be simulated
     * @param droneState The next state of the drone after simulation
     */
    public static void simulateDroneActivity(Drone drone, DroneState droneState) {
        drone.setState(droneState);
        drone.setBatteryCapacity(drone.getBatteryCapacity() - generateRandom());
    }

    /**
     * This is a helping function for simulation
     * which generate random number to be decreased in battery capacity
     *
     * @return random number from 1 to 5
     */
    private static int generateRandom() {
        int min = 1;
        int max = 5;
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
