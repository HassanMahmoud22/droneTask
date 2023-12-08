package com.example.dronetask.history;

import com.example.dronetask.model.Drone;
import com.example.dronetask.serviceImpl.DroneServiceImpl;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
@Component
public class LoggerHistory implements History{
    static final Logger logger = Logger.getLogger(DroneServiceImpl.class.getName());

    /**
     * updates The History with new data
     *
     * @param drone   drone updated data
     * @param event   The event which changed the data
     */
    @Override
    public void updateHistory(Drone drone, String event) {
        logger.info("Drone capacity check at "+ formatEventDate());
        logger.info("drone with serial number " + drone.getSerialNumber() + " has " + drone.getBatteryCapacity() + " battery capacity due to " + event);
    }


}
