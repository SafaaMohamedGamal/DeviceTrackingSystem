package com.iot.DeviceTrackingSystem.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.DeviceTrackingSystem.controller.DeviceController;
import com.iot.DeviceTrackingSystem.exception.DeviceAPIException;
import com.iot.DeviceTrackingSystem.model.Device;
import com.iot.DeviceTrackingSystem.model.DeviceDto;
import com.iot.DeviceTrackingSystem.model.DeviceResponse;
import com.iot.DeviceTrackingSystem.model.Response;
import com.iot.DeviceTrackingSystem.model.Status;
import com.iot.DeviceTrackingSystem.service.DeviceService;

@WebMvcTest(DeviceController.class)
@TestInstance(Lifecycle.PER_CLASS)
class DeviceControllerTest {

	@Autowired
	MockMvc mockMvc; // to perform request
	@Autowired
	ObjectMapper mapper; // json converter

	@MockBean
	DeviceService deviceService;

	final String URL = "/devices";
	List<Device> deviceList;
	DeviceDto deviceDto;
	DeviceResponse createdDevice;
	Status ready;
	Status active;
	@BeforeAll
	void setUp() {
		ready = new Status(1, "Ready");
		active = new Status(2, "Active");
		deviceList = new ArrayList<>();
		deviceList.add(new Device(1, -1, "1112221", ready));
		deviceList.add(new Device(2, 5, "1112211", active));
		deviceList.add(new Device(3, 7, "1443111", active));
		deviceList.add(new Device(4, -1, "111231", ready));
		deviceList.add(new Device(5, 2, "1112211", ready));

		deviceDto = new DeviceDto();
		deviceDto.setPinCode("5555666");
		deviceDto.setStatusId(2);
		deviceDto.setTemprature(6);
		createdDevice = new DeviceResponse();
		createdDevice.setId(13);
		createdDevice.setPinCode("5555666");
		createdDevice.setStatus(active);
		createdDevice.setTemprature(6);

	}

	@Test
	public void configureDevice_shouldSucceed() throws Exception {
		Device device = deviceList.get(0);
		DeviceResponse returnedDevice = new DeviceResponse();
		returnedDevice.setId(device.getId());
		returnedDevice.setPinCode(device.getPinCode());
		returnedDevice.setStatus(active);
		returnedDevice.setTemprature(6);
		Mockito.when(deviceService.configureDevice(device.getId())).thenReturn(returnedDevice);

		MvcResult mvcResult = mockMvc.perform(put(URL+"/"+device.getId()+"/configuration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Long.toString(device.getId()))
				).andExpect(status().isOk())
			.andReturn();
		
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		assertTrue(response.isSuccess());
	}

	@Test
	public void configureConfiguredDevice_shouldFailAnd() throws Exception {
		Device device = deviceList.get(1);
		Mockito.when(deviceService.configureDevice(device.getId())).thenThrow(DeviceAPIException.class);

		MvcResult mvcResult = mockMvc.perform(put(URL+"/"+device.getId()+"/configuration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Long.toString(device.getId()))
				).andExpect(status().isBadRequest())
			.andReturn();
		
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		assertFalse(response.isSuccess());
	}

	@Test
	public void getAllDevices_shouldReturnSuccess3Records() throws Exception {
		Pageable pageable = PageRequest.of(1, 3).withSort(Sort.Direction.ASC, "pinCode");
		final Page<Device> page = new PageImpl<>(deviceList.subList(0, 3), pageable, deviceList.size());

		Mockito.when(deviceService.getAllDevices(pageable)).thenReturn(page);

		MvcResult mvcResult = mockMvc.perform(get(URL + "?page=1&size=3"))
				.andExpect(status().isOk())
				.andReturn();
		String stringResponse = mvcResult.getResponse().getContentAsString();
		HashMap<String, List<String>> response = (HashMap<String, List<String>>)mapper
											.readValue(stringResponse, Response.class)
											.getData();
		assertEquals(3, response.get("content").size());
		assertEquals(2, response.get("totalPages"));
	}

	@Test
	public void createDevice_shouldReturn201() throws Exception {
		Mockito.when(deviceService.createDevice(deviceDto)).thenReturn(createdDevice);

		MvcResult mvcResult = mockMvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(deviceDto))
				).andExpect(status().isCreated())
			.andReturn();
		
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		DeviceResponse deviceResponse = mapper.readValue((new JSONObject(response.getData().toString())).toString(), DeviceResponse.class);
		assertNotEquals(0, deviceResponse.getId());
		assertEquals(deviceDto.getStatusId(), deviceResponse.getStatus().getId());
		assertEquals(deviceDto.getTemprature(), deviceResponse.getTemprature());
	}

	@Test
	public void createDeviceWithoutPincode_shouldFail() throws Exception {
		DeviceDto device = new DeviceDto();
		device.setStatusId(2);
		device.setTemprature(6);
		Mockito.when(deviceService.createDevice(device)).thenThrow(DeviceAPIException.class);

		MvcResult mvcResult = mockMvc.perform(post(URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(device))
				).andExpect(status().isBadRequest())
			.andReturn();
		
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		assertFalse(response.isSuccess());
	}

	@Test
	public void updateDevice_shouldSucceed() throws Exception {
		Mockito.when(deviceService.updateDevice(deviceDto, 13)).thenReturn(createdDevice);

		MvcResult mvcResult = mockMvc.perform(put(URL+"/13/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(deviceDto))
				).andExpect(status().isOk())
			.andReturn();
		
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		DeviceResponse deviceResponse = mapper.readValue((new JSONObject(response.getData().toString())).toString(), DeviceResponse.class);
		assertNotEquals(0, deviceResponse.getId());
		assertEquals(deviceDto.getStatusId(), deviceResponse.getStatus().getId());
		assertEquals(deviceDto.getTemprature(), deviceResponse.getTemprature());
	}


	@Test
	public void getDevice_shouldSucceed() throws Exception {
		Mockito.when(deviceService.getDeviceById(13)).thenReturn(createdDevice);

		MvcResult mvcResult = mockMvc.perform(get(URL + "/13/"))
		.andExpect(status().isOk())
		.andReturn();
		
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		assertNotNull( response.getData());
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void deleteDevice_shouldSucceed() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get(URL + "/13/"))
				.andExpect(status().isOk())
				.andReturn();
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void deleteNonExistingDevice_shouldThrowException() throws Exception {
		Mockito.doThrow(DeviceAPIException.class)
	       .when(deviceService)
	       .deleteDeviceById(200);
		MvcResult mvcResult = mockMvc.perform(delete(URL + "/200/"))
				.andExpect(status().isBadRequest())
				.andReturn();
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		assertFalse(response.isSuccess());
	}

	@Test
	public void deleteAllDevices_shouldSucceed() throws Exception {
		MvcResult mvcResult = mockMvc.perform(delete(URL))
				.andExpect(status().isOk())
				.andReturn();
		Response response = mapper.readValue(
				mvcResult.getResponse().getContentAsString()
				, Response.class);
		assertTrue(response.isSuccess());
	}
}
