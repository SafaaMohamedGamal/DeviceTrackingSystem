package com.iot.device_tracking_system.model;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {

	@Min(value = -1, message = "Device temperature must be between 0 to 10 C if configured and -1 if not")
	@Max(value = 10, message = "Device temperature must be between 0 to 10 C if configured and -1 if not")
	private Integer temperature;

	@NotNull(message = "statusId is required")
	@Min(value = 1, message = "Device status must be Ready(1) or Active(2)")
	@Max(value = 2, message = "Device status must be Ready(1) or Active(2)")
	private Integer statusId;

	@NotBlank(message = "pincode is required")
    @Size(max = 7, message = "Device pinCode should have seven-digit characters")
	private String pinCode;

	
}
