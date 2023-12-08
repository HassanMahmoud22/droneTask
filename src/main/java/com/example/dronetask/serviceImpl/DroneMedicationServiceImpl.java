package com.example.dronetask.serviceImpl;

import com.example.dronetask.constant.Message;
import com.example.dronetask.dto.LoadDroneDTO;
import com.example.dronetask.dto.MedicationDTO;
import com.example.dronetask.exceptionHandler.WeightLimitExceeded;
import com.example.dronetask.model.Drone;
import com.example.dronetask.model.DroneState;
import com.example.dronetask.model.Medication;
import com.example.dronetask.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DroneMedicationServiceImpl implements DroneMedicationService {

    private final MedicationService medicationService;
    private final DroneInternalService droneInternalService;

    @Autowired
    public DroneMedicationServiceImpl(DroneInternalService droneInternalService, MedicationService medicationService) {
        this.droneInternalService = droneInternalService;
        this.medicationService = medicationService;
    }

    /**
     * loads Medications to Drone
     *
     * @param loadDroneDTO This DTO is serial number of Drone and List of Medications to be loaded
     */
    @Transactional
    @Override
    public void loadDrone(LoadDroneDTO loadDroneDTO) {
        //retreiving drone from Database with given serial number and in loading state
        Drone drone = droneInternalService.getDroneBySerialNumberAndState(loadDroneDTO.getSerialNumber(), DroneState.LOADING);
        //if any of these medications exist in database, throw exception
        medicationService.throwExceptionIfMedicationExist(loadDroneDTO.getMedications());
        List<Medication> medications = medicationService.mapMedicationDTOsToMedications(loadDroneDTO.getMedications());
        double totalMedicationsWeight = medicationService.calculateTotalWeightOfMedications(medications);
        //if Drone doesn't have enough space to load medications
        if (!droneInternalService.isDroneHasSpace(drone, totalMedicationsWeight)) {
            throw new WeightLimitExceeded(Message.WEIGHT_LIMIT_EXCEEDED);
        } else {
            //load the medications
            loadMedicationsToDrone(medications, drone, totalMedicationsWeight);
        }
    }

    /**
     * updates medications droneId to given Drone id and updates drone with given medications list
     *
     * @param medications            The Medications list
     * @param drone                  The Drone to be loaded
     * @param totalMedicationsWeight the total weight of the Medications
     */
    public void loadMedicationsToDrone(List<Medication> medications, Drone drone, double totalMedicationsWeight) {
        medicationService.registerMedications(medications);
        double droneNewWeight = drone.getWeightLoaded() + totalMedicationsWeight;
        drone.setWeightLoaded(droneNewWeight);
        droneInternalService.updateDroneStateIfFilled(drone);
        setDroneToMedications(medications, drone);
        drone.setMedications(medications);
        droneInternalService.updateDrone(drone);
    }

    /**
     * assign the given Drone to each Medication in the list
     *
     * @param medications The Medications list
     * @param drone       The drone to be assigned
     */
    private void setDroneToMedications(List<Medication> medications, Drone drone) {
        medications.forEach((medication) -> medication.setDroneId(drone));
    }

    /**
     * Gets the Medications List which were loaded by given Drone
     *
     * @param serialnumber The Serial Number of the Drone
     * @return loadDroneDTO which have The List of Medications which loaded and the serial number of the Drone
     */
    @Override
    public LoadDroneDTO getDroneMedications(String serialnumber) {
        Drone drone = droneInternalService.getDroneBySerialNumber(serialnumber);
        //sets loadDroneDTO values by given serial number and medicationDTOS list
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO();
        loadDroneDTO.setSerialNumber(serialnumber);
        List<MedicationDTO> medications = medicationService.listMedications(drone);
        loadDroneDTO.setMedications(medications);
        return loadDroneDTO;
    }
}
