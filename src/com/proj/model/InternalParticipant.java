package com.proj.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InternalParticipant  implements Participant{
	private Employee employee;
	private Status status;
	private boolean alarm;
	private boolean hidden;
	private PropertyChangeSupport pcs= new PropertyChangeSupport(this);
	
	public InternalParticipant(Employee employee) {
		this.employee = employee;
	}

	public InternalParticipant(Employee employee, Status status, boolean alarm, boolean hidden) {
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
		Status oldValue=this.status;
		this.status = status;
		pcs.firePropertyChange("status", oldValue, this.status);

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
		boolean oldValue=this.hidden;
		this.hidden = hidden;
		pcs.firePropertyChange("hidden", oldValue, this.hidden);

	}

	@Override
	public String getDisplayName() {
		return employee.getName();
	}
	
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	

}
