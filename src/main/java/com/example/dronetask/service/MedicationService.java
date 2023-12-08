package com.example.dronetask.service;

import com.example.dronetask.dto.MedicationDTO;
import com.example.dronetask.model.Drone;
import com.example.dronetask.model.Medication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicationService {
    /**
     * Stores Medications to Database by converting MedicationDTO to Medication Entity
     *
     * @param medications list of MedicationsDTOs to store them in Database
     */
    public List<Medication> registerMedications(List<Medication> medications);



    /**
     * lists MedicationsDTOs by mapping Medications
     *
     * @param drone The Drone which its medications will be listed
     * @return List of MedicationsDTO
     */
    public List<MedicationDTO> listMedications(Drone drone);



}
