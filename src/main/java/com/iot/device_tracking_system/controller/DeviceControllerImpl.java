package com.iot.device_tracking_system.controller;

import com.iot.device_tracking_system.model.DeviceDto;
import com.iot.device_tracking_system.model.Response;
import com.iot.device_tracking_system.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class DeviceControllerImpl implements DeviceController {

	private final DeviceService deviceService;
	public DeviceControllerImpl(DeviceService deviceService){
		this.deviceService = deviceService;
	}

	public ResponseEntity<Response> createDevice(@Valid @RequestBody DeviceDto deviceDto) {
		Response response;
		HttpStatus status;

			response = new Response(true, "Device added successfully", deviceService.createDevice(deviceDto));
			status = HttpStatus.CREATED;
		return new ResponseEntity<>(response, status);
	}

	public ResponseEntity<Response> updateDevice(@Valid @RequestBody DeviceDto deviceDto, @PathVariable long id) {
		Response response;
		HttpStatus status;

			response = new Response(true, "Device updated successfully", deviceService.updateDevice(deviceDto, id));
			status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);
	}

	public ResponseEntity<Response>  deleteDevice(@PathVariable long id) {
		Response response;
		HttpStatus status;

			deviceService.deleteDeviceById(id);
			response = new Response(true, "Device deleted successfully", "");
			status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);
	}

	public ResponseEntity<Response>  deleteAllDevices() {
		Response response;
		HttpStatus status;

			deviceService.deleteAllDevices();
			response = new Response(true, "Devices deleted successfully", "");
			status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);
	}

	public ResponseEntity<Response>  getDevice(@PathVariable long id) {
		Response response;
		HttpStatus status;

			response = new Response(true, "Data found", deviceService.getDeviceById(id));
			status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);
	}

	public ResponseEntity<Response>  getAllDevices(
  	      @PageableDefault(size = 5)
  	      @SortDefault(sort = "id", direction = Sort.Direction.ASC)
  	       Pageable pageable) {
		Response response;
		HttpStatus status;
		
			response = new Response(true, "Data found", deviceService.getAllDevices(pageable));
			status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);
	}
	

	public ResponseEntity<Response> configureDevice(@PathVariable long id) {
		Response response;
		HttpStatus status;
			response = new Response(true, "Device configured", deviceService.configureDevice(id));
			status = HttpStatus.OK;

		return new ResponseEntity<>(response, status);
	}
}
