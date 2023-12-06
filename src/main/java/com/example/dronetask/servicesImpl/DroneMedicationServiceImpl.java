package com.example.dronetask.servicesImpl;

import com.example.dronetask.dtos.LoadDroneDTO;
import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.Medication;
import com.example.dronetask.services.DroneMedicationService;
import com.example.dronetask.services.DroneService;
import com.example.dronetask.services.MedicationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneMedicationServiceImpl implements DroneMedicationService {

    private final MedicationService medicationService;
    private final DroneService droneService;

    @Autowired
    public DroneMedicationServiceImpl(DroneService droneService , MedicationService medicationService) {
        this.droneService = droneService;
        this.medicationService = medicationService;
    }

    /**
     * loads Medications to Drone
     *
     * @param loadDroneDTO This DTO is serial number of Drone and List of Medications to be loaded
     * @return ResponseEntity with http status and Message or Object in body
     */
    @Transactional
    @Override
    public ResponseEntity<?> loadDrone(LoadDroneDTO loadDroneDTO) {
        //retreiving drone from Database with given serial number
        Drone drone = droneService.getDroneBySerialNumber(loadDroneDTO.getSerialNumber());
        //if drone isn't suitable for loading
        if (!droneService.isDroneSuitable(drone)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no Drone with this serial number in loading state");
        }
        List<Medication> medications = medicationService.mapMedicationDTOsToMedications(loadDroneDTO.getMedications());
        double totalMedicationsWeight = medicationService.calculateTotalWeightOfMedications(medications);
        //if Drone doesn't have enough space to load medications
        if (!droneService.isDroneHaveSpace(drone , totalMedicationsWeight)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The Medication Weight exceeds the available weight in this drone");
        } else {
            //load the medications
            return loadMedicationsToDrone(medications , drone , totalMedicationsWeight);
        }
    }

    /**
     * updates medications droneId to given Drone id and updates drone with given medications list
     *
     * @param medications            The Medications list
     * @param drone                  The Drone to be loaded
     * @param totalMedicationsWeight the total weight of the Medications
     * @return
     */
    private ResponseEntity<?> loadMedicationsToDrone(List<Medication> medications , Drone drone , double totalMedicationsWeight) {
        medicationService.registerMedications(medications);
        double droneNewWeight = drone.getWeightLoaded() + totalMedicationsWeight;
        drone.setWeightLoaded(droneNewWeight);
        droneService.updateDroneStateIfFilled(drone);
        setDroneToMedications(medications , drone);
        drone.setMedications(medications);
        droneService.updateDrone(drone);
        return ResponseEntity.status(HttpStatus.OK).body("The Medications are loaded to the drone Successfully!!");
    }

    /**
     * assign the given Drone to each Medication in the list
     *
     * @param medications The Medications list
     * @param drone       The drone to be assigned
     */
    private void setDroneToMedications(List<Medication> medications , Drone drone) {
        medications.forEach((medication) -> medication.setDroneId(drone));
    }

    /**
     * Gets the Medications List which were loaded by given Drone
     *
     * @param serialnumber The Serial Number of the Drone
     * @return loadDroneDTO which have The List of Medications which loaded and the serial number of the Drone
     */
    @Override
    public ResponseEntity<?> getDroneMedications(String serialnumber) {
        Drone drone = droneService.getDroneBySerialNumber(serialnumber);
        //if drone exists
        if (drone != null) {
            //sets loadDroneDTO values by given serial number and medicationDTOS list
            LoadDroneDTO loadDroneDTO = new LoadDroneDTO();
            loadDroneDTO.setSerialNumber(serialnumber);
            List<MedicationDTO> medicationDTOS = medicationService.listMedicationsDTO(drone);
            loadDroneDTO.setMedications(medicationDTOS);
            return ResponseEntity.status(HttpStatus.OK).body(loadDroneDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Drone with this serial number not found");
        }
    }
}
