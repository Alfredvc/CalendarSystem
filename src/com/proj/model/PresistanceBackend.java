package com.proj.model;

public interface PresistanceBackend {

	boolean save(Appointment appointment);
	boolean delete(Appointment appointment);
	
}
