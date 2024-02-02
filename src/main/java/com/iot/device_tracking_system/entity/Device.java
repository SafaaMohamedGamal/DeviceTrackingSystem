package com.iot.device_tracking_system.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot.device_tracking_system.model.Status;


@Entity(name = "device")
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5495824783659595151L;


	public Device() {
		super();
	}

	public Device(long id, int temperature, String pinCode, Status status) {
		super();
		this.id = id;
		this.temperature = temperature;
		this.pinCode = pinCode;
		this.status = status;
	}

	public Device(int temperature, String pinCode, Status status) {
		super();
		this.temperature = temperature;
		this.pinCode = pinCode;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "device_id")
    @TableGenerator(name = "device_id", table = "sequence", pkColumnName = "seq_name", valueColumnName = "seq_value",
        pkColumnValue="device_id", initialValue=1, allocationSize = 1)
	private long id;

	@Column
	private int temperature;

	@Column(unique = true, length = 7, nullable = false)
	private String pinCode;
	
	@JoinColumn(name = "status_id",nullable = false)
	@ManyToOne
	private Status status;
	
	@JsonIgnore
	@Column(name = "status_id", insertable = false, updatable = false)
	private int statusId;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	
	
}
