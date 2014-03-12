package com.proj.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

public class Appointment implements Serializable{

	private UUID id;
	
	private String
			description,
			location;
	
	private Date
			startTime,
			endTime;

	private ArrayList<Participant> participants = new ArrayList();
	private Participant leader;
	private ArrayList<Notification> notifications = new ArrayList();
	private MeetingRoom meetingRoom;
	
	
	public Appointment(Participant leader,Date startTime, Date endTime, String location ){   //burde description vere obligatorisk??
		this.setLeader(leader);
		this.setEndTime(endTime);
		this.setStartTime(startTime);
		this.setLocation(location);
        this.description = null;
        this.id = UUID.randomUUID();
        this.meetingRoom = null;
        participants = new ArrayList<Participant>();
        notifications = new ArrayList<Notification>();
	}
	
	public Appointment(Participant leader,Date startTime, Date endTime, MeetingRoom meetingRoom ){   //burde description vere obligatorisk??
		this.setLeader(leader);
		this.setEndTime(endTime);
		this.setStartTime(startTime);
		this.setMeetingRoom(meetingRoom);
        this.location = null;
        this.description = null;
        this.id = UUID.randomUUID();
        participants = new ArrayList<Participant>();
        notifications = new ArrayList<Notification>();
	}
	
	
	public UUID getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Participant[] getParticipants() {
		return (Participant[]) participants.toArray();
	}
	
	public void addParticipant(Participant participant) {
		participants.add(participant);
	}
	
	public void removeParticipant(Participant participant){
		if(participant.equals(this.leader)==false){ 		//passer på at møtelederen ikke kan slettes
			participants.remove(participant);
		}

	}
	
	public Participant getLeader() {
		return leader;
	}
	
	public void setLeader(Participant leader) {
		this.leader = leader;
	}
	
	public Notification[] getNotifications() {
		return (Notification[]) notifications.toArray();
	}
	
	public void addNotification(Notification notification) {
		notifications.add(notification);
	}
	
	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}
	
	public void setMeetingRoom(MeetingRoom meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

    @Override
    public String toString(){
        return "Appointment " + id + ", " + description;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Appointment)) return false;
        return this.id.equals(((Appointment)other).getId());
    }

}
