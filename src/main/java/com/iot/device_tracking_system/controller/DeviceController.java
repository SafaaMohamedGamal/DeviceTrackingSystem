package com.iot.device_tracking_system.controller;

import com.iot.device_tracking_system.model.DeviceDto;
import com.iot.device_tracking_system.model.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
public interface DeviceController {

    @PostMapping()
    ResponseEntity<Response> createDevice(@Valid @RequestBody DeviceDto deviceDto) ;

    @PutMapping("/{id}")
    ResponseEntity<Response> updateDevice(@Valid @RequestBody DeviceDto deviceDto, @PathVariable(name = "id") long id);

    @DeleteMapping("/{id}")
    ResponseEntity<Response>  deleteDevice(@PathVariable(name = "id") long id) ;

    @DeleteMapping
    ResponseEntity<Response>  deleteAllDevices();

    @GetMapping("/{id}")
    ResponseEntity<Response>  getDevice(@PathVariable(name = "id") long id);

    @GetMapping()
    ResponseEntity<Response>  getAllDevices(
            @PageableDefault(page = 1, size = 5)
            @SortDefault(sort = "pinCode", direction = Sort.Direction.ASC)
            Pageable pageable) ;


    @PutMapping("/{id}/configuration")
    ResponseEntity<Response> configureDevice(@PathVariable(name = "id") long id) ;
}
