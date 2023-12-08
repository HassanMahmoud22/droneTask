package com.example.dronetask.service;

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
        MedicationDTO medicationDTO = new MedicationDTO("dawa ko7a", 50.0, "123abcd", "asdasnfhwqe");
        MedicationDTO medicationDTO1 = new MedicationDTO("panadoll", 20.0, "masfi82j", "acnlkd");
        Medication medication = new Medication("dawa ko7a", 50.0, "123abcd", "asdasnfhwqe", null);
        Medication medication1 = new Medication("panadoll", 20.0, "masfi82j", "acnlkd", null);
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
}
