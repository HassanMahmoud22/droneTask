package com.example.dronetask.service;

import com.example.dronetask.constant.Message;
import com.example.dronetask.dto.MedicationDTO;
import com.example.dronetask.model.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MedicationInternalServiceTest {
    @Autowired
    private MedicationInternalService medicationInternalService;
    private List<MedicationDTO> medicationDTOList;
    private List<Medication> medicationList;

    public MedicationInternalServiceTest() {
        medicationDTOList = new ArrayList<>();
        medicationList = new ArrayList<>();
        MedicationDTO medicationDTO = new MedicationDTO("c retard", 50.0, "123ABCD", "image.png");
        MedicationDTO medicationDTO1 = new MedicationDTO("natrelex", 20.0, "MASI82J", "image.jpg");
        Medication medication = new Medication("c retard", 50.0, "123ABCD", "image.png", null);
        Medication medication1 = new Medication("natrelex", 20.0, "MASI82J", "image.jpg", null);
        medicationDTOList.add(medicationDTO);
        medicationDTOList.add(medicationDTO1);
        medicationList.add(medication);
        medicationList.add(medication1);
    }
    @Test
    public void testMapMedicationDTOsToMedications() {
        List<Medication> medications = medicationInternalService.mapMedicationDTOsToMedications(medicationDTOList);
        assertEquals(medicationList, medications);
    }

    @Test
    public void testMapMedicationDTOsToMedicationsByNull() {
        List<Medication> medications = medicationInternalService.mapMedicationDTOsToMedications(null);
        assertNull(medications);
    }

    @Test
    public void testMapMedicationDTOsToMedicationsByEmpty() {
        List<MedicationDTO> medicationDTOS = new ArrayList<>();
        List<Medication> medications = medicationInternalService.mapMedicationDTOsToMedications(medicationDTOS);
        assertEquals(new ArrayList<>(), medications);
    }

    @Test
    public void testCalculateTotalWeightOfMedications() {
        double totalWeight = medicationInternalService.calculateTotalWeightOfMedications(medicationList);
        assertEquals(70.0, totalWeight);
    }

    @Test
    public void testCalculateTotalWeightOfMedicationsByNull() {
        double totalWeight = medicationInternalService.calculateTotalWeightOfMedications(null);
        assertEquals(0, totalWeight);
    }

    @Test
    public void testCalculateTotalWeightOfMedicationsByEmpty() {
        List<Medication> medications = new ArrayList<>();
        double totalWeight = medicationInternalService.calculateTotalWeightOfMedications(medications);
        assertEquals(0, totalWeight);
    }

    @Test
    public void testThrowExceptionIfMedicationExist()  {
        MedicationDTO medicationDTO = new MedicationDTO("Panadol", 50.0,"122AGF5", "image.png");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        try {
            medicationInternalService.throwExceptionIfMedicationExist(medicationDTOList);
        } catch (Exception e) {
            assertEquals(Message.MEDICATION_CODE_EXISTS + "122AGF5", e.getMessage());
        }
    }
}
