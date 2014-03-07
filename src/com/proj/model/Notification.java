package com.proj.model;

import java.sql.Date;

public class Notification {
	private String text;
	private Date timestamp;
	
	public Notification(String text) {
		this.text = text;
		//this.date= ??; TODO
	}
	
	public String getText() {
		return text;
	}
	
	public Date getTimestamp() {
		return (Date) timestamp.clone();
	}
}
