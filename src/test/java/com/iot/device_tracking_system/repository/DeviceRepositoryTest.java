package com.iot.device_tracking_system.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.iot.device_tracking_system.entity.Device;

@DataJpaTest()
class DeviceRepositoryTest {

	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private StatusRepository statusRepository;

	@Test
	void addDeviceWithoutStatus_ThrowsIntegrityViolationException() {
		Device device = new Device();
		device.setPinCode("1111111");
		device.setTemperature(1);
		assertThrows(DataIntegrityViolationException.class, () -> {
			deviceRepository.save(device);
	    });
	}

	@Test
	void addDevice_shouldReturnDeviceWithCorrectStatus() {
		Device device = new Device();
		device.setPinCode("1111112");
		device.setTemperature(1);
		device.setStatus(statusRepository.getById(1));
		Device createdDevice = deviceRepository.save(device);
		assertNotEquals(0, device.getId());
		assertEquals(createdDevice.getTemperature(), device.getTemperature());
		assertEquals("Ready", createdDevice.getStatus().getName());
	}


	@Test
	void getDeviceById_shouldReturnSameAddedDevice() {
		Device device = new Device();
		device.setPinCode("1111113");
		device.setTemperature(1);
		device.setStatus(statusRepository.getById(1));
		Device createdDevice = deviceRepository.save(device);
		Device returnedDevice = deviceRepository.getById(createdDevice.getId());
		assertNotEquals(0, returnedDevice.getId());
		assertEquals(returnedDevice.getPinCode(), device.getPinCode());
		assertEquals(returnedDevice.getTemperature(), device.getTemperature());
		assertEquals("Ready", returnedDevice.getStatus().getName());
	}
	
	@Test
	void deleteDevice_shouldReturnNumberOfDevicesReducedBy1() {
		long countBeforeDeletion = deviceRepository.count();
		deviceRepository.deleteById(1L);
		long countAfterDeletion = deviceRepository.count();
		assertEquals(countBeforeDeletion-1,countAfterDeletion);
	}
	
	@Test
	void deleteAllDevice_shouldReturn0Devices() {
		deviceRepository.deleteAll();
		long countAfterDeletion = deviceRepository.count();
		assertEquals(0,countAfterDeletion);
	}
	

}
