package com.example.dronetask.controllers;

import com.example.dronetask.dtos.LoadDroneDTO;
import com.example.dronetask.services.DroneMedicationService;
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
    public ResponseEntity<?> loadDrone(@Valid @RequestBody LoadDroneDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(droneMedicationService.loadDrone(dto));
    }

    @GetMapping(value="/getMedications/{serialNumber}")
    public ResponseEntity<?> getMedications(@PathVariable("serialNumber")String serialNumber){
        return ResponseEntity.status(HttpStatus.OK).body(droneMedicationService.getDroneMedications(serialNumber));
    }
}