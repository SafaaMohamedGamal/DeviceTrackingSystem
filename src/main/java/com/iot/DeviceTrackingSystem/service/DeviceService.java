package com.iot.DeviceTrackingSystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iot.DeviceTrackingSystem.model.Device;
import com.iot.DeviceTrackingSystem.model.DeviceDto;
import com.iot.DeviceTrackingSystem.model.DeviceResponse;

public interface DeviceService {


	DeviceResponse createDevice(DeviceDto DeviceDto);

    public Page<Device> getAllDevices(Pageable pageable);

    DeviceResponse getDeviceById(long id);

    DeviceResponse updateDevice(DeviceDto DeviceDto, long id);

    void deleteDeviceById(long id);
    
    void deleteAllDevices();
    
    DeviceResponse configureDevice(long id);
}
