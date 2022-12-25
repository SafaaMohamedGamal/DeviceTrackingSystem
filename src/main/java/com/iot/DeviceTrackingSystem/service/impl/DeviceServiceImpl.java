package com.iot.DeviceTrackingSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.iot.DeviceTrackingSystem.exception.DeviceAPIException;
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
    	if(deviceDto.getPinCode()==null || deviceDto.getTemprature()==0 || deviceDto.getStatusId()==0) 
    		throw new DeviceAPIException("Parameters are missing (pinCode, temprature, statusId)!"
    				, HttpStatus.BAD_REQUEST);
    	if(deviceRepository.getByPinCode(deviceDto.getPinCode())!=null) 
    		throw new DeviceAPIException("Device with this pin-code already exists!", HttpStatus.BAD_REQUEST);
    	Device device = new Device();
    	BeanUtils.copyProperties(deviceDto, device);

    	device = deviceRepository.save(device);
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
    	Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceAPIException("There isn't any device with id="+id, HttpStatus.BAD_REQUEST));
    	DeviceResponse returnedDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, returnedDevice);
    	return returnedDevice;
    }

    public DeviceResponse updateDevice(DeviceDto deviceDto, long id) {
    	Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceAPIException("There isn't any device with id="+id, HttpStatus.BAD_REQUEST));
    	
    	if(deviceDto.getPinCode()==null) deviceDto.setPinCode(device.getPinCode());
    	else
    	{
	    	Device pinCodeDevice = deviceRepository.getByPinCode(deviceDto.getPinCode());
	    	if(pinCodeDevice!=null && pinCodeDevice.getId()!=device.getId())
	    		throw new DeviceAPIException("Device with this pin-code '"+deviceDto.getPinCode()
	    		+"' already exists!", HttpStatus.BAD_REQUEST);
    	}
    	if(deviceDto.getTemprature()==0) deviceDto.setTemprature(device.getTemprature());
    	if(deviceDto.getStatusId()==0) deviceDto.setStatusId(device.getStatusId());
    	
    	BeanUtils.copyProperties(deviceDto, device);
    	device = deviceRepository.save(device);
    	DeviceResponse updatedDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, updatedDevice);
    	return updatedDevice;
    	
    }

    public void deleteDeviceById(long id) {
    	if(deviceRepository.findById(id).isEmpty()) 
    		throw new DeviceAPIException("Device doesn't exist!", HttpStatus.BAD_REQUEST);
    	deviceRepository.deleteById(id);
    }

    public void deleteAllDevices() {
    	deviceRepository.deleteAll();
    }
    
}
