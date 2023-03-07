package com.iot.DeviceTrackingSystem.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot.DeviceTrackingSystem.DeviceTrackingSystemApplication;
import com.iot.DeviceTrackingSystem.model.DeviceDto;
import com.iot.DeviceTrackingSystem.model.Response;
import com.iot.DeviceTrackingSystem.service.DeviceService;

@Validated
@RestController
@RequestMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {

    private static final Logger LOGGER = LogManager.getLogger(DeviceController.class);
	@Autowired
	private DeviceService deviceService;
	
	@PostMapping()
	public ResponseEntity<Response> createDevice(@Valid @RequestBody DeviceDto deviceDto) {
		Response response;
		HttpStatus status;

			response = new Response(true, "Device added successfully", deviceService.createDevice(deviceDto));
			status = HttpStatus.CREATED;
		return new ResponseEntity<Response>(response, status);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateDevice(@Valid @RequestBody DeviceDto deviceDto, @PathVariable(name = "id") long id) {
		Response response;
		HttpStatus status;

			response = new Response(true, "Device updated successfully", deviceService.updateDevice(deviceDto, id));
			status = HttpStatus.OK;
		return new ResponseEntity<Response>(response, status);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response>  deleteDevice(@PathVariable(name = "id") long id) {
		Response response;
		HttpStatus status;

			deviceService.deleteDeviceById(id);
			response = new Response(true, "Device deleted successfully", "");
			status = HttpStatus.OK;
		return new ResponseEntity<Response>(response, status);
	}
	
	@DeleteMapping
	public ResponseEntity<Response>  deleteAllDevices() {
		Response response;
		HttpStatus status;

			deviceService.deleteAllDevices();
			response = new Response(true, "Devices deleted successfully", "");
			status = HttpStatus.OK;
		return new ResponseEntity<Response>(response, status);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response>  getDevice(@PathVariable(name = "id") long id) {
		Response response;
		HttpStatus status;

			response = new Response(true, "Data found", deviceService.getDeviceById(id));
			status = HttpStatus.OK;
		return new ResponseEntity<Response>(response, status);
	}
	
	@GetMapping()
	public ResponseEntity<Response>  getAllDevices(
  	      @PageableDefault(page = 1, size = 5)
  	      @SortDefault(sort = "pinCode", direction = Sort.Direction.ASC)
  	       Pageable pageable) {
		Response response;
		HttpStatus status;
		
			response = new Response(true, "Data found", deviceService.getAllDevices(pageable));
			status = HttpStatus.OK;
		return new ResponseEntity<Response>(response, status);
	}
	

	@PutMapping("/{id}/configuration")
	public ResponseEntity<Response> configureDevice(@PathVariable(name = "id") long id) {
		Response response;
		HttpStatus status;
			response = new Response(true, "Device configured", deviceService.configureDevice(id));
			status = HttpStatus.OK;

		return new ResponseEntity<Response>(response, status);
	}
}
