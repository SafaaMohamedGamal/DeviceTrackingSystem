package com.iot.DeviceTrackingSystem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity(name = "device")
public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5495824783659595151L;


	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "device_id")
    @TableGenerator(name = "device_id", table = "sequence", pkColumnName = "seq_name", valueColumnName = "seq_value",
        pkColumnValue="device_id", initialValue=1, allocationSize = 1)
	private long id;

	@Column
	private int temprature = -1;

	@JoinColumn(name = "status_id", insertable = false, updatable = false)
	@ManyToOne
	private Status status;
	
	@Column(name = "status_id")
	private int statusId = 1;

	@Column(unique = true, length = 7)
	private String pinCode;
	
	@Transient
	private String statusName;

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

	public String getStatusName() {
		if(status!=null) return status.getName();
		return "Ready";
	}

	
	
	
}