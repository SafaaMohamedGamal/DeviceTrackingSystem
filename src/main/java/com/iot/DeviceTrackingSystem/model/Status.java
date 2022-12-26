package com.iot.DeviceTrackingSystem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "status")
public class Status implements Serializable, Cloneable {

	private static final long serialVersionUID = -4448377296664511360L;

	public Status() {
	}
	public Status(int id) {
		super();
		this.id = id;
	}

	@Id
	private int id;
	
	@Column
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
