package com.iot.DeviceTrackingSystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iot.DeviceTrackingSystem.model.DeviceDto;
import com.iot.DeviceTrackingSystem.model.DeviceResponse;
import com.iot.DeviceTrackingSystem.model.Response;
import com.iot.DeviceTrackingSystem.service.DeviceService;

@Validated
@RestController
@RequestMapping("/devices")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;
	
	@PostMapping()
	public ResponseEntity<Response> createDevice(@Valid @RequestBody DeviceDto deviceDto) {
		Response response;
		HttpStatus status;
		try {
			response = new Response(true, "Device added successfully", deviceService.createDevice(deviceDto));
			status = HttpStatus.CREATED;
		}catch (Exception e) {
			response = new Response(false, e.getMessage(), null);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response>(response, status);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateDevice(@RequestBody DeviceDto deviceDto, @PathVariable(name = "id") long id) {
		Response response;
		HttpStatus status;
		try {
			response = new Response(true, "Device updated successfully", deviceService.updateDevice(deviceDto, id));
			status = HttpStatus.OK;
		}catch (Exception e) {
			response = new Response(false, e.getMessage(), null);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response>(response, status);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response>  deleteDevice(@PathVariable(name = "id") long id) {
		Response response;
		HttpStatus status;
		try{
			deviceService.deleteDeviceById(id);
			response = new Response(true, "Device deleted successfully", null);
			status = HttpStatus.OK;
		}catch (Exception e) {
			response = new Response(false, e.getMessage(), null);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response>(response, status);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<Response>  deleteAllDevices() {
		Response response;
		HttpStatus status;
		try{
			deviceService.deleteAllDevices();
			response = new Response(true, "Devices deleted successfully", null);
			status = HttpStatus.OK;
		}catch (Exception e) {
			response = new Response(false, e.getMessage(), null);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response>(response, status);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response>  getDevice(@PathVariable(name = "id") long id) {
		Response response;
		HttpStatus status;
		try {
			response = new Response(true, "Data found", deviceService.getDeviceById(id));
			status = HttpStatus.OK;
		}catch (Exception e) {
			response = new Response(false, e.getMessage(), null);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response>(response, status);
	}
	
	@GetMapping()
	public ResponseEntity<Response>  getAllDevices(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "pinCode", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
            ) {
		Response response;
		HttpStatus status;
		try {
			response = new Response(true, "Data found", deviceService.getAllDevices(pageNo, pageSize, sortBy, sortDir));
			status = HttpStatus.OK;
		}catch (Exception e) {
			response = new Response(false, e.getMessage(), null);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response>(response, status);
	}
	

	@PutMapping("/{id}/configuration")
	public ResponseEntity<Response> configureDevice(@PathVariable(name = "id") long id) {
		Response response;
		HttpStatus status;
		try {
			response = new Response(true, "Device configured", deviceService.configureDevice(id));
			status = HttpStatus.OK;
		}catch (Exception e) {
			response = new Response(false, e.getMessage(), null);
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Response>(response, status);
	}
}
