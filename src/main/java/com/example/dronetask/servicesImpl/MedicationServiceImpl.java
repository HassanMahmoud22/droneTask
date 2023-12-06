package com.example.dronetask.servicesImpl;

import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.mappers.MedicationMapper;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.Medication;
import com.example.dronetask.repositories.MedicationRepository;
import com.example.dronetask.services.MedicationInternalService;
import com.example.dronetask.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService, MedicationInternalService {
    private final MedicationRepository medicationRepository;
    private final MedicationMapper medicationMapper;

    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository , MedicationMapper medicationMapper) {
        this.medicationMapper = medicationMapper;
        this.medicationRepository = medicationRepository;
    }

    /**
     * Stores Medications to Database by converting MedicationDTO to Medication Entity
     *
     * @param medications list of MedicationsDTOs to store them in Database
     */
    @Override
    public void registerMedications(List<Medication> medications) {
        medicationRepository.saveAll(medications);
    }

    /**
     * maps MedicationsDTOs list to Medications list
     *
     * @param medicationsDTOS The MedicationDTO list to be mapped
     * @return                Medications list
     */
    public List<Medication> mapMedicationDTOsToMedications(List<MedicationDTO> medicationsDTOS) {
        return medicationMapper.medicationsDtosToMedications(medicationsDTOS);
    }

    /**
     * lists MedicationsDTOs by mapping Medications
     *
     * @param drone The Drone which its medications will be listed
     * @return List of MedicationsDTO
     */
    @Override
    public List<MedicationDTO> listMedicationsDTO(Drone drone) {
        List<Medication> medications = medicationRepository.findByDroneId(drone);
        return medicationMapper.medicationsToDTOs(medications);
    }

    /**
     * calculates the total weight of given medications list
     *
     * @param medications the list of Medications which its weights are calculated
     * @return            the total weight of the Medications list
     */
    public double calculateTotalWeightOfMedications (List<Medication> medications) {
        double totalWeights = 0;
        for(Medication medication : medications) {
            totalWeights += medication.getWeight();
        }
        return totalWeights;
    }
}
