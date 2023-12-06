package com.example.dronetask.servicesImpl;

import com.example.dronetask.constants.Constant;
import com.example.dronetask.constants.Message;
import com.example.dronetask.dtos.*;
import com.example.dronetask.exceptionHandler.DroneNotFoundException;
import com.example.dronetask.history.History;
import com.example.dronetask.mappers.DroneMapper;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.DroneModel;
import com.example.dronetask.models.DroneState;
import com.example.dronetask.repositories.DroneRepository;
import com.example.dronetask.services.DroneInternalService;
import com.example.dronetask.services.DroneService;
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
        Drone drone = droneMapper.dronerequestDtoToDrone(droneRequestDTO);
        drone.setDroneModel(classifyDroneModel(drone.getWeightLimit()));
        drone.setState(classifyDroneState(drone.getBatteryCapacity()));
        drone.setWeightLoaded(0.0);
        //save this drone to database and return droneResponseDTO
        Drone repositoryDrone = droneRepository.save(drone);
        return droneMapper.droneToDroneResponseDto(repositoryDrone);
    }

    /**
     * Classifies Drone Model depending on weight limit of the Drone
     *
     * @param weightLimit the maximum weight drone can load to classify drone model depending on it
     * @return The DroneModel after being classified
     */
    private DroneModel classifyDroneModel(double weightLimit) {
        // check if weightLimit is smaller than or equal specified maximum weight that lightweight Drones can hold
        if (weightLimit <= Constant.LIGHT_WEIGHT_MAX) {
            return DroneModel.Lightweight;
            // check if weightLimit is smaller than or equal specified middle weight that middleweight Drones can hold
        } else if (weightLimit <= Constant.MIDDLE_WEIGHT_MAX) {
            return DroneModel.Middleweight;
            // check if weightLimit is smaller than or equal specified cruiser weight that cruiserweight Drones can hold
        } else if (weightLimit <= Constant.CRUISER_WEIGHT_MAX) {
            return DroneModel.Cruiserweight;
        } else {
            return DroneModel.Heavyweight;
        }
    }

    /**
     * Classifies the Drone state depending on Battery Capacity
     *
     * @param batteryCapacity the battery capacity of drone to classify drone state based on it
     * @return the state of the drone after classification
     */
    private DroneState classifyDroneState(int batteryCapacity) {
        //if battery capacity > specified battery limit (25%)
        if (batteryCapacity > Constant.BATTERY_LIMIT) {
            return DroneState.LOADING;
        } else {
            return DroneState.IDLE;
        }
    }

    /**
     * lists all available drones for loading (Their state is Loading)
     *
     * @return all drones in loading state
     */
    @Override
    public AvailableDronesDTO listAvailableDronesForLoading() {
        List<Drone> drones = droneRepository.findByState(DroneState.LOADING);
        return droneMapper.droneToAvailableDronesDTO(drones.size(), drones);
    }

    /**
     * Gets the Battery Capacity of given serial number of Drone
     *
     * @param serialNumber the serial number of the drone
     * @return the serial number and battery capacity of the drone
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
            simulateDroneActivity(drone, DroneState.DELIVERING);
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
            simulateDroneActivity(drone, DroneState.DELIVERED);
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
            simulateDroneActivity(drone, DroneState.RETURNING);
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
            if (drone.getBatteryCapacity() <= Constant.BATTERY_LIMIT)
                simulateDroneActivity(drone, DroneState.IDLE);
            else
                simulateDroneActivity(drone, DroneState.LOADING);
            history.updateHistory(drone, Message.LANDING);
        }
        droneRepository.saveAll(drones);
    }

    /**
     * Simulates Drone Activity by decreasing current Battery Capacity
     * and sets its state to next state
     *
     * @param drone      The drone which its activities will be simulated
     * @param droneState The next state of the drone after simulation
     */
    private void simulateDroneActivity(Drone drone, DroneState droneState) {
        drone.setState(droneState);
        drone.setBatteryCapacity(drone.getBatteryCapacity() - generateRandom());
    }

    /**
     * This is a helping function for simulation
     * which generate random number to be decreased in battery capacity
     *
     * @return random number from 1 to 5
     */
    private int generateRandom() {
        int min = 1;
        int max = 5;
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    /**
     * Gets Drone by given serial number
     *
     * @param serialNumber The serial number of the Drone
     * @return The Drone needed if exists otherwise null
     */
    public Drone getDroneBySerialNumber(String serialNumber) {
        return droneRepository.findById(serialNumber).orElseThrow(() -> new DroneNotFoundException(Message.DRONE_DOESNT_EXIST + " " + serialNumber));
    }

    @Override
    public Drone getDroneBySerialNumberAndState(String serialNumber, DroneState state) {
        Drone drone = droneRepository.findBySerialNumberAndState(serialNumber, state);
        if(drone == null) {
            throw new DroneNotFoundException(Message.DRONE_DOESNT_EXIST + "in " + state + "state");
        } else
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
    public boolean isDroneHaveSpace(Drone drone, double totalMedicationsWeights) {
        double availableWeight = drone.getWeightLimit() - drone.getWeightLoaded();
        return !(availableWeight < totalMedicationsWeights);
    }
}
