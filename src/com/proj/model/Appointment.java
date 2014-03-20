package com.proj.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class Appointment implements Serializable{

	private final UUID id;
	
	
	private String
			description,
			location;
	
	private Date
			startTime,
			endTime;


    public static enum Flag{
        UPDATE, DELETE;
    }

    private HashSet<Participant> participants = new HashSet<Participant>();
	private InternalParticipant leader;
	private HashSet<Notification> notifications = new HashSet<>();
    private MeetingRoom meetingRoom;
	private PropertyChangeSupport pcs= new PropertyChangeSupport(this);
    private AppointmentChangeNotifier notifier = new AppointmentChangeNotifier(this);

    public Appointment(Appointment appointment){
        this.id = appointment.getId();
        this.leader = new InternalParticipant(appointment.getLeader());
        this.participants = new HashSet<>(Arrays.asList(appointment.getParticipants()));
        this.notifications = new HashSet<>(Arrays.asList(appointment.getNotifications()));
        for (Notification n : notifications) n.setAppointment(this);
        this.startTime = new Date(appointment.getStartTime().getTime());
        this.endTime = new Date(appointment.getEndTime().getTime());
        this.description = new String(appointment.getDescription());
        if (appointment.getLocation() == null) {
            this.location = null;
            this.meetingRoom = appointment.getMeetingRoom();
        } else {
            this.meetingRoom = null;
            this.location = appointment.getLocation();
        }
    }
	
	public Appointment(UUID id, InternalParticipant leader,Date startTime){
		if (leader == null) {
			throw new NullPointerException("Leader cannot be null");
		}
		this.id = id;
		this.leader = leader;
		this.setStartTime(startTime);
		this.setLocation(location);
        addPropertyChangeListener(notifier);
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
	
	public Appointment(UUID id, InternalParticipant leader, Date startTime, long duration, String description) {
		this(id, leader, startTime);
		this.setEndTime(new Date(startTime.getTime() + duration));
		this.setDescription(description);
	}
	
	
	public UUID getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	
	/**
	 * Set the description of this appointment
	 * @param description
	 */
	public void setDescription(String description) {
		if (description != null && description.equals(this.description)) {
			return;
		}
		String oldValue = this.description;
		this.description = description;
		pcs.firePropertyChange("description",oldValue, this.description);
	}
	
	
	public String getLocation() {
		return location;
	}
	
	
	/**
	 * Set the location of this appointment
	 * @param location
	 */
	public void setLocation(String location) {
        if (location == null && this.location == null) return;

		if (location != null && meetingRoom != null) {
			throw new IllegalArgumentException("Cannot add location as this appointment already has a meeting room.");
		}
	
		String oldValue=this.location;
		this.location = location;
		pcs.firePropertyChange("location",oldValue, this.location);
	
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		if (startTime == null) {
			throw new NullPointerException("Cannot set appointment start time to null");
		}
		if (endTime != null && startTime.after(endTime)) {
			throw new IllegalArgumentException("The start time cannot be after the end time!");
		}

		Date oldValue=this.startTime;
		this.startTime = startTime;
		pcs.firePropertyChange("startTime",oldValue, this.startTime);
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		if (endTime == null) {
			throw new NullPointerException("Cannot sett appointment end time to null!");
		}
		if (startTime != null && endTime.before(startTime)) {
			throw new IllegalArgumentException("The end time cannot be before the start time!");
		}
		
		Date oldValue=this.endTime;
		this.endTime = endTime;
		pcs.firePropertyChange("endTime", oldValue, this.endTime);
	}
	
	public void setStartEndTime(Date startTime, Date endTime) {
		if (startTime == null && endTime == null) {
			throw new NullPointerException("End or start time cannot be null!");
		}
		if (startTime != null && endTime != null && startTime.after(endTime)) {
			throw new IllegalArgumentException("Start time cannot be after end time!");
		}
	}
	
	public Participant[] getParticipants() {
		return (Participant[]) participants.toArray(new Participant[participants.size()]);
	}
	

	/**
	 * Checks if this appointment in some way is relevant for the given employee
	 */
	public boolean involves(Employee employee) {
		// Is (s)he the leader?
		if (getLeader().getEmployee().equals(employee)) {
			return true;
		}
		
		// Regular participant?
		for (Participant p : participants) {
			if (p instanceof InternalParticipant &&
					((InternalParticipant) p).getEmployee().equals(employee)) {
				return true;
			}
		}
		
		// No sigar!
		return false;
	}
	
	
	public void addParticipant(Participant participant) {
		if (participants.add(participant)) {
			pcs.firePropertyChange("participants", null, participant);
		}
	}
	
	public void removeParticipant(Participant participant){
		if(participant.equals(this.leader)){ 		//passer på at møtelederen ikke kan slettes
			throw new IllegalArgumentException("Cannot delete the meeting leader from an appointment!");
		}
		
		participants.remove(participant);
		pcs.firePropertyChange("participants", participant, null);
	}
	
	public InternalParticipant getLeader() {
		return leader;
	}
	
	public Notification[] getNotifications() {
		return (Notification[]) notifications.toArray(new Notification[notifications.size()]);
	}
	
	public void addNotification(Notification notification) {
		int index=notifications.size();
		notifications.add(notification);
		notification.setAppointment(this);
		pcs.fireIndexedPropertyChange("notifications", index - 1, null, notification);
	}
	
	public MeetingRoom getMeetingRoom() {
		return meetingRoom;
	}
	
	public void setMeetingRoom(MeetingRoom meetingRoom) {
        if (this.meetingRoom == null && meetingRoom == null) return;

		if (meetingRoom != null && location != null) {
			throw new IllegalArgumentException("Cannot set meeting room as location is already set!");
		}
		
		MeetingRoom oldValue=this.meetingRoom;
		this.meetingRoom = meetingRoom;
		pcs.firePropertyChange("meetingroom", oldValue, this.meetingRoom);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

    public void addAppointmentChangeListener(AppointmentChangeSupport.AppointmentChangedListener listener){
        notifier.acs.addAppointmentChangedListener(listener);
    }

    public void removeAppointmentChangeListener(AppointmentChangeSupport.AppointmentChangedListener listener){
        notifier.acs.removeAppointmentChangedListener(listener);
    }
	
	public long getDuration() {
		return getEndTime().getTime() - getStartTime().getTime();
	}
	
	
	/**
	 * Copies all fields from the given appointment and uses the set methods so the correct events are triggered
	 * NOTE: Copies only the editable fields!!!
	 * @param appointment Appointment to copy data from!
	 */
	public void updateFrom(Appointment appointment) {
		// Notifications is a collection! Special treatment needed
		Notification[] newNotifications = appointment.getNotifications();

        notifications.addAll(Arrays.asList(newNotifications));

		// Participants
		List<Participant> newParticipants = Arrays.asList(appointment.getParticipants());
        ArrayList<Participant> toRemove = new ArrayList<>();
        for (Participant p : getParticipants()){
            if (p instanceof InternalParticipant && newParticipants.contains(p)){
                ((InternalParticipant)p).updateFrom((InternalParticipant)newParticipants.get(newParticipants.indexOf(p)));
            } else{
                toRemove.add(p);
            }
        }

        for (Participant p : newParticipants){
            if (!(participants.contains(p))) addParticipant(p);
        }
		
		// Simple values
		setDescription(appointment.getDescription());
		setStartEndTime(appointment.getStartTime(), appointment.getEndTime());
		
		// Be carefull not to set both meeting room and location at the same time
		String location = appointment.getLocation();
		if (location == null) {
			setLocation(null);
			setMeetingRoom(appointment.getMeetingRoom());
		} else {
			setMeetingRoom(null);
			setLocation(location);
		}
	}
	

    @Override
    public String toString(){
        return "Appointment " + id + ", " + description;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Appointment)) return false;
        return (this.id.equals(((Appointment)other).getId()) && this.notifications.equals(((Appointment) other).getNotifications()));
    }
    
    @Override
    public int hashCode(){
    	return this.endTime.hashCode();
    }

    private class AppointmentChangeNotifier implements PropertyChangeListener, Serializable{

        public AppointmentChangeSupport acs;


        public AppointmentChangeNotifier(Appointment appointment){
            acs = new AppointmentChangeSupport(appointment);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            acs.fireAppointmentChanged(evt);
        }
    }

}
