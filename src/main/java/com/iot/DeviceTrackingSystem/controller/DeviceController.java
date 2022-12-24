package com.iot.DeviceTrackingSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.DeviceTrackingSystem.model.DeviceDto;
import com.iot.DeviceTrackingSystem.model.DeviceResponse;
import com.iot.DeviceTrackingSystem.service.DeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;
	
	@PostMapping("/")
	public DeviceResponse createDevice(@RequestBody DeviceDto deviceDto) {
		return deviceService.createDevice(deviceDto);
	}
	
	@PutMapping("/{id}")
	public DeviceResponse updateDevice(@RequestBody DeviceDto deviceDto, @PathVariable(name = "id") long id) {
		return deviceService.updateDevice(deviceDto, id);
	}
	
	@DeleteMapping("/{id}")
	public String deleteDevice(@PathVariable(name = "id") long id) {
		try{
			deviceService.deleteDeviceById(id);
		}catch (Exception e) {
			return "Error";
		}
		return "Success";
	}
	
	@DeleteMapping("/")
	public String deleteAllDevices() {
		try{
			deviceService.deleteAllDevices();
		}catch (Exception e) {
			return "Error";
		}
		return "Success";
	}

	@GetMapping("/{id}")
	public DeviceResponse getDevice(@PathVariable(name = "id") long id) {
		return deviceService.getDeviceById(id);
	}
	
	@GetMapping()
	public List<DeviceResponse> getAllDevices() {
		return deviceService.getAllDevices();
	}
}
