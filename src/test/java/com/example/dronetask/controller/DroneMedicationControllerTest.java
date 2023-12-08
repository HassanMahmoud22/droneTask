package com.example.dronetask.controller;

import com.example.dronetask.dto.LoadDroneDTO;
import com.example.dronetask.dto.MedicationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith (SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DroneMedicationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void testLoadDrone() throws Exception {
        MedicationDTO medicationDTO = new MedicationDTO("cold and flu", 50.0, "WASD1_23", "image.png");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO("1111111", medicationDTOList);
        String postValue = OBJECT_MAPPER.writeValueAsString(loadDroneDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/DroneMedication/load")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andExpect(status().isOk());
    }

    @Test
    void testLoadDroneByInvalid() throws Exception {
        MedicationDTO medicationDTO = new MedicationDTO(" ", 600.0, "@!#$%", "image.png");
        List<MedicationDTO> medicationDTOList = new ArrayList<>();
        medicationDTOList.add(medicationDTO);
        LoadDroneDTO loadDroneDTO = new LoadDroneDTO("1111111", medicationDTOList);
        String postValue = OBJECT_MAPPER.writeValueAsString(loadDroneDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/DroneMedication/load")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetMedications() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/DroneMedication/getMedications/{serialNumber}", "1111111"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMedicationsByInvalidSerial() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/DroneMedication/getMedications/{serialNumber}", "    "))
                .andExpect(status().isNotFound());
    }
}
