package com.example.dronetask.service;

import com.example.dronetask.constant.Message;
import com.example.dronetask.model.Drone;
import com.example.dronetask.model.DroneModel;
import com.example.dronetask.model.DroneState;
import com.example.dronetask.model.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DroneInternalServiceTest {
    @Autowired
    DroneInternalService droneInternalService;
    @Test
    void testGetDroneBySerialNumber() {
        String serialNumber = "1111112";
        List<Medication> medicationList = new ArrayList<>();
        Drone expectedDrone = new Drone("1111112", DroneModel.Lightweight, 100.0, 80, DroneState.LOADING, 0.0, medicationList);
        Drone drone = droneInternalService.getDroneBySerialNumber(serialNumber);
        assertEquals(expectedDrone, drone);
    }

    @Test
    void testGetDroneBySerialNumberByNull() {
        try {
            Drone drone = droneInternalService.getDroneBySerialNumber(null);
        } catch (Exception e) {
            assertEquals("The given id must not be null", e.getMessage());
        }
    }

    @Test
    void testGetDroneBySerialNumberByInvalidSerial() {
        try {
            Drone drone = droneInternalService.getDroneBySerialNumber("");
        } catch (Exception e) {
            assertEquals(Message.DRONE_DOESNT_EXIST, e.getMessage());
        }
    }

    @Test
    void testGetDroneBySerialNumberAndState() {

        Drone expectedDrone = new Drone("1111114", DroneModel.Cruiserweight, 350.0, 20, DroneState.IDLE, 0.0, new ArrayList<Medication>());
        Drone drone = droneInternalService.getDroneBySerialNumberAndState("1111114", DroneState.IDLE);
        assertEquals(expectedDrone, drone);
    }

    @Test
    void testGetDroneBySerialNumberAndStateByNull() {
        DroneState droneState = null;
        try {
            Drone drone = droneInternalService.getDroneBySerialNumberAndState(null, droneState);
        } catch (Exception e) {
            assertEquals(Message.DRONE_DOESNT_EXIST + " in " + droneState + " state", e.getMessage());
        }
    }

    @Test
    void testGetDroneBySerialNumberAndStateByEmpty() {
        DroneState droneState = DroneState.IDLE;
        try {
            Drone drone = droneInternalService.getDroneBySerialNumberAndState("", droneState);
        } catch (Exception e) {
            assertEquals(Message.DRONE_DOESNT_EXIST + " in " + droneState + " state", e.getMessage());
        }
    }

    @Test
    void testUpdateDrone() {
        String oldSerialNumber = "1111114";
        String newSerialNumber = "12345";
        Drone drone = droneInternalService.getDroneBySerialNumber(oldSerialNumber);
        drone.setSerialNumber(newSerialNumber);
        droneInternalService.updateDrone(drone);
        Drone updatedDrone = droneInternalService.getDroneBySerialNumber(newSerialNumber);
        assertEquals(drone.toString(), updatedDrone.toString());
    }

    @Test
    void testUpdateDroneByNull() {
        try {
            droneInternalService.updateDrone(null);
        } catch (Exception e) {
            assertEquals("Entity must not be null", e.getMessage());
        }
    }

    @Test
    void testUpdateDroneStateIfFilled() {
        Drone drone = new Drone("1111111", DroneModel.Heavyweight, 500.0, 100, DroneState.LOADING, 500.0);
        droneInternalService.updateDroneStateIfFilled(drone);
        Drone expectedDrone = new Drone("1111111", DroneModel.Heavyweight, 500.0, 100, DroneState.LOADED, 500.0);
        assertEquals(expectedDrone, drone);
    }

    @Test
    void testUpdateDroneStateIfFilledByNull() {
       try {
           droneInternalService.updateDroneStateIfFilled(null);
       } catch (Exception e) {
           assertEquals(Message.DRONE_DOESNT_EXIST, e.getMessage());
       }
    }

    @Test
    void testIsDroneHasSpace() {
        Drone drone = new Drone("1111111", DroneModel.Heavyweight, 500.0, 100, DroneState.LOADING, 0.0);
        assertEquals(true, droneInternalService.isDroneHasSpace(drone, 200));
    }

    @Test
    void testIsDroneHasSpaceByNull() {
        try {
            droneInternalService.isDroneHasSpace(null, 200);
        } catch (Exception e) {
            assertEquals(Message.DRONE_DOESNT_EXIST, e.getMessage());
        }
    }
}
