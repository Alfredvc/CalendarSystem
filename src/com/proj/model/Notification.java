package com.proj.model;

<<<<<<< HEAD
import java.util.Calendar;
import java.util.Date;
=======
import java.io.Serializable;
import java.sql.Date;
>>>>>>> networking

public class Notification implements Serializable{
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
