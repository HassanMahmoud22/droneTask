package com.example.dronetask.controller;

import com.example.dronetask.dto.LoadDroneDTO;
import com.example.dronetask.service.DroneMedicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"/api/v1/drones/DroneMedication"})
public class DroneMedicationController {
    @Autowired
    private DroneMedicationService droneMedicationService;
    @PostMapping(value="/load")
    public ResponseEntity<?> loadDrone(@Valid @RequestBody LoadDroneDTO loadDroneDTO){
        return ResponseEntity.status(HttpStatus.OK).body(loadDroneDTO);
    }

    @GetMapping(value="/getMedications/{serialNumber}")
    public ResponseEntity<?> getMedications(@PathVariable("serialNumber") String serialNumber){
        return ResponseEntity.status(HttpStatus.OK).body(droneMedicationService.getDroneMedications(serialNumber));
    }
}