package com.example.dronetask.service;

import com.example.dronetask.constant.Message;
import com.example.dronetask.model.Drone;
import com.example.dronetask.model.DroneModel;
import com.example.dronetask.model.DroneState;
import com.example.dronetask.model.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MedicationServiceTest {
    @Autowired
    private MedicationService medicationService;

    private Drone drone;
    private List<Medication> medications;
    MedicationServiceTest() {
        medications = new ArrayList<>();
        drone = new Drone("12345", DroneModel.Lightweight, 500.0, 100, DroneState.LOADING, 0.0);
        Medication medication = new Medication("modad 7ywy", 20.0, "12ksd1", "afsadgeg", drone);
        Medication medication2 = new Medication("dwa bard", 50.0, "code2", "imageeee", drone);
        medications.add(medication);
        medications.add(medication2);
    }

    @Test
    void testRegisterMedications() {
        assertEquals(medications, medicationService.registerMedications(medications));
    }

   @Test
    void testListMedicationsDTOWithNullDrone() {
        try {
            medicationService.listMedications(null);
        } catch (Exception e) {

            assertEquals( Message.DRONE_DOESNT_EXIST + " loaded by medications", e.getMessage() );
        }
    }

    @Test
    void testListMedicationsDTOWithDummySerialNumber() {
        Drone drone = new Drone();
        drone.setSerialNumber("dummy");
        try {
            medicationService.listMedications(drone);
        } catch (Exception e) {
            assertEquals( Message.DRONE_DOESNT_EXIST + " loaded by medications", e.getMessage() );
        }
    }
}
