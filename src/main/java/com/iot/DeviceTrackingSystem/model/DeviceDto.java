package com.iot.DeviceTrackingSystem.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DeviceDto {

	@Min(value = -1, message = "Device temprature must be between 0 to 10 C if configured and -1 if not")
	@Max(value = 10, message = "Device temprature must be between 0 to 10 C if configured and -1 if not")
	private int temprature;

	private int statusId;

	@NotBlank(message = "pincode is required")
    @Size(max = 7, message = "Device pinCode should have seven-digit characters")
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
