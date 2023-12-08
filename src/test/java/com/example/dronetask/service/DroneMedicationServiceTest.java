package com.example.dronetask.service;

import com.example.dronetask.constant.Message;
import com.example.dronetask.dto.LoadDroneDTO;
import com.example.dronetask.dto.MedicationDTO;
import com.example.dronetask.model.DroneState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DroneMedicationServiceTest {

    @Autowired
    DroneMedicationService droneMedicationService;

    @Test
    public void testLoadDrone() {
        String serialNumber = "1111112";
        MedicationDTO medicationDTO = new MedicationDTO("natrelex", 50.0, "123ABCD", "image.png");
        MedicationDTO medicationDTO1 = new MedicationDTO("c retard", 20.0, "MAS82J", "image.jpg");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        medicationDTOList.add(medicationDTO1);
        LoadDroneDTO expectedLoadDrone = new LoadDroneDTO(serialNumber, medicationDTOList);
        droneMedicationService.loadDrone(expectedLoadDrone);
        LoadDroneDTO returnedLoadDrone = droneMedicationService.getDroneMedications("1111112");
        assertEquals(expectedLoadDrone, returnedLoadDrone);
    }
    @Test
    public void testLoadDroneByInvalidSerial() {
        String serialNumber = " ";
        MedicationDTO medicationDTO = new MedicationDTO("natrelex", 50.0, "123ABCD", "image.png");
        MedicationDTO medicationDTO1 = new MedicationDTO("c retard", 20.0, "MAS82J", "image.jpg");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        medicationDTOList.add(medicationDTO1);
        LoadDroneDTO expectedLoadDrone = new LoadDroneDTO(serialNumber, medicationDTOList);
        try {
            droneMedicationService.loadDrone(expectedLoadDrone);
        } catch (Exception e) {
            assertEquals(Message.DRONE_DOESNT_EXIST + " in " + DroneState.LOADING + " state", e.getMessage());
        }
    }

    @Test
    public void testLoadDroneByNull() {
        String serialNumber = null;
        MedicationDTO medicationDTO = new MedicationDTO("natrelex", 50.0, "123ABCD", "image.png");
        MedicationDTO medicationDTO1 = new MedicationDTO("c retard", 20.0, "MAS82J", "image.jpeg");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        medicationDTOList.add(medicationDTO1);
        LoadDroneDTO expectedLoadDrone = new LoadDroneDTO(serialNumber, medicationDTOList);
        try {
            droneMedicationService.loadDrone(expectedLoadDrone);
        } catch (Exception e) {
            assertEquals(Message.DRONE_DOESNT_EXIST + " in " + DroneState.LOADING + " state", e.getMessage());
        }
    }

    @Test
    public void testLoadDroneByExistedMedicationCode() {
        String serialNumber = "1111112";
        MedicationDTO medicationDTO = new MedicationDTO("natrelex", 50.0, "122AGF5", "image.png");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO(serialNumber, medicationDTOList);
        try {
            droneMedicationService.loadDrone(loadDroneDTO);
        } catch (Exception e) {
            assertEquals(Message.MEDICATION_CODE_EXISTS + "122AGF5", e.getMessage());
        }
    }

    @Test
    public void testLoadDroneByHugeWeight() {
        String serialNumber = "1111111";
        MedicationDTO medicationDTO = new MedicationDTO("natrelex", 500.0, "123ABCD", "image.png");
        MedicationDTO medicationDTO1 = new MedicationDTO("c retard", 300.0, "MAS82J", "image.jpg");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        medicationDTOList.add(medicationDTO1);
        LoadDroneDTO expectedLoadDrone = new LoadDroneDTO(serialNumber, medicationDTOList);
        try {
            droneMedicationService.loadDrone(expectedLoadDrone);
        } catch (Exception e) {
            assertEquals(Message.WEIGHT_LIMIT_EXCEEDED, e.getMessage());
        }
    }

    @Test
    public void testGetDroneMedications() {
        String serialNumber = "1111111";
        MedicationDTO medicationDTO = new MedicationDTO("Panadol", 50.0,"122AGF5", "image.png");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        LoadDroneDTO expectedLoadDroneDTO = new LoadDroneDTO("1111111", medicationDTOList);
        LoadDroneDTO returnedLoadDroneDTO = droneMedicationService.getDroneMedications(serialNumber);
        assertEquals(expectedLoadDroneDTO, returnedLoadDroneDTO);
    }

    @Test
    public void testGetDroneMedicationsByNull() {
        String serialNumber = null;
        try {
            LoadDroneDTO returnedLoadDroneDTO = droneMedicationService.getDroneMedications(serialNumber);
        } catch (Exception e) {
            assertEquals("The given id must not be null", e.getMessage());
        }
    }

    @Test
    public void testGetDroneMedicationsByInvaild() {
        String serialNumber = "   ";
        try {
            LoadDroneDTO returnedLoadDroneDTO = droneMedicationService.getDroneMedications(serialNumber);
        } catch (Exception e) {
            assertEquals(Message.DRONE_DOESNT_EXIST, e.getMessage());
        }
    }
}
