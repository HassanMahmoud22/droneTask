package com.example.dronetask.controllers;
import com.example.dronetask.dtos.*;
import com.example.dronetask.services.DroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/api/v1/drones"}, produces = APPLICATION_JSON_VALUE)
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping(value="/registerDrone",consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerDrone(@Valid @RequestBody DroneRequestDTO droneRequestDTO){
        DroneResponseDTO droneResponseDTO = droneService.registerDrone(droneRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(droneResponseDTO);
    }

    @GetMapping(path = "/batteryCapacity/{serialNumber}")
    public ResponseEntity<?> getDroneBattery(@PathVariable("serialNumber")String serialNumber){
        return ResponseEntity.status(HttpStatus.OK).body(droneService.getBatteryCapacity(serialNumber));
    }

    @GetMapping("/available")
    public ResponseEntity<AvailableDronesDTO> findAvailableDrones(){
        return ResponseEntity.status(HttpStatus.OK).body(droneService.listAvailableDronesForLoading());
    }
}
