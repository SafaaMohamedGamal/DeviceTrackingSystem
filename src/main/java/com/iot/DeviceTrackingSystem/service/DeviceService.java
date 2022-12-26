package com.iot.DeviceTrackingSystem.service;

import java.util.List;

import com.iot.DeviceTrackingSystem.model.DeviceDto;
import com.iot.DeviceTrackingSystem.model.DeviceResponse;

public interface DeviceService {


	DeviceResponse createDevice(DeviceDto DeviceDto);

    public List<DeviceResponse> getAllDevices(int pageNo, int pageSize, String sortBy, String sortDir);

    DeviceResponse getDeviceById(long id);

    DeviceResponse updateDevice(DeviceDto DeviceDto, long id);

    void deleteDeviceById(long id);
    
    void deleteAllDevices();
    
    DeviceResponse configureDevice(long id);
}
