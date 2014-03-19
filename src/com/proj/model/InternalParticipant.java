package com.proj.model;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class InternalParticipant  implements Participant, Serializable{
	private Employee employee;
	private Status status;
	private boolean alarm;
	private boolean hidden;
	private PropertyChangeSupport pcs= new PropertyChangeSupport(this);
	
	public InternalParticipant(Employee employee) {
		this.employee = employee;
	}

    public InternalParticipant(InternalParticipant internalParticipant){
        this.employee = new Employee(internalParticipant.getEmployee());
        this.status = internalParticipant.getStatus();
        this.alarm = internalParticipant.isAlarm();
        this.hidden = internalParticipant.isHidden();
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

    public void updateFrom(InternalParticipant other){
        if (!(other.getEmployee().equals(employee))) throw new RuntimeException("Must be same employee");
        setStatus(other.status);
        setAlarm(other.isAlarm());
        setHidden(other.isHidden());
    }

	@Override
	public String getDisplayName() {
		return employee.getName();
	}
	public String toString() {
		return employee.getName();
	}

    @Override
    public boolean equals(Object o) {
        if (o instanceof InternalParticipant){
            if (this.employee.equals(((InternalParticipant)o).getEmployee())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return employee != null ? employee.hashCode() : 0;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	

}
