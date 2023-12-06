package com.example.dronetask.servicesImpl;

import com.example.dronetask.dtos.LoadDroneDTO;
import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.DroneState;
import com.example.dronetask.models.Medication;
import com.example.dronetask.repositories.DroneRepository;
import com.example.dronetask.services.DroneMedicationService;
import com.example.dronetask.services.MedicationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DroneMedicationServiceImpl implements DroneMedicationService {

    @Autowired
    private DroneRepository droneRepository;

    private MedicationService medicationService;

    @Autowired
    public DroneMedicationServiceImpl(MedicationService medicationService) {
        this.medicationService = medicationService;
    }


    @Transactional
    @Override
    public ResponseEntity<?> loadDrone(LoadDroneDTO loadDroneDTO) {
        Drone drone = droneRepository.findBySerialNumber(loadDroneDTO.getSerialNumber());
        if(drone == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Drone with this serial number not found!!");
        if(drone.getState() != DroneState.LOADING)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("This Drone is in " + drone.getState() + " state which can't be loaded");
        List<Medication> meds = medicationService.createMedication(loadDroneDTO.getMedications());
        double totalMedicationWeights = 0;
        for(Medication medication : meds) {
            totalMedicationWeights += medication.getWeight();
        }
        double availableWeight = drone.getWeightLimit() - drone.getWeightLoaded();
        if( availableWeight < totalMedicationWeights)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The Medication Weight exceeds the available weight in this drone");
        double droneNewWeight = drone.getWeightLoaded() + totalMedicationWeights;
        drone.setWeightLoaded(droneNewWeight);
        if(drone.getWeightLimit() == droneNewWeight)
            drone.setState(DroneState.LOADED);
        for(Medication medication : meds) {
            totalMedicationWeights += medication.getWeight();
            medication.setDroneId(drone);
        }
        drone.setMedications(meds);
        droneRepository.save(drone);
        return ResponseEntity.status(HttpStatus.OK).body("The Medications are loaded to the drone Successfully!!");
    }

   @Override
    public LoadDroneDTO getDroneMedications(String serialnumber) {
        Drone drone = droneRepository.findBySerialNumber(serialnumber);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO();
        loadDroneDTO.setSerialNumber(serialnumber);
        if(drone != null) {
            List<Medication> medications = medicationService.listMedications(drone);
            List<MedicationDTO> medicationDTOS =medicationService.listMedications(medications);
            loadDroneDTO.setMedications(medicationDTOS);
        }
        return loadDroneDTO;
    }
}
