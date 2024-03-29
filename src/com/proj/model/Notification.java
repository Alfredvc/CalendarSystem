package com.proj.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Notification implements Serializable {
	private String text;
	private Date timestamp;
	private Appointment appointment;
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
	
	public  boolean isRelevantFor(Employee employee) {
		if (appointment == null) {
			return true;
		}

		return appointment.involves(employee);
	}

    @Override
    public boolean equals(Object other){
        return (other instanceof Notification && ((Notification)other).getText().equals(this.text)
        && ((Notification)other).getTimestamp().equals(timestamp));
    }

    @Override
    public int hashCode(){
        return text != null ?text.hashCode() : 0;
    }

}
