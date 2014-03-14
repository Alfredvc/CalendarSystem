package com.proj.database;

import com.proj.database.columns.ExternalParticipantColumns;
import com.proj.database.columns.InvitedToColumns;
import com.proj.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.proj.model.Employee;
import com.proj.model.ExternalParticipant;
import com.proj.model.InternalParticipant;
import com.proj.model.Model;
import com.proj.model.Participant;
import com.proj.model.Status;

public class ParticipantHandler {
	private Model model;
	
	public ParticipantHandler(Model model) {
		this.model = model;
	}
	
	/**
	 * Adds all the participants in the result set to the model.
	 * @param model
	 * @param resultSet ResultSet with the columns matching InvitedToColumns.
	 * @return Number of participants added.
	 * @throws SQLException
	 */
	public int instantiateInternalParticipants(ResultSet resultSet) throws SQLException {
		int added = 0;

		while (resultSet.next()) {
			
			// Fetch the employee
			Employee employee = model.getEmployee(
					resultSet.getString(InvitedToColumns.EmployeeEmail.colNr())
				);
			
			// Instantiate the participant
			Participant participant = new InternalParticipant(
					employee,
					Status.valueOf(resultSet.getString(InvitedToColumns.Attending.colNr())),
					resultSet.getBoolean(InvitedToColumns.Alarm.colNr()),
					resultSet.getBoolean(InvitedToColumns.Hidden.colNr())
				);

			// Add participant to correct appointment
			String uuid = resultSet.getString(InvitedToColumns.AppointmentId.colNr());
			added += addParticipant(uuid, participant);
		}
		
		return added;
	}
	
	
	/**
	 * Load participants and add them to the appointments in the model.
	 */
	public int instantiateExternalParticipants(ResultSet resultSet) throws SQLException {
		int added = 0;
		
		while (resultSet.next()) {
			
			ExternalParticipant participant = new ExternalParticipant(
					resultSet.getString(ExternalParticipantColumns.Email.colNr())
				);
			
			String uuid = resultSet.getString(ExternalParticipantColumns.AppointmentId.colNr());
			added += addParticipant(uuid, participant);
			
		}
		//TODO!
		return added;
	}
	
	
	/**
	 * Adds a participant to an appointment identified by uuid (as string)
	 */
	private int addParticipant(String uuid, Participant participant) {
		Appointment appointment =  model.getAppointment(
				UUID.fromString(uuid)
			);

		if (appointment == null) {
			System.out.println("Database: Could not find appointment " + uuid);
			return 0;
		}

		appointment.addParticipant(participant);
		return 1;
	}
	
	public void mapInternalParticipant(UUID appointmentId, InternalParticipant participant, PreparedStatement statement) throws SQLException {
		statement.setString(1, participant.getEmployee().getEmail());
		statement.setString(2, appointmentId.toString());
		statement.setBoolean(3, participant.isAlarm());
		statement.setBoolean(4, participant.isHidden());
		statement.setString(5, participant.getStatus().name());
	}

	public void mapExternalParticipant(UUID appointmentId, ExternalParticipant participant, PreparedStatement statement) throws SQLException {
		statement.setString(ExternalParticipantColumns.Email.colNr(), participant.getEmail());
		statement.setString(ExternalParticipantColumns.AppointmentId.colNr(), appointmentId.toString());
		
	}
}
