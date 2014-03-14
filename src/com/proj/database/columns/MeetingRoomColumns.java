package com.proj.database.columns;

public enum MeetingRoomColumns implements Columns {
	RoomNumber,
	Name,
	Capacity,
	Notes;
	
	public int colNr() {
		return this.ordinal() + 1;
	}

}
