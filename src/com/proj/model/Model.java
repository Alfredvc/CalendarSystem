package com.proj.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class Model {
	private HashMap<UUID,Appointment> appointments;
	private HashMap <String, Employee> employees;
	private HashMap <String, MeetingRoom> meetingRooms;
	private ArrayList <Group> groups;
	
	
	
	
	public void deleteAppointment(UUID id){                //her staar det int i klassediagrammet, men det er vel UUID vi gikk for??
		this.appointments.remove(id);
	}
	


	public void addAppointment(Appointment app){this.appointments.put(app.getId(), app);} // Naar en appointment addes, skal den først addes i modellaget for den gaar til databasen??


	
	
	public Appointment[]  getAppointments() {
		return (Appointment[]) appointments.values().toArray();
	}

	public void setAppointments(Appointment [] apps) {
		for(int i=0; i<apps.length; i++){
			this.addAppointment(apps[i]);
			}
	}
	
	public Appointment getAppointment(UUID id) {
		return appointments.get(id);
	}
	
	public void addMeetingRoom(MeetingRoom meetingRoom) {
		meetingRooms.put(meetingRoom.getRoomNr(), meetingRoom);
	}
	
	public MeetingRoom getMeetingRoom(String roomNr) {
		return meetingRooms.get(roomNr);

	}
	public MeetingRoom[] getMeetingRooms(){
		return this.meetingRooms.values().toArray((new MeetingRoom[meetingRooms.values().size()]));
		
	}

	public Employee[] getEmployees() {
		return (Employee[]) employees.values().toArray(new Employee[employees.size()]);
	}
	
	public Employee getEmployee(String email) {
		return employees.get(email);
	}

	public void addEmployee(Employee employee) {
		employees.put(employee.getEmail(), employee);
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}
	
	public void addGroup(Group group) {
		groups.add(group);
	}
	
	public ArrayList<MeetingRoom> getFreeMeetingRooms(Date startTime, Date endTime){
		ArrayList<MeetingRoom> freeRooms= new ArrayList();
		for(MeetingRoom m: this.getMeetingRooms()){
			freeRooms.add(m);
		}             
		for(UUID key: appointments.keySet()){
			Appointment app=appointments.get(key);
			if((app.getStartTime().before(endTime) && app.getEndTime().after(endTime)) || (app.getStartTime().before(startTime) && app.getEndTime().after(startTime))){
				
				freeRooms.remove(app.getMeetingRoom());
			};
		}
		return freeRooms;
	}
}
