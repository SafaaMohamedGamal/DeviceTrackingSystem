package com.iot.DeviceTrackingSystem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.iot.DeviceTrackingSystem.model.Device;

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
		device.setTemprature(1);
		assertThrows(DataIntegrityViolationException.class, () -> {
			deviceRepository.save(device);
	    });
	}

	@Test
	void addDevice_shouldReturnDeviceWithCorrectStatus() {
		Device device = new Device();
		device.setPinCode("1111112");
		device.setTemprature(1);
		device.setStatus(statusRepository.getById(1));
		Device createdDevice = deviceRepository.save(device);
		assertNotEquals(0, device.getId());
		assertEquals(createdDevice.getTemprature(), device.getTemprature());
		assertEquals("Ready", createdDevice.getStatus().getName());
	}


	@Test
	void getDeviceById_shouldReturnSameAddedDevice() {
		Device device = new Device();
		device.setPinCode("1111113");
		device.setTemprature(1);
		device.setStatus(statusRepository.getById(1));
		Device createdDevice = deviceRepository.save(device);
		Device returnedDevice = deviceRepository.getById(createdDevice.getId());
		assertNotEquals(0, returnedDevice.getId());
		assertEquals(returnedDevice.getPinCode(), device.getPinCode());
		assertEquals(returnedDevice.getTemprature(), device.getTemprature());
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
