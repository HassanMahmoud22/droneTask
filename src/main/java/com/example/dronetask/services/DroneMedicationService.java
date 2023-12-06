package com.example.dronetask.services;
import com.example.dronetask.dtos.LoadDroneDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface DroneMedicationService {
    public ResponseEntity<?> loadDrone(LoadDroneDTO drontActivityDto);
    public LoadDroneDTO getDroneMedications(String serialnumber);
}
