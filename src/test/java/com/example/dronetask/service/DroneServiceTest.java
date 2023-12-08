package com.example.dronetask.service;

import com.example.dronetask.constant.Message;
import com.example.dronetask.dto.DroneBatteryDTO;
import com.example.dronetask.dto.DroneRequestDTO;
import com.example.dronetask.dto.DroneResponseDTO;
import com.example.dronetask.model.DroneModel;
import com.example.dronetask.model.DroneState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest

public class DroneServiceTest {
    @Autowired
    DroneService droneService;

    @Test
    public void testRegisterDrone() {
        DroneRequestDTO droneRequestDTO = new DroneRequestDTO("12345", 500.0, 100);
        DroneResponseDTO expectedDroneResponseDTO = new DroneResponseDTO("12345", DroneModel.Heavyweight, 500.0, 100, DroneState.LOADING, 0.0);

        DroneResponseDTO droneResponseDTO = droneService.registerDrone(droneRequestDTO);
        assertEquals(expectedDroneResponseDTO, droneResponseDTO);

    }

    @Test
    public void testRegisterDroneByNull() {
        try {
            DroneResponseDTO droneResponseDTO = droneService.registerDrone(null);
        } catch (Exception e) {
            assertEquals(Message.EMPTY_DATA, e.getMessage());
        }
    }

    @Test
    public void testRegisterDroneByEmpty() {
        DroneRequestDTO droneRequestDTO1 = new DroneRequestDTO();
        try {
            DroneResponseDTO droneResponseDTO = droneService.registerDrone(droneRequestDTO1);
        } catch (Exception e) {
            assertEquals(Message.EMPTY_DATA, e.getMessage());
        }
    }
    @Test
    public void testListAvailableDronesForLoading() {
        List<DroneResponseDTO> droneResponseDTOList = new ArrayList<>();
        DroneResponseDTO droneResponseDTO = new DroneResponseDTO("1111111", DroneModel.Heavyweight, 500.0, 100, DroneState.LOADING, 50.0);
        DroneResponseDTO droneResponseDTO2 = new DroneResponseDTO("1111112", DroneModel.Lightweight, 100.0, 80, DroneState.LOADING, 0.0);
        DroneResponseDTO droneResponseDTO3 = new DroneResponseDTO("1111113", DroneModel.Middleweight, 200.0, 100, DroneState.LOADING, 0.0);
        droneResponseDTOList.add(droneResponseDTO);
        droneResponseDTOList.add(droneResponseDTO2);
        droneResponseDTOList.add(droneResponseDTO3);
        List<DroneResponseDTO> droneResponseDTOS = droneService.listAvailableDronesForLoading();
        assertEquals(droneResponseDTOList, droneResponseDTOS);
    }

    @Test
    void testGetBatteryCapacity() {
        String serialNumber = "1111111";
        DroneBatteryDTO expectedDroneBatteryDTO = new DroneBatteryDTO(serialNumber, 100);
        DroneBatteryDTO droneBatteryDTO = droneService.getBatteryCapacity(serialNumber);
        assertEquals(expectedDroneBatteryDTO, droneBatteryDTO);
    }
    @Test
    void testGetBatteryCapacityByNInvalidId() {
        String serialNumber = "";
        try {
            DroneBatteryDTO droneBatteryDTO = droneService.getBatteryCapacity(serialNumber);
        } catch (Exception e) {
            assertEquals(Message.DRONE_DOESNT_EXIST, e.getMessage());
        }
    }

    @Test
    void testGetBatteryCapacityByNull() {
        try {
            DroneBatteryDTO droneBatteryDTO = droneService.getBatteryCapacity(null);
        } catch (Exception e) {
            assertEquals("The given id must not be null", e.getMessage());
        }
    }
}
