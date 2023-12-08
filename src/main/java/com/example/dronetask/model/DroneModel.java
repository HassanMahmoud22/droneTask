package com.example.dronetask.model;

import com.example.dronetask.constant.Constraints;

public enum DroneModel {
        Lightweight, Middleweight, Cruiserweight, Heavyweight;

        /**
         * Classifies Drone Model depending on weight limit of the Drone
         *
         * @param weightLimit the maximum weight drone can load to classify drone model depending on it
         * @return The DroneModel after being classified
         */
        public static DroneModel classifyDroneModel(double weightLimit) {
                // check if weightLimit is smaller than or equal specified maximum weight that lightweight Drones can hold
                if (weightLimit <= Constraints.LIGHT_WEIGHT_MAX) {
                        return DroneModel.Lightweight;
                        // check if weightLimit is smaller than or equal specified middle weight that middleweight Drones can hold
                } else if (weightLimit <= Constraints.MIDDLE_WEIGHT_MAX) {
                        return DroneModel.Middleweight;
                        // check if weightLimit is smaller than or equal specified cruiser weight that cruiserweight Drones can hold
                } else if (weightLimit <= Constraints.CRUISER_WEIGHT_MAX) {
                        return DroneModel.Cruiserweight;
                } else {
                        return DroneModel.Heavyweight;
                }
        }
}

