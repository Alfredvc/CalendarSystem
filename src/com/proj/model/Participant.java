package com.proj.model;

import java.io.Serializable;

public class Participant implements Serializable{
	private Employee employee;
	private Status status;
	private boolean alarm;
	private boolean hidden;
	
	public Participant(Employee employee) {
		this.employee = employee;
	}
	
	public Participant(Employee employee, Status status, boolean alarm, boolean hidden) {
		this(employee);
		this.status = status;
		this.alarm = alarm;
		this.hidden = hidden;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public boolean isAlarm() {
		return alarm;
	}
	
	public void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	

}
