package com.iot.DeviceTrackingSystem.exception;

import org.springframework.http.HttpStatus;

public class DeviceAPIException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus code;

	public DeviceAPIException(HttpStatus code) {
		super();
		this.code = code;
	}

	public DeviceAPIException(String message, Throwable cause, HttpStatus code) {
		super(message, cause);
		this.code = code;
	}

	public DeviceAPIException(String message, HttpStatus code) {
		super(message);
		this.code = code;
	}

	public DeviceAPIException(Throwable cause, HttpStatus code) {
		super(cause);
		this.code = code;
	}
	
	public HttpStatus getCode() {
		return this.code;
	}
}
