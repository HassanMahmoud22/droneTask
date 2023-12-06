package com.example.dronetask.history;

import com.example.dronetask.models.Drone;
import com.example.dronetask.servicesImpl.DroneServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class LoggerHistory implements History{
    static final Logger logger = Logger.getLogger(DroneServiceImpl.class.getName());
    @Override
    public void updateHistory(Drone drone, String message) {
        logger.info("Drone capacity check at "+ formatEventDate());
        logger.info("drone with serial number " + drone.getSerialNumber() + " has " + drone.getBatteryCapacity() + " battery capacity due to " + message);
    }


}