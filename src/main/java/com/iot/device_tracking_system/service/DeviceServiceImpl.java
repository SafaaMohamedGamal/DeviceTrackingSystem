package com.iot.device_tracking_system.service;

import com.iot.device_tracking_system.entity.Device;
import com.iot.device_tracking_system.exception.DeviceAPIException;
import com.iot.device_tracking_system.model.DeviceDto;
import com.iot.device_tracking_system.model.DeviceResponse;
import com.iot.device_tracking_system.repository.DeviceRepository;
import com.iot.device_tracking_system.repository.StatusRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

	private static final String DEVICE_DOES_NOT_EXIST = "Device doesn't exist!";
	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private StatusRepository statusRepository;
	

    public DeviceResponse createDevice(DeviceDto deviceDto) {
    	if(deviceDto.getPinCode()==null || deviceDto.getTemperature()==null || deviceDto.getStatusId()==null)
    		throw new DeviceAPIException("Parameters are missing (pinCode, temperature, statusId)!"
    				, HttpStatus.BAD_REQUEST);
    	if(deviceRepository.getByPinCode(deviceDto.getPinCode())!=null) 
    		throw new DeviceAPIException("Device with this pin-code already exists!", HttpStatus.BAD_REQUEST);
    	Device device = new Device();
    	BeanUtils.copyProperties(deviceDto, device);
    	device.setStatus(statusRepository.findById(device.getStatusId()).orElse(null));
    	device = deviceRepository.save(device);
    	DeviceResponse createdDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, createdDevice);
    	return createdDevice;
    }

    public Page<Device> getAllDevices(Pageable pageable) {
        Page<Device> pageDevices= deviceRepository.findAll(pageable);
        if (pageable.getPageNumber() > pageDevices.getTotalPages()) {
			throw new DeviceAPIException("No Data found!", HttpStatus.BAD_REQUEST);
		}
    	return pageDevices;
    }

    public DeviceResponse getDeviceById(long id) {
    	Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceAPIException(DEVICE_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST));
    	DeviceResponse returnedDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, returnedDevice);
    	return returnedDevice;
    }

    public DeviceResponse updateDevice(DeviceDto deviceDto, long id) {
    	Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceAPIException(DEVICE_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST));
    	
    	if(deviceDto.getPinCode()==null) {
			deviceDto.setPinCode(device.getPinCode());
		}
    	else {
	    	Device pinCodeDevice = deviceRepository.getByPinCode(deviceDto.getPinCode());
	    	if(pinCodeDevice!=null && pinCodeDevice.getId()!=device.getId())
	    		throw new DeviceAPIException("Device with this pin-code '"+deviceDto.getPinCode()
	    		+"' already exists!", HttpStatus.BAD_REQUEST);
    	}
    	if(deviceDto.getTemperature()==null) deviceDto.setTemperature(device.getTemperature());
    	if(deviceDto.getStatusId()==null) deviceDto.setStatusId(device.getStatusId());
    	
    	BeanUtils.copyProperties(deviceDto, device);
    	device.setStatus(statusRepository.findById(device.getStatusId()).orElse(null));
    	device = deviceRepository.save(device);
    	DeviceResponse updatedDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, updatedDevice);
    	return updatedDevice;
    	
    }

    public void deleteDeviceById(long id) {
    	if(deviceRepository.findById(id).isEmpty()) 
    		throw new DeviceAPIException(DEVICE_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST);
    	deviceRepository.deleteById(id);
    }

    public void deleteAllDevices() {
    	deviceRepository.deleteAll();
    }

	@Override
    public DeviceResponse configureDevice(long id) {

    	Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceAPIException(DEVICE_DOES_NOT_EXIST, HttpStatus.BAD_REQUEST));
    	if(device.getStatusId()==2)
    		throw new DeviceAPIException("This device already configured", HttpStatus.BAD_REQUEST);
    	device.setTemperature(Integer.parseInt(RandomStringUtils.randomNumeric(0,11)));
    	device.setStatus(statusRepository.findById(2).orElse(null));
    	device = deviceRepository.save(device);
    	DeviceResponse configuredDevice = new DeviceResponse();
    	BeanUtils.copyProperties(device, configuredDevice);
    	return configuredDevice;
    }
    
}
