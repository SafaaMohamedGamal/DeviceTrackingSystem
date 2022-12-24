package com.iot.DeviceTrackingSystem.service;

import java.util.List;

import com.iot.DeviceTrackingSystem.model.DeviceDto;
import com.iot.DeviceTrackingSystem.model.DeviceResponse;

public interface DeviceService {


	DeviceResponse createDevice(DeviceDto DeviceDto);

    List<DeviceResponse>  getAllDevices();

    DeviceResponse getDeviceById(long id);

    DeviceResponse updateDevice(DeviceDto DeviceDto, long id);

    void deleteDeviceById(long id);
    
    void deleteAllDevices();
}
