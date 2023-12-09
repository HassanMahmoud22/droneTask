package com.example.dronetask.serviceImpl;

import com.example.dronetask.constant.Constraints;
import com.example.dronetask.constant.Message;
import com.example.dronetask.dto.*;
import com.example.dronetask.exceptionHandler.DroneNotFoundException;
import com.example.dronetask.exceptionHandler.EmptyDataException;
import com.example.dronetask.history.History;
import com.example.dronetask.mapper.DroneMapper;
import com.example.dronetask.model.Drone;
import com.example.dronetask.model.DroneModel;
import com.example.dronetask.model.DroneState;
import com.example.dronetask.repository.DroneRepository;
import com.example.dronetask.service.DroneInternalService;
import com.example.dronetask.service.DroneService;
import com.example.dronetask.simulation.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@EnableScheduling
public class DroneServiceImpl implements DroneService, DroneInternalService {

    private final DroneRepository droneRepository;
    private final DroneMapper droneMapper;
    private final History history;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper, History history) {
        this.droneMapper = droneMapper;
        this.droneRepository = droneRepository;
        this.history = history;
    }

    /**
     * Registers the drone to Database after its data being set.
     *
     * @param droneRequestDTO some data of drone entity since the other data are being set by application
     * @return the response data of the drone
     */
    @Override
    public DroneResponseDTO registerDrone(DroneRequestDTO droneRequestDTO) {
        validateDroneRequestDTO(droneRequestDTO);
        Drone drone = droneMapper.dronerequestDtoToDrone(droneRequestDTO);
        drone.setDroneModel(DroneModel.classifyDroneModel(drone.getWeightLimit()));
        drone.setState(DroneState.classifyDroneState(drone.getBatteryCapacity()));
        drone.setWeightLoaded(0.0);
        //save this drone to database and return droneResponseDTO
        Drone repositoryDrone = droneRepository.save(drone);
        return droneMapper.droneToDroneResponseDto(repositoryDrone);
    }

    /**
     * lists all available drones for loading (Their state is Loading)
     *
     * @return all drones in loading state
     */
    @Override
    public List<DroneResponseDTO> listAvailableDronesForLoading() {
        List<Drone> drones = droneRepository.findByState(DroneState.LOADING);
        return droneMapper.dronesToDroneResponseDtos(drones);
    }

    /**
     * Gets the Battery Capacity of given serial number of Drone
     *
     * @param serialNumber the serial number of the drone
     * @return             the serial number and battery capacity of the drone
     */
    @Override
    public DroneBatteryDTO getBatteryCapacity(String serialNumber) {
        Drone drone = getDroneBySerialNumber(serialNumber);
        return droneMapper.droneToBatteryDto(drone);
    }

    /**
     * This is a simulation of delivering the Medications
     */
    @Scheduled (fixedRate = 3000)
    private void deliverMedication() {
        List<Drone> drones = droneRepository.findByState(DroneState.LOADED);
        for (Drone drone : drones) {
            Simulation.simulateDroneActivity(drone, DroneState.DELIVERING);
            history.updateHistory(drone, Message.DELIVERING);
        }
        droneRepository.saveAll(drones);
    }

    /**
     * This is a simulation of unloading the Medications
     */
    @Scheduled (fixedRate = 6000)
    private void unloadDrone() {
        List<Drone> drones = droneRepository.findByState(DroneState.DELIVERING);
        for (Drone drone : drones) {
            Simulation.simulateDroneActivity(drone, DroneState.DELIVERED);
            drone.setWeightLoaded(0.0);
            history.updateHistory(drone, Message.UNLOADING);
        }
        droneRepository.saveAll(drones);
    }

    /**
     * This is a simulation of returning drone to the Base the Medications
     */
    @Scheduled (fixedRate = 8000)
    private void returnDrone() {
        List<Drone> drones = droneRepository.findByState(DroneState.DELIVERED);
        for (Drone drone : drones) {
            Simulation.simulateDroneActivity(drone, DroneState.RETURNING);
            history.updateHistory(drone, Message.RETURNING);
        }
        droneRepository.saveAll(drones);
    }

    /**
     * This is a simulation of Landing the Drone on the Base
     */
    @Scheduled (fixedRate = 10000)
    private void landDrone() {
        List<Drone> drones = droneRepository.findByState(DroneState.RETURNING);
        for (Drone drone : drones) {
            if (drone.getBatteryCapacity() <= Constraints.BATTERY_LIMIT)
                Simulation.simulateDroneActivity(drone, DroneState.IDLE);
            else
                Simulation.simulateDroneActivity(drone, DroneState.LOADING);
            history.updateHistory(drone, Message.LANDING);
        }
        droneRepository.saveAll(drones);
    }

    /**
     * Gets Drone by given serial number
     *
     * @param serialNumber The serial number of the Drone
     * @return The Drone needed if exists otherwise null
     */
    public Drone getDroneBySerialNumber(String serialNumber) {
        return droneRepository.findById(serialNumber).orElseThrow(() -> new DroneNotFoundException(Message.DRONE_DOESNT_EXIST));
    }

    /**
     * Gets Drone by serial number and state
     * @param serialNumber  The serial number to search with
     * @param state         The state of the drone to search with
     * @return              return drone if exists
     */
    @Override
    public Drone getDroneBySerialNumberAndState(String serialNumber, DroneState state) {
        Drone drone = droneRepository.findBySerialNumberAndState(serialNumber, state);
        throwExceptionIfDroneNotExist(drone, " in " + state + " state");
        return drone;
    }

    /**
     * updates Drone in Database
     *
     * @param drone The Drone to be updated
     */
    public void updateDrone(Drone drone) {
         droneRepository.save(drone);
    }

    /**
     * check if the Drone doesn't have any free space then change its state to loaded
     *
     * @param drone The Drone to be checked
     */
    public void updateDroneStateIfFilled(Drone drone) {
        throwExceptionIfDroneNotExist(drone, "");
        if (Double.compare(drone.getWeightLoaded(), drone.getWeightLimit()) == 0)
            drone.setState(DroneState.LOADED);
    }

    /**
     * checks if drone has enough free space to can load medications weights
     *
     * @param drone                   The drone which its free space checked
     * @param totalMedicationsWeights The total weight of medications needed to be loaded
     * @return true if there is free space, false if there is no free space
     */
    public boolean isDroneHasSpace(Drone drone, double totalMedicationsWeights) {
       throwExceptionIfDroneNotExist(drone, "");
       double availableWeight = drone.getWeightLimit() - drone.getWeightLoaded();
       return !(availableWeight < totalMedicationsWeights);
    }

    /**
     * Throws Exception if drone is null
     *
     * @param drone     The drone to be checked on
     * @param message   The printed message in exception
     */
    private void throwExceptionIfDroneNotExist(Drone drone, String message) {
        if(drone == null)
            throw new DroneNotFoundException(Message.DRONE_DOESNT_EXIST + message);
    }

    /**
     * Throws Exception if given serial number exists
     *
     * @param serialNumber     The Serial Number to be checked on
     */
    private void throwExceptionIfDroneSerialExist(String serialNumber) {
        if(droneRepository.existsById(serialNumber)) {
            throw new DroneNotFoundException(Message.DRONE_EXISTS);
        }
    }

    /**
     * Throws Exception if given DroneRequestDTO object is null or doesn't include serial number
     *
     * @param droneRequestDTO   The DroneRequestDTO object to check
     */
    private void throwExceptionIfDroneRequestDTOIsNull(DroneRequestDTO droneRequestDTO) {
        if(droneRequestDTO == null || droneRequestDTO.getSerialNumber() == null) {
            throw new EmptyDataException(Message.EMPTY_DATA);
        }
    }

    /**
     * Throws Exception if given DroneRequestDTO object is null or doesn't include serial number
     *
     * @param droneRequestDTO   The DroneRequestDTO object to check
     */
    private void validateDroneRequestDTO(DroneRequestDTO droneRequestDTO) {
        throwExceptionIfDroneRequestDTOIsNull(droneRequestDTO);
        throwExceptionIfDroneSerialExist(droneRequestDTO.getSerialNumber());
    }
}
