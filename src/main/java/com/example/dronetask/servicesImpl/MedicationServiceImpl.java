package com.example.dronetask.servicesImpl;
import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.mappers.MedicationMapper;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.Medication;
import com.example.dronetask.repositories.MedicationRepository;
import com.example.dronetask.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MedicationServiceImpl implements MedicationService {
    private MedicationRepository medicationRepository;
    private MedicationMapper medicationMapper;
    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationMapper = medicationMapper;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public void createMedication(MedicationDTO medDto) {
        Medication med = medicationMapper.dtoToMedication(medDto);
        medicationRepository.save(med);
    }
    @Override
    public List<Medication> createMedication(List<MedicationDTO> medDtos) {

        List<Medication> meds = medicationMapper.dtosToMedications(medDtos);
        List<Medication> medicationList = new ArrayList<>();
        //medRepo.saveAll(meds).iterator().forEachRemaining(actualList::add);
        for(Medication medication : meds) {
            medicationList.add(medicationRepository.save(medication));
        }
        return medicationList;
    }

    @Override
    public List<Medication> listMedications() {
        return medicationRepository.findAll();
    }

   @Override
    public List<Medication> listMedications(Drone drone) {
        return medicationRepository.findByDroneId(drone);
    }
    public List<MedicationDTO> listMedications(List<Medication> medications) {
        return medicationMapper.medicationsToDTOs(medications);
    }
}
