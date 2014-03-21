package com.proj.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import com.proj.model.Appointment;
import com.proj.model.ExternalParticipant;
import com.proj.model.InternalParticipant;
import com.proj.model.Model;
import com.proj.model.Notification;
import com.proj.model.Participant;
import com.proj.network.Storage;


public class Database extends Storage {
	
	private static final String CONFIG_FILE = "conf/database.conf";
	
	private Connection connection;
	private Properties props;
	
	private AppointmentHandler appointmentHandler;
	private ParticipantHandler participantHandler;
	private NotificationHandler notificationHandler;
	private StaticHandler staticHandler;
    private Model model;
	
	public Database(Model model) {
        this.model = model;
		appointmentHandler = new AppointmentHandler(model);
		participantHandler = new ParticipantHandler(model);		
		notificationHandler = new NotificationHandler(model);
		staticHandler = new StaticHandler(model);
		
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
			System.err.println("WHAAT? Could not connect to database! o.O\n" + e.getMessage());
		}
		
	}
	
	/**
	 * Fetches all data from database and adds it to the model
	 */
	public boolean load() {
		if (connection == null) {
			throw new IllegalStateException("No database connection present!");
		}
		
		try {
			// Load stuff
			staticHandler.instantiateEmployees(
					connection.prepareStatement(
						"SELECT * FROM `Employee`;"
					).executeQuery()
				);
			
			staticHandler.instantiateGroups(
					connection.prepareStatement(
						"SELECT * FROM `Group`;"
					).executeQuery(),
					connection.prepareStatement(
						"SELECT * FROM `Member_of`;"
					).executeQuery()
				);
			
			staticHandler.instantiateMeetingRooms(
					connection.prepareStatement(
						"SELECT * FROM `MeetingRoom`;"
					).executeQuery()
				);
			
			appointmentHandler.instantiateAppointments(
					connection.prepareStatement(
						"SELECT * FROM `Appointment`;"
					).executeQuery()
				);	
			
			participantHandler.instantiateInternalParticipants(
					connection.prepareStatement(
						"SELECT * FROM `Invited_to`;"
					).executeQuery()
				);
			
			participantHandler.instantiateExternalParticipants(
					connection.prepareStatement(
						"SELECT * FROM `ExternalParticipant`;"
					).executeQuery()
				);
			
			notificationHandler.instantiateNotifications(
					connection.prepareStatement(
						"SELECT * FROM `Notification`;"
					).executeQuery()
				);
            model.addModelChangeListener(this);
			
		} catch (SQLException e) {
			System.err.println("Could not load model: " + e.getMessage());
			return false;
		}

		return true;
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
		PreparedStatement internalStatement = connection.prepareStatement(
				"INSERT INTO `Invited_to` VALUES (?, ?, ?, ?, ?);"
			);
		
		PreparedStatement externalStatement = connection.prepareStatement(
				"INSERT INTO `ExternalParticipant` VALUES (?, ?);"
			);

		for (Participant participant : appointment.getParticipants()) {
			if (participant instanceof InternalParticipant) {
				// Map and run query
				participantHandler.mapInternalParticipant(
						appointment.getId(),
						(InternalParticipant) participant,
						internalStatement
					);
				successful &= internalStatement.execute();
			} else if (participant instanceof ExternalParticipant) {
				participantHandler.mapExternalParticipant(
						appointment.getId(),
						(ExternalParticipant) participant,
						externalStatement
					);
			}
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
		
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO `Notification` VALUES (?, ?, ?, ?);"
			);
		
		// We will manually create the incrementing partial key
		int index = 1;
		
		for (Notification notification : appointment.getNotifications()) {
			// Map parameters and run query
			notificationHandler.mapNotification(appointment.getId(), index++, notification, statement);
			successful &= statement.execute();
		}
		
		return successful;
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
			
			appointmentHandler.mapAppointment(appointment, statement);
			
			// Execute query and save relations
			return statement.execute() &&
					saveParticipants(appointment) &&
					saveNotifications(appointment);
			
		} catch (SQLException e) {
			System.err.println("Could not save appointment to database:\n" + e.getMessage());
			return false;
		}
	}
	
	
	public boolean delete(Appointment appointment) {
		if (connection == null) {
			throw new IllegalStateException("No database connection present!");
		}
		
		try {
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM `Appointment` WHERE ID=?;"
			);
			statement.setString(1, appointment.getId().toString());
			statement.execute();
		} catch (SQLException e) {
			System.err.println("Could not delete appointment!\n" + e.getMessage());
			return false;
		}
		
		return true;
	}

    public boolean checkLogin(String username, String password){
    	try {
    		PreparedStatement statement = connection.prepareStatement("SELECT * FROM `Employee` WHERE email=? AND password=?;");
    		statement.setString(1, username);
    		statement.setString(2, password);
    		return statement.executeQuery().next();
    	} catch (SQLException e) {
    		System.err.println("Could not check username and password :'(\n" + e.getMessage());
    	}
 
    	return false;
    }
}
