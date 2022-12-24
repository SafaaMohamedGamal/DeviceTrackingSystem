package com.iot.DeviceTrackingSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot.DeviceTrackingSystem.model.Device;
import com.iot.DeviceTrackingSystem.model.DeviceDto;
import com.iot.DeviceTrackingSystem.model.DeviceResponse;
import com.iot.DeviceTrackingSystem.repository.DeviceRepository;
import com.iot.DeviceTrackingSystem.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;
	

    public DeviceResponse createDevice(DeviceDto deviceDto) {
    	if(deviceRepository.getByPinCode(deviceDto.getPinCode())!=null) 
    		throw new RuntimeException("Device with this pin-code already exists!");
    	Device device = new Device();
    	BeanUtils.copyProperties(deviceDto, device);
    	deviceRepository.save(device);
    	DeviceResponse createdDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, createdDevice);
    	return createdDevice;
    }

    public List<DeviceResponse> getAllDevices(){
    	List<Device> devices = deviceRepository.findAll();
    	List<DeviceResponse> deviceResList = devices.stream().map(device -> {
    		DeviceResponse deviceRes = new DeviceResponse();
        	BeanUtils.copyProperties(device, deviceRes);
        	return deviceRes;
    	}).collect(Collectors.toList());
    	return deviceResList;
    }

    public DeviceResponse getDeviceById(long id) {
    	Device device = deviceRepository.getById(id);
    	DeviceResponse returnedDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, returnedDevice);
    	return returnedDevice;
    }

    public DeviceResponse updateDevice(DeviceDto deviceDto, long id) {
    	Device device = deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("There isn't any device with id="+id));
    	BeanUtils.copyProperties(deviceDto, device);
    	deviceRepository.save(device);
    	DeviceResponse updatedDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, updatedDevice);
    	return updatedDevice;
    	
    }

    public void deleteDeviceById(long id) {
    	deviceRepository.deleteById(id);
    }

    public void deleteAllDevices() {
    	deviceRepository.deleteAll();
    }
    
}
