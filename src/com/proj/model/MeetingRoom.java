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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingRoom that = (MeetingRoom) o;

        if (capacity != that.capacity) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (roomNr != null ? !roomNr.equals(that.roomNr) : that.roomNr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roomNr != null ? roomNr.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + capacity;
        return result;
    }
}
