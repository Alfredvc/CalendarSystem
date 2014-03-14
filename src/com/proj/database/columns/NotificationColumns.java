package com.proj.database.columns;

public enum NotificationColumns implements Columns {
	Id,
	CreationTime,
	Text,
	Appointment;
	
	public int colNr() {
		return this.ordinal() + 1;
	}

}
