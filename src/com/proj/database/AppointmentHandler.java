package com.proj.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import java.sql.PreparedStatement;
import com.proj.database.columns.AppointmentColumns;
import com.proj.model.*;

public class AppointmentHandler {
	private Model model;
	
	public AppointmentHandler(Model model) {
		this.model = model;
	}
	
	public int instantiateAppointments(ResultSet resultSet) throws SQLException {
		int added = 0;
		
		while (resultSet.next()) {
			// Get meeting leader!
			//TODO: Database cannot reflect the "participant" part, only the employee!
			String email = resultSet.getString(AppointmentColumns.Leader.colNr());
			Employee employee = model.getEmployee(email);
			
			if (employee == null) {
				System.out.println("Database: Could not find employee " + email);
				continue;
			}
			
			InternalParticipant leader = new InternalParticipant(employee);

			
			// Instantiate appointment
			Appointment appointment = new Appointment(
					UUID.fromString(resultSet.getString(AppointmentColumns.Id.colNr())),
					leader,
					DateConverter.getDate(resultSet.getTimestamp(AppointmentColumns.Date.colNr())),
					resultSet.getInt(AppointmentColumns.Duration.colNr()),
					resultSet.getString(AppointmentColumns.Description.colNr())
				);
			
			// Set correct meeting room or location
			String roomNr = resultSet.getString(AppointmentColumns.MeetingRoom.colNr());	
			if (roomNr != null) {
				MeetingRoom meetingRoom = model.getMeetingRoom(roomNr);
				if (meetingRoom != null) {
					appointment.setMeetingRoom(model.getMeetingRoom(roomNr));
				} else {
					System.out.println("Database: Could not find meetingroom " + roomNr);
				}
			} else {
				appointment.setLocation(resultSet.getString(AppointmentColumns.Location.colNr()));
			}
			
			// Add our new appointment!
			model.addAppointment(appointment);
			added++;
		}
		
		return added;
	}
	
	
	public void mapAppointment(Appointment appointment, PreparedStatement statement) throws SQLException {
		statement.setString(AppointmentColumns.Id.colNr(), appointment.getId().toString());
		statement.setString(AppointmentColumns.Description.colNr(), appointment.getDescription());
		statement.setTimestamp(AppointmentColumns.Date.colNr(), DateConverter.getSqlTimestamp(appointment.getStartTime()));
		statement.setInt(AppointmentColumns.Duration.colNr(), appointment.getDuration());
		statement.setString(AppointmentColumns.Location.colNr(), appointment.getLocation());
        statement.setString(AppointmentColumns.MeetingRoom.colNr(), appointment.getMeetingRoom() == null
                ? null : appointment.getMeetingRoom().getRoomNr());
		statement.setString(AppointmentColumns.Leader.colNr(), appointment.getLeader().getEmployee().getEmail());
	}
	

}
