package com.iot.device_tracking_system.repository;

import com.iot.device_tracking_system.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

	public Device getById(long id);
	public Device getByPinCode(String pinCode);
}
