package com.iot.DeviceTrackingSystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeviceTrackingSystemApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DeviceTrackingSystemApplication.class, args);
	}

	@Value(value = "${spring.datasource.url}")
	String dbName;
	
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("******************"+dbName+"************************");
	}
}
