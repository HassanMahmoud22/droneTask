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
     * lists MedicationsDTOs by mapping Medications
     *
     * @param drone The Drone which its medications will be listed
     * @return List of MedicationsDTO
     */
    public List<MedicationDTO> listMedicationsDTO(Drone drone);



}
