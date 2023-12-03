package com.example.dronetask.mappers;

import com.example.dronetask.dtos.MedicationDTO;
import com.example.dronetask.models.Medication;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface MedicationMapper {

    MedicationMapper INSTANCE = Mappers.getMapper(MedicationMapper.class);

    MedicationDTO medicationToDTOo(Medication medication);

    @InheritInverseConfiguration
    Medication dtoToMedication(MedicationDTO dto);

    @InheritInverseConfiguration
    List<Medication> dtosToMedications(List<MedicationDTO> dtos);

}