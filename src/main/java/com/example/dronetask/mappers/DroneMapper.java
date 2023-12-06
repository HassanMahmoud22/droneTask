package com.example.dronetask.mappers;
import com.example.dronetask.dtos.*;
import com.example.dronetask.models.Drone;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DroneMapper {
    DroneResponseDTO droneToDroneResponseDto(Drone drone);
    @InheritInverseConfiguration
    Drone dronerequestDtoToDrone(DroneRequestDTO dto);
    DroneBatteryDTO droneToBatteryDto(Drone drone);
    @Mapping(target="drones",source="availableDrones")
    AvailableDronesDTO droneToAvailableDronesDTO(int total, List<Drone> availableDrones);
}
