package com.example.dronetask.mappers;
import com.example.dronetask.dtos.*;
import com.example.dronetask.models.Drone;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DroneMapper {
    DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);

    DroneResponseDTO droneToDroneResponseDto(Drone drone);


    List<DroneDTO> dronesToDto(List<Drone> drones);

    @InheritInverseConfiguration
    Drone droneDtoToDrone(DroneDTO dto);

    @InheritInverseConfiguration
    Drone dronerequestDtoToDrone(DroneRequestDTO dto);

    DroneBatteryDTO droneToBatteryDto(Drone drone);

    @Mapping(target="drones",source="availableDrones")
    AvailableDronesDTO droneToAvailableDronesDTO(int total, List<Drone> availableDrones);
}
