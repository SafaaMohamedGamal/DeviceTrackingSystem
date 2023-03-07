package com.iot.DeviceTrackingSystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeviceTrackingSystemApplication implements CommandLineRunner{

    private static final Logger LOGGER = LogManager.getLogger(DeviceTrackingSystemApplication.class);
	  
	public static void main(String[] args) {
		SpringApplication.run(DeviceTrackingSystemApplication.class, args);

		LOGGER.debug("This is a debug message");
		LOGGER.info("This is an info message");
		LOGGER.warn("This is a warn message");
		LOGGER.error("This is an error message");
		
	}

	@Value(value = "${spring.datasource.url}")
	String dbName;
	
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("******************"+dbName+"************************");
	}
}
