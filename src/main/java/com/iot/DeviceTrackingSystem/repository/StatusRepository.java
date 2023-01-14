package com.iot.DeviceTrackingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iot.DeviceTrackingSystem.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

	public Status getById(int id);
}
