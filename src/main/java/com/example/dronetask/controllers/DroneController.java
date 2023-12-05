package com.example.dronetask.controllers;
import com.example.dronetask.dtos.DroneDTO;
import com.example.dronetask.dtos.DroneBatteryDTO;
import com.example.dronetask.dtos.AvailableDronesDTO;
import com.example.dronetask.models.Drone;
import com.example.dronetask.services.DroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/api/v1/drones"}, produces = APPLICATION_JSON_VALUE)
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping(value="/register",consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<DroneDTO> registerDrone(@Valid @RequestBody Drone drone){
        DroneDTO serviceDroneDTO = droneService.registerDrone(drone);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceDroneDTO);
    }

    @GetMapping(path = "/{serialNumber}")
    public ResponseEntity<?> getDroneBattery(@PathVariable("serialNumber")String serialNumber){
        DroneBatteryDTO dto = droneService.checkBattery(serialNumber);
        if(dto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no drone with this id");
    }

    @GetMapping("/available")
    public ResponseEntity<AvailableDronesDTO> findAvailableDrones(){
        return ResponseEntity.status(HttpStatus.OK).body(droneService.listAvailableDrones());
    }
}