package com.proj.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Notification implements Serializable {
	private String text;
	private Date timestamp;
	
	public Notification(String text) {
		this.text = text;
		timestamp = Calendar.getInstance().getTime();
	}
	
	public String getText() {
		return text;
	}
	
	public Date getTimestamp() {
		return (Date) timestamp.clone();
	}
}
