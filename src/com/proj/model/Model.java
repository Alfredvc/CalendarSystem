package com.proj.model;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.UUID;

public class Model {
	private HashMap<UUID,Appointment> appointments;
	private ArrayList <Employee> employees;
	private ArrayList <Group> groups;
	private ArrayList<MeetingRoom> meetingRooms;
	
	
	
	public void deleteAppointment(UUID id){                //her staar det int i klassediagrammet, men det er vel UUID vi gikk for??
		this.appointments.remove(id);
	}
	


	public void addAppointment(Appointment app){this.appointments.put(app.getId(), app);} // Naar en appointment addes, skal den først addes i modellaget for den gaar til databasen??

	
	public Appointment getAppointment(UUID id){
		return appointments.get(id);
	}
	
	
	public Appointment[]  getAppointments() {
		return (Appointment[]) appointments.values().toArray();
	}

	public void setAppointments(Appointment [] apps) {
		for(int i=0; i<apps.length; i++){
			this.addAppointment(apps[i]);
			}
	}

	public Employee[] getEmployees() {
		return (Employee[]) employees.toArray();
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}
	
	public ArrayList<MeetingRoom> getFreeMeetingRooms(Date startTime, Date endTime){
		ArrayList<MeetingRoom> freeRooms=(ArrayList<MeetingRoom>) meetingRooms.clone(); // det gjeng vel greit med en shallowcopy her?
		for(UUID key: appointments.keySet()){
			Appointment app=appointments.get(key);
			if((app.getStartTime().before(endTime) && app.getEndTime().after(endTime)) || (app.getStartTime().before(startTime) && app.getEndTime().after(startTime))){
				
				freeRooms.remove(app.getMeetingRoom());
			};
		}
		return freeRooms;
	}
}
