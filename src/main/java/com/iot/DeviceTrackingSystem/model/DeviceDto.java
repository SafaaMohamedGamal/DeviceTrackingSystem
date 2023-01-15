package com.iot.DeviceTrackingSystem.model;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeviceDto {

	@Min(value = -1, message = "Device temprature must be between 0 to 10 C if configured and -1 if not")
	@Max(value = 10, message = "Device temprature must be between 0 to 10 C if configured and -1 if not")
	private int temprature;

	@NotNull(message = "statusId is required")
	@Min(value = 1, message = "Device status must be Ready(1) or Active(2)")
	@Max(value = 2, message = "Device status must be Ready(1) or Active(2)")
	private Integer statusId;

	@NotBlank(message = "pincode is required")
    @Size(max = 7, message = "Device pinCode should have seven-digit characters")
	private String pinCode;

	public int getTemprature() {
		return temprature;
	}

	public void setTemprature(int temprature) {
		this.temprature = temprature;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceDto other = (DeviceDto) obj;
		return Objects.equals(statusId, other.statusId)
				&& Objects.equals(pinCode, other.pinCode)
				&& Objects.equals(temprature, other.temprature);
	}
	
	
}
