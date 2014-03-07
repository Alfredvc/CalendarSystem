package com.proj.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

public class Appointment {

	private UUID id;
	
	private String
			description,
			location;
	
	private Date
			startTime,
			EndTime;
	
	private ArrayList<Participant> participants = new ArrayList<>();
	private Participant leader;
	private ArrayList<Notification> notifications = new ArrayList<>();
	private MeetingRoom meetingRoom;
	
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
		return EndTime;
	}
	
	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}
	
	public Participant[] getParticipants() {
		return (Participant[]) participants.toArray();
	}
	
	public void addParticipant(Participant participant) {
		participants.add(participant);
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
}
