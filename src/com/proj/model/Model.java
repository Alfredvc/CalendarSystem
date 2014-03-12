package com.proj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Model {
	private HashMap<UUID,Appointment> appointments = new HashMap<>();
	private HashMap<String, Employee> employees = new HashMap<>();
	private HashMap<String, MeetingRoom> meetingRooms = new HashMap<>();
	private ArrayList <Group> groups = new ArrayList<>();
	
	
	
	public void deleteAppointment(UUID id){                //her staar det int i klassediagrammet, men det er vel UUID vi gikk for??
		this.appointments.remove(id);
	}
	


	public void addAppointment(Appointment app){this.appointments.put(app.getId(), app);}  // Naar en appointment addes, skal den først addes i modellaget for den gaar til databasen??

	
	public HashMap<UUID,Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(HashMap<UUID,Appointment> appointments) {
		this.appointments = appointments;
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
	
}
