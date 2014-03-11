package com.proj.model;

import java.util.Date;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.UUID;

public class Appointment {

	private final UUID id;
	
	
	private String
			description,
			location;
	
	private Date
			startTime,
			endTime;
	
	private ArrayList<Participant> participants = new ArrayList<>();
	private Participant leader;
	private ArrayList<Notification> notifications = new ArrayList<>();
	private MeetingRoom meetingRoom;
	private PropertyChangeSupport pcs= new PropertyChangeSupport(this);
	
	
	public Appointment(Participant leader,Date startTime, Date endTime, String location ){   //burde description vere obligatorisk??
		this.id=UUID.randomUUID();
		this.setLeader(leader);
		this.setEndTime(endTime);
		this.setStartTime(startTime);
		this.setLocation(location);
	}
	
	public Appointment(Participant leader,Date startTime, Date endTime, MeetingRoom meetingRoom ){   //burde description vere obligatorisk??
		this.id=UUID.randomUUID();
		this.setLeader(leader);
		this.setEndTime(endTime);
		this.setStartTime(startTime);
		this.setMeetingRoom(meetingRoom);
		
	}
	
	
	public UUID getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		String oldValue=this.description;
		this.description = description;
		pcs.firePropertyChange("description",oldValue, this.description);
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		String oldValue=this.location;
		this.location = location;
		pcs.firePropertyChange("location",oldValue, this.location);
	
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		Date oldValue=this.startTime;
		this.startTime = startTime;
		pcs.firePropertyChange("startTime",oldValue, this.startTime);
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		Date oldValue=this.endTime;
		this.endTime = endTime;
		pcs.firePropertyChange("endTime",oldValue, this.endTime);
	}
	
	public Participant[] getParticipants() {
		return (Participant[]) participants.toArray(new Participant[participants.size()]);
	}
	
	public void addParticipant(Participant participant) {
		participants.add(participant);
		int index=participants.size();
		
		pcs.fireIndexedPropertyChange("participants", index-1, null, participant);
	}
	
	public void removeParticipant(Participant participant){
		if(participant.equals(this.leader)==false){ 		//passer på at møtelederen ikke kan slettes
			int index=participants.indexOf(participant);
			Participant oldValue=participants.remove(index);
			pcs.fireIndexedPropertyChange("participants", index, oldValue, null);
		}
		

	}
	
	public Participant getLeader() {
		return leader;
	}
	
	public void setLeader(Participant leader) {
		this.leader = leader;
	}
	
	public Notification[] getNotifications() {
		return (Notification[]) notifications.toArray(new Notification[notifications.size()]);
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
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public int getDuration() {
		//TODO: Implement this one!
		return 10;
	}
}
