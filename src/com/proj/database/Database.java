package com.proj.database;

import com.proj.model.*;

import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class Database implements PresistanceBackend {
	
	private static final String CONFIG_FILE = "conf/database.conf";
	
	private Connection connection;
	private Properties props;
	
	
	public Database() {
		props = loadProperties();
		
		// Get connection string (and remove it from props)
		String connection_string = (String) props.remove("connection_string");
		if (connection_string == null) {
			System.err.println("No database connection string in config. :S");
			return;
		}

		try {
			// Connect to database
			connection = DriverManager.getConnection(connection_string, props.getProperty("username"), props.getProperty("password"));
		} catch (SQLException e) {
			System.err.println("WHAAT? Could not connect to database! o.O");
		}
		
	}
	

	/**
	 * Loads database configuration from file
	 * @return Properties from file
	 */
	private Properties loadProperties() {
		Properties props = new Properties();

		try {
			InputStream fis = new FileInputStream(CONFIG_FILE);
			props.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			System.out.println("Database config file was not found!");
		} catch (IOException e) {
			System.out.println("Could not load database config file!");
		}
		
		return props;		
	}
	
	
	/**
	 * Saves the "Invited to" relation to database.
	 * @param appointment
	 * @return Whether the operation was successful for all participants.
	 * @throws SQLException
	 */
	private boolean saveParticipants(Appointment appointment) throws SQLException {
		boolean successful = true;
		
		// This time we should not get duplicates!
		PreparedStatement insertParticipantStatement = connection.prepareStatement(
				"INSERT INTO `Invited_to` VALUES (?, ?, ?, ?, ?);"
			);
		
		for (Participant p : appointment.getParticipants()) {
			// Copy parameters for query
			insertParticipantStatement.setString(1, p.getEmployee().getEmail());
			insertParticipantStatement.setString(2, appointment.getId().toString());
			insertParticipantStatement.setBoolean(3, p.isAlarm());
			insertParticipantStatement.setBoolean(4, p.isHidden());
			insertParticipantStatement.setString(5, p.getStatus().name());
			
			// Run query
			successful &= insertParticipantStatement.execute();
		}
		
		return successful;
	}
	
	
	/**
	 * Saves the notifications related to an appointment to the database.
	 * @param appointment
	 * @return Whether the operation was successful.
	 * @throws SQLException
	 */
	private boolean saveNotifications(Appointment appointment) throws SQLException {
		boolean successful = true;
		
		// This time we should not get duplicates!
		PreparedStatement insertParticipantStatement = connection.prepareStatement(
				"INSERT INTO `Notification` VALUES (?, ?, ?, ?);"
			);
		
		// We will manually create the incrementing partial key
		int index = 1;
		
		for (Notification n : appointment.getNotifications()) {
			// Copy parameters for query
			insertParticipantStatement.setInt(1, index++);
			insertParticipantStatement.setTimestamp(2, getSqlTimestamp(n.getTimestamp()));
			insertParticipantStatement.setString(3, n.getText());
			insertParticipantStatement.setString(4, appointment.getId().toString());
			
			// Run query
			successful &= insertParticipantStatement.execute();
		}
		
		return successful;
	}
	
	
	/**
	 * Converts a java.util.Date to a java.sql.Timestamp
	 */
	private Timestamp getSqlTimestamp(Date date){
		return new Timestamp(date.getTime());
	}
	
	
	/**
	 * Saves the given appointment and all relationships to database
	 * @param appointment Appointment to save
	 * @return Whether the operation was successful or not
	 */
	public boolean save(Appointment appointment) {
		// If the init failed, there is no connection
		if (connection == null) {
			throw new IllegalStateException("No database connection present!");
		}
		
		try {
			PreparedStatement statement = connection.prepareStatement(
					"REPLACE INTO `Appointment` VALUES (?, ?, ?, ?, ?, ?, ?);"
				);
			
			// Copy arguments from  appointment
			statement.setString(1, appointment.getId().toString());
			statement.setString(2, appointment.getDescription());
			statement.setTimestamp(3, getSqlTimestamp(appointment.getStartTime()));
			statement.setInt(4, appointment.getDuration());
			statement.setString(5, appointment.getLocation());
			statement.setString(6, appointment.getMeetingRoom().getRoomNr());
			statement.setString(7, appointment.getLeader().getEmployee().getEmail());

			// Execute query and save relations
			return statement.execute() &
					saveParticipants(appointment) &
					saveNotifications(appointment);
			
		} catch (SQLException e) {
			System.err.println("Could not save appointment to database:\n" + e.getMessage());
			return false;
		}
	}
	
	
	public static void main(String[] args) {
		Database database = new Database();
		
		Participant leader = new Participant(
				new Employee("mons@company.com", "Mons Minasen", "94934455")
			);
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(2014,2,1, 14, 10, 0);
		Date startTime = calendar.getTime();
		
		calendar.set(2014, 2, 1, 14, 40, 0);
		Date endTime = calendar.getTime();
		
		Appointment a = new Appointment(
				leader,
				startTime,
				endTime,
				new MeetingRoom("R1", 200)
			);
		
		a.setDescription("Testbeskrivelse");
		a.addNotification(new Notification("Haha"));
		a.addNotification(new Notification("hnotheunt"));
		
		database.save(a);
		System.out.println("Ferdig!");

	}
}
