package com.iot.device_tracking_system.repository;

import com.iot.device_tracking_system.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest()
@ActiveProfiles("it")
class StatusRepositoryIT {

	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private DeviceRepository deviceRepository;



	@Test
	void findStatusByIdThatNotExist_shouldThrowNoSuchElementException() {
		var statusOptional = statusRepository.findById(3);
		assertThrows(NoSuchElementException.class, statusOptional::get);
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
