package com.iot.DeviceTrackingSystem.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class DeviceResponse {

    private long id;

	private int temprature;

	private String statusName;

	private String pinCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTemprature() {
		return temprature;
	}

	public void setTemprature(int temprature) {
		this.temprature = temprature;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	
}
