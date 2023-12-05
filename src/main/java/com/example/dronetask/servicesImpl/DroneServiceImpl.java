package com.example.dronetask.servicesImpl;

import com.example.dronetask.dtos.AvailableDronesDTO;
import com.example.dronetask.dtos.DroneBatteryDTO;
import com.example.dronetask.mappers.DroneMapper;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.DroneState;
import com.example.dronetask.repositories.DroneRepository;
import com.example.dronetask.dtos.DroneDTO;
import com.example.dronetask.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DroneServiceImpl implements DroneService {

    private DroneRepository droneRepository;
    private DroneMapper droneMapper;
    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper) {
        this.droneMapper = droneMapper;
        this.droneRepository = droneRepository;
    }
    private final int BATTERY_LIMIT = 25;
    @Override
    public DroneDTO registerDrone(Drone drone) {

        if(drone.getBatteryCapacity() > BATTERY_LIMIT) {
            drone.setState(DroneState.LOADING);
        }
        else {
            drone.setState(DroneState.IDLE);
        }
        drone.setWeightLoaded(0.0);
        Drone repoDrone = droneRepository.save(drone);
        return droneMapper.droneToDroneDto(repoDrone);
    }
    @Override
    public AvailableDronesDTO listAvailableDrones() {
        List<Drone> drones = droneRepository.findByState(DroneState.LOADING);
        return droneMapper.droneToAvailableDronesDTO(drones.size(), drones);
    }
    @Override
    public DroneBatteryDTO checkBattery(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return droneMapper.droneToBatteryDto(drone);
    }
}
