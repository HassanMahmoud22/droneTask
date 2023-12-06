package com.example.dronetask.services;
import com.example.dronetask.dtos.LoadDroneDTO;
import com.example.dronetask.dtos.MedicationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DroneMedicationService {

    /**
     * loads Medications to Drone
     *
     * @param loadDroneDTO This DTO is serial number of Drone and List of Medications to be loaded
     * @return             ResponseEntity with http status and Message or Object in body
     */
    public List<MedicationDTO> loadDrone(LoadDroneDTO loadDroneDTO);

    /**
     * Gets the Medications List which were loaded by given Drone
     *
     * @param serialnumber  The Serial Number of the Drone
     * @return              loadDroneDTO which have The List of Medications which loaded and the serial number of the Drone
     */
    public LoadDroneDTO getDroneMedications(String serialnumber);
}
