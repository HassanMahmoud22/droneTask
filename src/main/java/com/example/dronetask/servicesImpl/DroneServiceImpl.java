package com.example.dronetask.servicesImpl;

import com.example.dronetask.constants.Constant;
import com.example.dronetask.dtos.*;
import com.example.dronetask.history.History;
import com.example.dronetask.mappers.DroneMapper;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.DroneModel;
import com.example.dronetask.models.DroneState;
import com.example.dronetask.repositories.DroneRepository;
import com.example.dronetask.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Service
@EnableScheduling
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final DroneMapper droneMapper;
    private History history;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper, History history) {
        this.droneMapper = droneMapper;
        this.droneRepository = droneRepository;
        this.history = history;
    }
    /*
        Param :
            registerDrone function takes a parameter of DroneRequestDTO type because it's not necessary to take all drone data from user
            some of them are being set by the application like droneModel as it's classified depending on weightLimit of the drone,
            droneState as it's classified depending on battery capacity, and weightLoaded as it's always zero since it's empty drone.
        Description :
            registerDrone function convert this dto to drone entity and sets its data and then save it to database
            and after that it converts it to droneResponseDTO and return it to user.
     */
    @Override
    public DroneResponseDTO registerDrone(DroneRequestDTO droneRequestDTO) {
        Drone drone = droneMapper.dronerequestDtoToDrone(droneRequestDTO);
        drone.setDroneModel(classifyDroneModel(drone.getWeightLimit()));
        drone.setState(classifyDroneState(drone.getBatteryCapacity()));
        drone.setWeightLoaded(0.0);
        Drone repoDrone = droneRepository.save(drone);
        return droneMapper.droneToDroneResponseDto(repoDrone);
    }
    /*
       Param :
           classifyDroneModel function takes a weightLimit as a parameter to classify the drone model depending on it.
       Description :
           classifyDroneModel function check
           if weightLimit is smaller than or equal specified maximum weight that lightweight Drones can hold
                then it's classified as lightWeight Drone
           if weightLimit is smaller than or equal specified middle weight that middleweight Drones can hold
                then it's classified as middle weight Drone
           if weightLimit is smaller than or equal specified cruiser weight that cruiserweight Drones can hold
                then it's classified as CruiserWeight Drone
           if nothing of this
                then it's classified as a Heavyweight Drone
    */
    private DroneModel classifyDroneModel(double weightLimit) {
        if (weightLimit <= Constant.LIGHT_WEIGHT_MAX)
            return DroneModel.Lightweight;
        if (weightLimit <= Constant.MIDDLE_WEIGHT_MAX)
            return DroneModel.Middleweight;
        if (weightLimit <= Constant.CRUISER_WEIGHT_MAX)
            return DroneModel.Cruiserweight;
        return DroneModel.Heavyweight;
    }

    /*
       Param :
           classifyDroneModel function takes a batteryCapacity as a parameter to classify the drone state depending on it.
       Description :
            classifyDroneModel function check
            if batteryCapacity is greater than the specified batter limit which is 25%
                then this drone is ready to be in loading state
            else it will be idle since it doesn't have enough charge to can load medications
    */
    private DroneState classifyDroneState(int batteryCapacity) {
        if(batteryCapacity > Constant.BATTERY_LIMIT)
            return DroneState.LOADING;
        return DroneState.IDLE;
    }
    /*
       listAvailableDronesForLoading is a function returns all Drones in loading state in type of AvailableDronesDTO
    */
    @Override
    public AvailableDronesDTO listAvailableDronesForLoading() {
        List<Drone> drones = droneRepository.findByState(DroneState.LOADING);
        return droneMapper.droneToAvailableDronesDTO(drones.size(), drones);
    }

    /*
       Param :
           getBatteryCapacity function takes a serialNumber of the Drone as a parameter to get its battery capacity.
       Description :
            classifyDroneModel function find drone by serial number and map it to batteryDTO
            to show the user the serial number of the drone and its battery capacity only
    */
    @Override
    public DroneBatteryDTO getBatteryCapacity(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return droneMapper.droneToBatteryDto(drone);
    }

    /*
       Description :
            deliverMedication is a scheduled function that simulate delivering the medications in loaded drones
            so, it get from database all drones are loaded "filled with medications" and then simulate delivering medications
    */
    @Scheduled(fixedRate = 3000)
    private void deliverMedication() {
        List<Drone> drones = droneRepository.findByState(DroneState.LOADED);
        for(Drone drone : drones) {
            simulateDroneActivity(drone, DroneState.DELIVERING);
            history.updateHistory(drone, "delivering medications");
        }
        droneRepository.saveAll(drones);
    }

    /*
           Description :
                emptyingDrone is a scheduled function that simulate emptying the medications in loaded drones
                so, it get from database all drones are in delivering state and then simulate Emptying medications
     */
    @Scheduled(fixedRate = 6000)
    private void emptyingDrone() {
        List<Drone> drones = droneRepository.findByState(DroneState.DELIVERING);
        for(Drone drone : drones) {
            simulateDroneActivity(drone, DroneState.DELIVERED);
            drone.setWeightLoaded(0.0);
            history.updateHistory(drone, "Emptying Medications");
        }
        droneRepository.saveAll(drones);
    }

    /*
           Description :
                returningDrone is a scheduled function that simulate returning the Drone to base again after emptying the medications in destination
                so, it get from database all drones are in Delivered state and then simulate Returning the Drone
     */
    @Scheduled(fixedRate = 8000)
    private void returningDrone() {
        List<Drone> drones = droneRepository.findByState(DroneState.DELIVERED);
        for(Drone drone : drones) {
            simulateDroneActivity(drone, DroneState.RETURNING);
            history.updateHistory(drone, "returning to base");
        }
        droneRepository.saveAll(drones);
    }

    /*
       Description :
            landingDrone is a scheduled function that simulate landing the Drone to base after arriving to it
            and check if the battery capacity is less than or equal battery limit which is 25%
                then it will be in idle state
            else
                it will be in loading state in which it can be loaded again
    */
    @Scheduled(fixedRate = 10000)
    private void landingDrone() {
        List<Drone> drones = droneRepository.findByState(DroneState.RETURNING);
        for(Drone drone : drones) {
            if(drone.getBatteryCapacity() <= Constant.BATTERY_LIMIT)
                simulateDroneActivity(drone, DroneState.IDLE);
            else
                simulateDroneActivity(drone, DroneState.LOADING);
            history.updateHistory(drone, "Landing");
        }
        droneRepository.saveAll(drones);
    }

    /*
       Param :
            Drone which will be simulated, DroneState which is the next state of the drone after simulation
       Description :
            simulateDroneActivity simulate the drone activity by changing its current state to next state
            and decrease its battery capacity to simulate it as it's consumed
    */
    private Drone simulateDroneActivity(Drone drone, DroneState droneState) {
        drone.setState(droneState);
        drone.setBatteryCapacity(drone.getBatteryCapacity() - generateRandom());
        return drone;
    }
    private int generateRandom() {
        int min = 1;
        int max = 5;
        return (int)Math.floor(Math.random() * (max - min + 1) + min);
    }
}
