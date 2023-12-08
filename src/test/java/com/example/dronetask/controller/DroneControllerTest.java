package com.example.dronetask.controller;

import com.example.dronetask.constant.Message;
import com.example.dronetask.dto.DroneRequestDTO;
import com.example.dronetask.model.DroneModel;
import com.example.dronetask.model.DroneState;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith (SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void testRegisterDrone() throws Exception {
        DroneRequestDTO droneRequestDTO = new DroneRequestDTO("12345",  200.0, 100);
        String postValue = OBJECT_MAPPER.writeValueAsString(droneRequestDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/drones/registerDrone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serialNumber").value("12345"))
                .andExpect(jsonPath("$.droneModel").value(String.valueOf(DroneModel.Middleweight)))
                .andExpect(jsonPath("$.weightLimit").value(200.0))
                .andExpect(jsonPath("$.batteryCapacity").value(100))
                .andExpect(jsonPath("$.state").value(String.valueOf(DroneState.LOADING)))
                .andExpect(jsonPath("$.weightLoaded").value(0.0));
    }

    @Test
    void testRegisterDroneByInvalidData() throws Exception {
        DroneRequestDTO droneRequestDTO = new DroneRequestDTO("asd234dsf342sdv435dsgdsg657asd234dsf342sdv435dsgdsg657asd234dsf342sdv435dsgdsg657asd234dsf342sdv435dsgdsg657",  600.0, 150);
        String postValue = OBJECT_MAPPER.writeValueAsString(droneRequestDTO);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/drones/registerDrone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postValue))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetDroneBattery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/drones/batteryCapacity/{serialNumber}", "1111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNumber").value("1111111"))
                .andExpect(jsonPath("$.batteryCapacity").value(100));
    }

    @Test
    void testGetDroneBatteryByInvalidSerial() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/drones/batteryCapacity/{serialNumber}", "0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").value(Message.DRONE_DOESNT_EXIST));
    }

    @Test
    void testFindAvailableDrones() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/drones/available"))
                .andExpect(status().isOk());
    }
}