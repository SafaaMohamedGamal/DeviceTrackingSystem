package com.iot.DeviceTrackingSystem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.iot.DeviceTrackingSystem.model.Status;

@DataJpaTest()
class StatusRepositoryTest {

	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private DeviceRepository deviceRepository;



	@Test
	void findStatusByIdThatNotExist_shouldThrowNoSuchElementException() {
		assertThrows(NoSuchElementException.class, ()->{
				statusRepository.findById(3).get();
				});
	}
	
	@Test
	void findStatusById_shouldReturnCorrectStatus() {
		Status status = statusRepository.findById(2).get();
		assertEquals(2, status.getId());
		assertEquals("Active", status.getName());
	}


	@Test
	void findAllStatus_shouldReturn2Records() {
		List<Status> statuses = statusRepository.findAll();
		assertEquals(2, statuses.size());
		assertEquals("Ready", statuses.get(0).getName());
		assertEquals("Active", statuses.get(1).getName());
	}


	@Test
	void deleteDevice_shouldNotCascadeToDeleteStatus() {
		deviceRepository.deleteAll();
		List<Status> statuses = statusRepository.findAll();
		assertEquals(2, statuses.size());
		assertEquals("Ready", statuses.get(0).getName());
		assertEquals("Active", statuses.get(1).getName());
	}
	

}
