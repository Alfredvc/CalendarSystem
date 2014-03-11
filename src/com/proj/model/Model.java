package com.proj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Model {
	private HashMap<UUID,Appointment> appointments;
	private ArrayList <Employee> employees;
	private ArrayList <Group> groups;
	
	
	
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

	public ArrayList<Employee> getEmployees() {
		return employees;
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
	
}
