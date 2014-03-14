package com.proj.database.columns;

public enum AppointmentColumns implements Columns {
	Id,
	Description,
	Date,
	Duration,
	Location,
	MeetingRoom,
	Leader;
	
	public int colNr() {
		return this.ordinal() + 1;
	}
}
