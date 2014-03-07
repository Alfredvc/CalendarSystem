package com.proj.model;

public class MeetingRoom {
	private String roomNr;
	private int capacity;
	
	public MeetingRoom(String roomNr, int capacity) {
		this.roomNr = roomNr;
		this.capacity = capacity;
	}
	
	public String getRoomNr() {
		return roomNr;
	}
	
	public int getCapacity() {
		return capacity;
	}
}
