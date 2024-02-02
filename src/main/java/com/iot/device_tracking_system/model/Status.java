package com.iot.device_tracking_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "status")
@Getter
@Setter
@NoArgsConstructor
public class Status implements Serializable {

	@Serial
	private static final long serialVersionUID = -4448377296664511360L;
	
	public Status(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	private int id;
	
	@Column
	private String name;
	

}
