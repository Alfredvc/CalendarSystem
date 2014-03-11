package com.proj.model;

import java.util.ArrayList;
import java.util.UUID;

public class Model {
	private ArrayList <Appointment> appointments;
	private ArrayList <Employee> employees;
	private ArrayList <Group> groups;
	
	
	
	
	
	
	
	public void deleteAppointment(UUID id){                //her staar det int i klassediagrammet, men det er vel UUID vi gikk for??
		for(int i=0; i<appointments.size(); i++){
			if(appointments.get(i).getId()==id){
				appointments.remove(i);
			}
		}
	}
	
	public void changeAppointment(Appointment app){
		                            						//??	
	}

	//public void addAppointment(Appointment app){}  // Naar en appointment addes, skal den først addes i modellaget for den gaar til databasen??
	
	
	
	
	
	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<Appointment> appointments) {
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
