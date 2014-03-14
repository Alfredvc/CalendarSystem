package com.proj.database.columns;


public enum ExternalParticipantColumns implements Columns {
	Email,
	AppointmentId;
	
	public int colNr() {
		return this.ordinal() + 1;
	}

}
