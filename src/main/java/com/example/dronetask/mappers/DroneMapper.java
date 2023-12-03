package com.example.dronetask.mappers;
import com.example.dronetask.dtos.AvailableDronesDTO;
import com.example.dronetask.dtos.DroneBatteryDTO;
import com.example.dronetask.dtos.DroneDTO;
import com.example.dronetask.models.Drone;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel="spring")
public interface DroneMapper {
    DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);


    @Mapping(target="weightLoaded",source="drone.currentWeight")
    DroneDTO modelToDto(Drone drone);

    List<DroneDTO> dronesToDto(List<Drone> drones);

    @InheritInverseConfiguration
    Drone dtoToDrone(DroneDTO dto);

    @Mapping(target="serialNumber",source="drone.serialNumber")
    @Mapping(target="batteryCapacity",source="drone.batteryCapacity")
    DroneBatteryDTO droneToBatteryDto(Drone drone);

    @Mapping(target="drones",source="availableDrones")
    AvailableDronesDTO droneToAvailableDronesDTO(int total, List<Drone> availableDrones);
}
