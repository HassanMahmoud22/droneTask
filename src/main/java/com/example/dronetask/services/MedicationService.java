package com.example.dronetask.services;

import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.Medication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicationService {
    /**
     * Stores Medications to Database by converting MedicationDTO to Medication Entity
     *
     * @param medications list of MedicationsDTOs to store them in Database
     */
    public void registerMedications(List<Medication> medications);

    /**
     * lists all medications which were loaded by drone id
     *
     * @param drone The Drone which its medications will be listed
     * @return List of all Medications were loaded by given Drone
     */
    public List<Medication> listMedications(Drone drone);

    /**
     * maps MedicationsDTOs list to Medications list
     *
     * @param medicationsDTOS The MedicationDTO list to be mapped
     * @return Medications list
     */
    public List<Medication> mapMedicationDTOsToMedications(List<MedicationDTO> medicationsDTOS);

    /**
     * lists MedicationsDTOs by mapping Medications
     *
     * @param drone The Drone which its medications will be listed
     * @return List of MedicationsDTO
     */
    public List<MedicationDTO> listMedicationsDTO(Drone drone);

    /**
     * calculates the total weight of given medications list
     *
     * @param medications the list of Medications which its weights are calculated
     * @return the total weight of the Medications list
     */
    public double calculateTotalWeightOfMedications(List<Medication> medications);

}
