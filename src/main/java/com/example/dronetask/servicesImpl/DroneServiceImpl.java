package com.example.dronetask.servicesImpl;

import com.example.dronetask.constants.Constant;
import com.example.dronetask.dtos.*;
import com.example.dronetask.mappers.DroneMapper;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.DroneModel;
import com.example.dronetask.models.DroneState;
import com.example.dronetask.repositories.DroneRepository;
import com.example.dronetask.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final DroneMapper droneMapper;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper) {
        this.droneMapper = droneMapper;
        this.droneRepository = droneRepository;
    }
    @Override
    public DroneResponseDTO registerDrone(DroneRequestDTO droneRequestDTO) {
        Drone drone = droneMapper.dronerequestDtoToDrone(droneRequestDTO);
        drone.setDroneModel(classifyDroneModel(drone.getWeightLimit()));
        drone.setState(classifyDroneState(drone.getBatteryCapacity()));
        drone.setWeightLoaded(0.0);
        Drone repoDrone = droneRepository.save(drone);
        return droneMapper.droneToDroneResponseDto(repoDrone);
    }
    private DroneModel classifyDroneModel(double weightLoaded) {
        if (weightLoaded <= Constant.LIGHT_WEIGHT_MAX)
            return DroneModel.Lightweight;
        if (weightLoaded <= Constant.MIDDLE_WEIGHT_MAX)
            return DroneModel.Middleweight;
        if (weightLoaded <= Constant.CRUISER_WEIGHT_MAX)
            return DroneModel.Cruiserweight;
        return DroneModel.Heavyweight;
    }
    private DroneState classifyDroneState(int batteryCapacity) {
        if(batteryCapacity > Constant.BATTERY_LIMIT)
            return DroneState.LOADING;
        return DroneState.IDLE;
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
