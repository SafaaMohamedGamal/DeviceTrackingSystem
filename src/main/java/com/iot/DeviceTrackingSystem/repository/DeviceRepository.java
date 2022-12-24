package com.iot.DeviceTrackingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iot.DeviceTrackingSystem.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

	public Device getById(long id);
	public Device getByPinCode(String pinCode);
}
