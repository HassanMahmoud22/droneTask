package com.example.dronetask.mapper;
import com.example.dronetask.dto.*;
import com.example.dronetask.model.Drone;
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
    List<DroneResponseDTO> dronesToDroneResponseDtos(List<Drone> drones);
}
