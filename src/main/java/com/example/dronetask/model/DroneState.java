package com.example.dronetask.model;

import com.example.dronetask.constant.Constraints;

public enum DroneState {
    IDLE,
    LOADING,
    LOADED,
    DELIVERING,
    DELIVERED,
    RETURNING;

    /**
     * Classifies the Drone state depending on Battery Capacity
     *
     * @param batteryCapacity the battery capacity of drone to classify drone state based on it
     * @return the state of the drone after classification
     */
    public static DroneState classifyDroneState(int batteryCapacity) {
        //if battery capacity > specified battery limit (25%)
        if (batteryCapacity > Constraints.BATTERY_LIMIT) {
            return DroneState.LOADING;
        } else {
            return DroneState.IDLE;
        }
    }

}