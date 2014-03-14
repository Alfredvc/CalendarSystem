package com.proj.model;

import java.util.Date;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class Appointment implements Serializable{

	private final UUID id;
	
	
	private String
			description,
			location;
	
	private Date
			startTime,
			endTime;

	private ArrayList<Participant> participants = new ArrayList<Participant>();
	private InternalParticipant leader;
	private ArrayList<Notification> notifications = new ArrayList<>();
	private MeetingRoom meetingRoom;
	private PropertyChangeSupport pcs= new PropertyChangeSupport(this);

	
	public Appointment(UUID id, InternalParticipant leader,Date startTime){
		this.id = id;
		this.setLeader(leader);
		this.setStartTime(startTime);
		this.setLocation(location);
        this.description = null;
        this.meetingRoom = null;
        participants = new ArrayList<Participant>();
        notifications = new ArrayList<Notification>();
	}
	
	public Appointment(InternalParticipant leader, Date startTime, Date endTime) {
		this(UUID.randomUUID(), leader, startTime);
		this.setEndTime(endTime);
	}
	
	public Appointment(InternalParticipant leader,Date startTime, Date endTime, String location ) {
		this(leader, startTime, endTime);
		this.setLocation(location);
	}
	
	public Appointment(InternalParticipant leader,Date startTime, Date endTime, MeetingRoom meetingRoom ) {
		this(leader, startTime, endTime);
		this.setMeetingRoom(meetingRoom);
	}
	
	public Appointment(UUID id, InternalParticipant leader, Date startTime, int duration, String description) {
		this(id, leader, startTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		calendar.add(Calendar.MINUTE, duration);
		this.setEndTime(calendar.getTime());
		this.setDescription(description);
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
	
	public void setEndTime(java.util.Date date) {
		Date oldValue=this.endTime;
		this.endTime = date;
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
	
	public InternalParticipant getLeader() {
		return leader;
	}
	
	public void setLeader(InternalParticipant leader) {
		this.leader = leader;
	}
	
	public Notification[] getNotifications() {
		return (Notification[]) notifications.toArray(new Notification[notifications.size()]);
	}
	
	public void addNotification(Notification notification) {
		int index=notifications.size();
		notifications.add(notification);
		pcs.fireIndexedPropertyChange("notifications", index-1, null, notification);
	}
	
	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}
	
	public void setMeetingRoom(MeetingRoom meetingRoom) {
		MeetingRoom oldValue=this.meetingRoom;
		this.meetingRoom = meetingRoom;
		pcs.firePropertyChange("meetingroom", oldValue, this.meetingRoom);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public int getDuration() {
		//TODO: Implement this one! (needed by db)
		return 10;
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
    
    @Override
    public int hashCode(){
    	return this.endTime.getMinutes();
    }
}
