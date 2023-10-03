package com.iot.device_tracking_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.device_tracking_system.entity.Device;
import com.iot.device_tracking_system.model.DeviceDto;
import com.iot.device_tracking_system.model.Status;
import com.iot.device_tracking_system.repository.DeviceRepository;
import com.iot.device_tracking_system.service.DeviceService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
class DeviceControllerImplTest {

    @Autowired
    MockMvc mockMvc; // to perform request
    @Autowired
    ObjectMapper mapper; // json converter

    @Autowired
    DeviceService deviceService;
    @Autowired
    DeviceRepository deviceRepository;

    final String URL = "/devices";
    List<Device> deviceList;
    DeviceDto deviceDto;
    Status ready;
    Status active;
    Device configuredDevice;
    Device createdDevice;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();
        ready = new Status(1, "Ready");
        active = new Status(2, "Active");
        deviceList = new ArrayList<>();
        deviceList.add(new Device(-1, "1112221", ready));
        deviceList.add(new Device(5, "1112211", active));
        deviceList.add(new Device(7, "1443111", active));
        deviceList.add(new Device(-1, "111231", ready));
        deviceRepository.saveAll(deviceList);

        Device device = new Device();
        device.setStatus(active);
        device.setTemperature(7);
        device.setPinCode("1122337");
        configuredDevice = deviceRepository.save(device);

        device = new Device();
        device.setStatus(ready);
        device.setTemperature(6);
        device.setPinCode("1112212");
        createdDevice = deviceRepository.save(device);

        deviceDto = new DeviceDto();
        deviceDto.setPinCode("5555666");
        deviceDto.setStatusId(2);
        deviceDto.setTemperature(6);

    }

    @Test
    void configureDevice_shouldSucceed() throws Exception {
        mockMvc.perform(put(URL + "/" + createdDevice.getId() + "/configuration")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void configureConfiguredDevice_shouldFailAnd() throws Exception {
        mockMvc.perform(put(URL + "/" + configuredDevice.getId() + "/configuration")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllDevices_shouldReturnSuccess3Records() throws Exception {
//
//		MvcResult mvcResult =
        mockMvc.perform(get(URL + "?page=0&size=5")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content.size()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages").value(2));
    }

    @Test
    void createDevice_shouldReturn201() throws Exception {

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(deviceDto))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.status.id").value(deviceDto.getStatusId()))
                .andExpect(jsonPath("$.data.temperature").value(deviceDto.getTemperature()));
    }

    @Test
    void createDeviceWithoutPincode_shouldFail() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setStatusId(2);
        device.setTemperature(6);
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(device))
                ).andExpect(status().isBadRequest())
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateDevice_shouldSucceed() throws Exception {
        DeviceDto updatedDevice = new DeviceDto();
        updatedDevice.setTemperature(8);
        updatedDevice.setStatusId(2);
        updatedDevice.setPinCode("1122337");
        mockMvc.perform(put(URL + "/" + configuredDevice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedDevice))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.temperature").value(updatedDevice.getTemperature()));

    }


    @Test
    void getDevice_shouldSucceed() throws Exception {
        mockMvc.perform(get(URL + "/" + configuredDevice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void deleteDevice_shouldSucceed() throws Exception {
        mockMvc.perform(get(URL + "/" + configuredDevice.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNonExistingDevice_shouldThrowException() throws Exception {
        mockMvc.perform(delete(URL + "/" + RandomStringUtils.randomNumeric(5)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAllDevices_shouldSucceed() throws Exception {
        mockMvc.perform(delete(URL))
                .andExpect(status().isOk());
    }
}
