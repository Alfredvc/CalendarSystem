package com.proj.model;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
public class Notification implements Serializable {
	private String text;
	private Date timestamp;
	private Appointment appointment;
	private PropertyChangeSupport pcs= new PropertyChangeSupport(this);
	public Notification(String text, Date timestamp) {
		if (text == null) {
			throw new NullPointerException("Text cannot be null");
		}
		if (timestamp == null) {
			throw new NullPointerException("The timestamp cannot be null.");
		}

		this.text = text;
		this.timestamp = timestamp;
	}
	
	public Notification(String text) {
		this(text, Calendar.getInstance().getTime());
	}
	
	void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	
	public String getText() {
		return text;
	}
	
	public Date getTimestamp() {
		return (Date) timestamp.clone();
	}

    @Override
    public boolean equals(Object other){
        return (other instanceof Notification && ((Notification)other).getText().equals(this.text)
        && ((Notification)other).getTimestamp().equals(timestamp) &&
                ((Notification)other).getAppointment().equals(appointment));
    }

    @Override
    public int hashCode(){
        return text != null ?text.hashCode() : 0;
    }

}
