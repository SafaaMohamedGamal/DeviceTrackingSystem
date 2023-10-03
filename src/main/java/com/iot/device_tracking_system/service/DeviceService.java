package com.iot.device_tracking_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iot.device_tracking_system.entity.Device;
import com.iot.device_tracking_system.model.DeviceDto;
import com.iot.device_tracking_system.model.DeviceResponse;

public interface DeviceService {


	DeviceResponse createDevice(DeviceDto DeviceDto);

    public Page<Device> getAllDevices(Pageable pageable);

    DeviceResponse getDeviceById(long id);

    DeviceResponse updateDevice(DeviceDto DeviceDto, long id);

    void deleteDeviceById(long id);
    
    void deleteAllDevices();
    
    DeviceResponse configureDevice(long id);
}
