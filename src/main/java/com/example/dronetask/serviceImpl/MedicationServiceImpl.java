package com.example.dronetask.serviceImpl;

import com.example.dronetask.constant.Message;
import com.example.dronetask.dto.MedicationDTO;
import com.example.dronetask.exceptionHandler.DroneNotFoundException;
import com.example.dronetask.exceptionHandler.MedicationExistsException;
import com.example.dronetask.mapper.MedicationMapper;
import com.example.dronetask.model.Drone;
import com.example.dronetask.model.Medication;
import com.example.dronetask.repository.MedicationRepository;
import com.example.dronetask.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

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
     * @return            list of medications after saving in database
     */
    @Override
    public List<Medication> registerMedications(List<Medication> medications) {

        return medicationRepository.saveAll(medications);
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
    public List<MedicationDTO> listMedications(Drone drone) {
        List<Medication> medications = medicationRepository.findByDroneId(drone);
        if(medications == null || medications.size() == 0) {
            throw new DroneNotFoundException(Message.DRONE_DOESNT_EXIST + " loaded by medications");
        }
        else {
            return medicationMapper.medicationsToDTOs(medications);
        }
    }

    /**
     * calculates the total weight of given medications list
     *
     * @param medications the list of Medications which its weights are calculated
     * @return            the total weight of the Medications list
     */
    public double calculateTotalWeightOfMedications (List<Medication> medications) {
        double totalWeights = 0;
        if(medications != null && medications.size() > 0) {
            for(Medication medication : medications) {
                totalWeights += medication.getWeight();
            }
        }
        return totalWeights;
    }

    /**
     * Throws Exception if drone is null
     *
     * @param medicationDTOList     The medications dto list to be checked on
     */
    public void throwExceptionIfMedicationExist(List<MedicationDTO> medicationDTOList) {
        for(MedicationDTO medicationDTO : medicationDTOList) {
            if(medicationRepository.existsById(medicationDTO.getCode())) {
                throw new MedicationExistsException("Medication code already exists " + medicationDTO.getCode());
            }
        }
    }
}
