package com.example.dronetask.service;

import com.example.dronetask.constant.Message;
import com.example.dronetask.dto.MedicationDTO;
import com.example.dronetask.model.Drone;
import com.example.dronetask.model.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MedicationServiceTest {
    @Autowired
    private MedicationService medicationService;
    private List<Medication> medications;
    private List<MedicationDTO> medicationDTOList;
    private List<Medication> medicationList;

    MedicationServiceTest() {
        medications = new ArrayList<>();
        medicationDTOList = new ArrayList<>();
        medicationList = new ArrayList<>();
        Medication medication = new Medication("c retard", 50.0, "123ABCD", "image.png", null);
        Medication medication1 = new Medication("natrelex", 20.0, "MASI82J", "image.jpg", null);
        MedicationDTO medicationDTO = new MedicationDTO("c retard", 50.0, "123ABCD", "image.png");
        MedicationDTO medicationDTO1 = new MedicationDTO("natrelex", 20.0, "MASI82J", "image.jpg");
        medications.add(medication);
        medications.add(medication1);
        medicationDTOList.add(medicationDTO);
        medicationDTOList.add(medicationDTO1);
        medicationList.add(medication);
        medicationList.add(medication1);
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

    @Test
    public void testMapMedicationDTOsToMedications() {
        List<Medication> medications = medicationService.mapMedicationDTOsToMedications(medicationDTOList);
        assertEquals(medicationList, medications);
    }

    @Test
    public void testMapMedicationDTOsToMedicationsByNull() {
        List<Medication> medications = medicationService.mapMedicationDTOsToMedications(null);
        assertNull(medications);
    }

    @Test
    public void testMapMedicationDTOsToMedicationsByEmpty() {
        List<MedicationDTO> medicationDTOS = new ArrayList<>();
        List<Medication> medications = medicationService.mapMedicationDTOsToMedications(medicationDTOS);
        assertEquals(new ArrayList<>(), medications);
    }

    @Test
    public void testCalculateTotalWeightOfMedications() {
        double totalWeight = medicationService.calculateTotalWeightOfMedications(medicationList);
        assertEquals(70.0, totalWeight);
    }

    @Test
    public void testCalculateTotalWeightOfMedicationsByNull() {
        double totalWeight = medicationService.calculateTotalWeightOfMedications(null);
        assertEquals(0, totalWeight);
    }

    @Test
    public void testCalculateTotalWeightOfMedicationsByEmpty() {
        List<Medication> medications = new ArrayList<>();
        double totalWeight = medicationService.calculateTotalWeightOfMedications(medications);
        assertEquals(0, totalWeight);
    }

    @Test
    public void testThrowExceptionIfMedicationExist()  {
        MedicationDTO medicationDTO = new MedicationDTO("Panadol", 50.0,"122AGF5", "image.png");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        try {
            medicationService.throwExceptionIfMedicationExist(medicationDTOList);
        } catch (Exception e) {
            assertEquals(Message.MEDICATION_CODE_EXISTS + "122AGF5", e.getMessage());
        }
    }
}
