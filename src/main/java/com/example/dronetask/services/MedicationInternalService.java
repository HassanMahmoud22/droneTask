package com.example.dronetask.services;

import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.models.Medication;

import java.util.List;

public interface MedicationInternalService {
    /**
     * maps MedicationsDTOs list to Medications list
     *
     * @param medicationsDTOS The MedicationDTO list to be mapped
     * @return Medications list
     */
    public List<Medication> mapMedicationDTOsToMedications(List<MedicationDTO> medicationsDTOS);

    /**
     * calculates the total weight of given medications list
     *
     * @param medications the list of Medications which its weights are calculated
     * @return the total weight of the Medications list
     */
    public double calculateTotalWeightOfMedications(List<Medication> medications);
}
