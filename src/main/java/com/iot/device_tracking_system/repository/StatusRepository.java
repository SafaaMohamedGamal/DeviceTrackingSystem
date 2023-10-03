package com.iot.device_tracking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iot.device_tracking_system.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

	public Status getById(int id);
}
