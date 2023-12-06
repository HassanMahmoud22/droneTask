package com.example.dronetask.mappers;

import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.models.Medication;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel="spring")
public interface MedicationMapper {
    MedicationDTO medicationToDTO(Medication medication);
    @InheritInverseConfiguration
    Medication dtoToMedication(MedicationDTO dto);
    @InheritInverseConfiguration
    List<Medication> medicationsDtosToMedications(List<MedicationDTO> dtos);
    List<MedicationDTO> medicationsToDTOs(List<Medication> dtos);

}