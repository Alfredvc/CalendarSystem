package com.proj.model;

import java.io.Serializable;

public class MeetingRoom implements Serializable{
	private String roomNr,
			name,
			notes;
	
	private int capacity;
	
	public MeetingRoom(String roomNr, int capacity) {
		this.roomNr = roomNr;
		this.capacity = capacity;
	}

	public MeetingRoom(String roomNr, int capacity, String name, String notes) {
		this(roomNr, capacity);
		this.name = name;
		this.notes = notes;
	}

	public String getRoomNr() {
		return roomNr;
	}

	public int getCapacity() {
		return capacity;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public String getName() {
		return name;
	}
	public String toString() {
		return roomNr;
	}
}
