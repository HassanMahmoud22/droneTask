package com.example.dronetask.services;

import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.Medication;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MedicationService {
    public void createMedication(MedicationDTO medDto);
    public List<Medication> createMedication(List<MedicationDTO> medDtos);
    public List<Medication> listMedications();
    public List<Medication> listMedications(Drone drone);
    public List<MedicationDTO> listMedications(List<Medication> medications);
}
