package com.iot.DeviceTrackingSystem.model;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DeviceDto {

	private int temprature;

	private int statusId;

	@NotEmpty
    @Size(min = 7, message = "Device pinCode should have seven-digit characters")
	private String pinCode;

	public int getTemprature() {
		return temprature;
	}

	public void setTemprature(int temprature) {
		this.temprature = temprature;
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
	
	
}
