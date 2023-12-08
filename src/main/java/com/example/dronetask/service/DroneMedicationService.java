package com.example.dronetask.service;
import com.example.dronetask.dto.LoadDroneDTO;
import com.example.dronetask.dto.MedicationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DroneMedicationService {

    /**
     * loads Medications to Drone
     *
     * @param loadDroneDTO This DTO is serial number of Drone and List of Medications to be loaded
     */
    public void loadDrone(LoadDroneDTO loadDroneDTO);

    /**
     * Gets the Medications List which were loaded by given Drone
     *
     * @param serialnumber  The Serial Number of the Drone
     * @return              loadDroneDTO which have The List of Medications which loaded and the serial number of the Drone
     */
    public LoadDroneDTO getDroneMedications(String serialnumber);
}
