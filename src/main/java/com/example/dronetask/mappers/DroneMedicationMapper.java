package com.example.dronetask.mappers;

import com.example.dronetask.dtos.DroneMedicationDTO;
import com.example.dronetask.dtos.LoadDroneDTO;
import com.example.dronetask.models.Drone;
import com.example.dronetask.models.DroneMedication;
import com.example.dronetask.models.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Stream;
@Mapper(componentModel="spring")
public interface DroneMedicationMapper {
    DroneMedicationMapper INSTANCE = Mappers.getMapper(DroneMedicationMapper.class);

    @Mapping(target="serialNumber", source="activity.droneId")
    @Mapping(target="droneModel",source="drone.droneModel")
    @Mapping(target="droneState",source="drone.droneState")
    @Mapping(target="totalWeight",source="activity.weightLoaded")
    @Mapping(target="deliveryAddress",source="activity.address")
    LoadDroneDTO DroneMedicationToDTO(DroneMedication droneMedication, Drone drone, Stream<Medication> meds);

    @Mapping(target="droneId",source="activity.serialNumber")
    @Mapping(target="weightLoaded",source="activity.totalWeight")
    @Mapping(target="address",source="activity.deliveryAddress")
    DroneMedication dtoToADroneMedication(LoadDroneDTO activity,Drone drone);

    @Mapping(target="total",source="total")
    @Mapping(target="activities",source="droneActivities")
    DroneMedicationDTO mapDroneMedicationDTO(int total, List<LoadDroneDTO> droneActivities);
}
