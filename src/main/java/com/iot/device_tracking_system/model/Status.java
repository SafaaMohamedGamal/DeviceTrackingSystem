package com.iot.device_tracking_system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "status")
@Getter
@Setter
@NoArgsConstructor
public class Status implements Serializable, Cloneable {

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
