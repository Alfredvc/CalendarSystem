package com.proj.database.columns;

public enum InvitedToColumns implements Columns {
	EmployeeEmail,
	AppointmentId,
	Alarm,
	Hidden,
	Attending;
	
	public int colNr() {
		return this.ordinal() + 1;
	}
	
}
