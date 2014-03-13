package com.proj.database;

import com.proj.model.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.sql.Timestamp;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	 * Fetches all data from database and adds it to the model
	 */
	public boolean load(Model model) {
		if (connection == null) {
			throw new IllegalStateException("No database connection present!");
		}
		
		try {
			// Load stuff
			loadEmployees(model);
			loadGroups(model);
			loadMeetingRooms(model);			
			loadAppointments(model);
			loadNotifications(model);
			loadInternalParticipants(model);
			loadExternalParticipants(model);
			
		} catch (SQLException e) {
			System.err.println("Could not load model: " + e.getMessage());
		}
		
		
		
		return false;
	}
	
	
	/**
	 * Loads and adds all appointments from db to model
	 * @param model
	 * @throws SQLException
	 */
	private void loadAppointments(Model model) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				"SELECT * FROM `Appointment`;"
			).executeQuery();
		
		while (resultSet.next()) {
			// Get meeting leader!
			//TODO: Database cannot reflect the "participant" part, only the employee!
			InternalParticipant leader = new InternalParticipant(
					model.getEmployee(resultSet.getString(AppointmentColumns.Leader.colNr()))
				);
			
			// Instantiate appointment
			Appointment appointment = new Appointment(
					UUID.fromString(resultSet.getString(AppointmentColumns.Id.colNr())),
					leader,
					getDate(resultSet.getTimestamp(AppointmentColumns.Date.colNr())),
					resultSet.getInt(AppointmentColumns.Duration.colNr()),
					resultSet.getString(AppointmentColumns.Description.colNr())
				);
			
			// Set correct meeting room or location
			String roomNr = resultSet.getString(AppointmentColumns.MeetingRoom.colNr());	
			if (roomNr != null) {
				appointment.setMeetingRoom(model.getMeetingRoom(roomNr));
			} else {
				appointment.setLocation(resultSet.getString(AppointmentColumns.Location.colNr()));
			}
			
			// Add our new appointment!
			model.addAppointment(appointment);
		}
	}
	
	
	/**
	 * Loads and adds all notifications from database to model.
	 */
	private void loadNotifications(Model model) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				"SELECT * FROM `Notification`;"
			).executeQuery();
		
		while (resultSet.next()) {
			// Instantiate notification
			Notification notification = new Notification(
					resultSet.getString(NotificationColumns.Text.colNr())
					//TODO: Creation time!
				);
			
			// Get appointment id
			UUID appointmentId = UUID.fromString(
					resultSet.getString(NotificationColumns.Appointment.colNr())
				);
			
			// Add notification to appointmnet
			model.getAppointment(appointmentId).addNotification(notification);
		}
	}
	
	
	/**
	 * Loads all employees from database and adds them to the model.
	 */
	private void loadEmployees(Model model) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				"SELECT * FROM `Employee`;"
			).executeQuery();
		
		while (resultSet.next()) {
			// Instantiate
			Employee employee = new Employee(
					resultSet.getString(EmployeeColumns.Email.colNr()),
					resultSet.getString(EmployeeColumns.Name.colNr()),
					resultSet.getInt(EmployeeColumns.Telephone.colNr())
				);
			
			// Add to model
			model.addEmployee(employee);
		}
	}
	
	
	/**
	 * Loads all groups and their employees from database and adds to model.
	 */
	private void loadGroups(Model model) throws SQLException {
		// Handle group names first
		ResultSet groupResultSet = connection.prepareStatement(
				"SELECT * FROM `Group`;"
			).executeQuery();
		
		HashMap<Integer, Group> groups = new HashMap<>();
		
		while (groupResultSet.next()) {
			String name = groupResultSet.getString(GroupColumns.Name.colNr());
			int groupId = groupResultSet.getInt(GroupColumns.Id.colNr());
			
			groups.put(new Integer(groupId), new Group(name));
		}
		
		// Handle members of groups
		ResultSet memberpResultSet = connection.prepareStatement(
				"SELECT * FROM `Member_of`;"
			).executeQuery();
		
		while (memberpResultSet.next()) {
			// Get relevant employee
			Employee employee = model.getEmployee(
					memberpResultSet.getString(MemberOfColumns.EmployeeEmail.colNr())
				);
			
			// Find group and add employee
			int groupId = memberpResultSet.getInt(MemberOfColumns.GroupId.colNr());
			groups.get(new Integer(groupId)).addEmployee(employee);
		}
		
		// Add groups to model
		for (Group group : groups.values()) {
			model.addGroup(group);
		}
	}
	
	
	/**
	 * Loads all meeting rooms from database and add them to the model
	 */
	private void loadMeetingRooms(Model model) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				"SELECT * FROM `MeetingRoom`;"
			).executeQuery();
		
		while (resultSet.next()) {
			// Instantiate
			MeetingRoom meetingRoom = new MeetingRoom(
					resultSet.getString(MeetingRoomColumns.RoomNumber.colNr()),
					resultSet.getInt(MeetingRoomColumns.Capacity.colNr()),
					resultSet.getString(MeetingRoomColumns.Name.colNr()),
					resultSet.getString(MeetingRoomColumns.Notes.colNr())
				);
			
			// Add to model
			model.addMeetingRoom(meetingRoom);
		}
	}
	
	
	/**
	 * Load participants and add them to the appointments in the model.
	 */
	private void loadInternalParticipants(Model model) throws SQLException {
		ResultSet resultSet = connection.prepareStatement(
				"SELECT * FROM `Invited_to`;"
			).executeQuery();
		
		while (resultSet.next()) {
			Employee employee = model.getEmployee(
					resultSet.getString(InvitedToColumns.EmployeeEmail.colNr())
				);
			
			String uuid = resultSet.getString(InvitedToColumns.AppointmentId.colNr());

			
			Participant participant = new InternalParticipant(
					employee,
					Status.valueOf(resultSet.getString(InvitedToColumns.Attending.colNr())),
					resultSet.getBoolean(InvitedToColumns.Alarm.colNr()),
					resultSet.getBoolean(InvitedToColumns.Hidden.colNr())
				);

			model.getAppointment(
					UUID.fromString(uuid)
				).addParticipant(participant);
		}
	}
	
	private void loadExternalParticipants(Model model) {
		//TODO!		
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

		for (Participant participant : appointment.getParticipants()) {
			if (participant instanceof InternalParticipant) {
				InternalParticipant p = (InternalParticipant) participant;
				// Copy parameters for query
				insertParticipantStatement.setString(1, p.getEmployee().getEmail());
				insertParticipantStatement.setString(2, appointment.getId().toString());
				insertParticipantStatement.setBoolean(3, p.isAlarm());
				insertParticipantStatement.setBoolean(4, p.isHidden());
				insertParticipantStatement.setString(5, p.getStatus().name());
				
				// Run query
				successful &= insertParticipantStatement.execute();
			} else if (participant instanceof ExternalParticipant) {
				//TODO!				
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
	 * Converts a java.sql.Timestamp to a java.util.Date
	 */
	private Date getDate(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		return calendar.getTime();
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
			statement.setString(AppointmentColumns.Id.colNr(), appointment.getId().toString());
			statement.setString(AppointmentColumns.Description.colNr(), appointment.getDescription());
			statement.setTimestamp(AppointmentColumns.Date.colNr(), getSqlTimestamp(appointment.getStartTime()));
			statement.setInt(AppointmentColumns.Duration.colNr(), appointment.getDuration());
			statement.setString(AppointmentColumns.Location.colNr(), appointment.getLocation());
			statement.setString(AppointmentColumns.MeetingRoom.colNr(), appointment.getMeetingRoom().getRoomNr());
			statement.setString(AppointmentColumns.Leader.colNr(), appointment.getLeader().getEmployee().getEmail());

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
		Model model = new Model();
		Database database = new Database();
		database.load(model);
		
		// Print debug data for model
		for (Appointment a : model.getAppointments()){
			System.out.println("\n" + a.getId());
			System.out.println(a.getDescription());
			System.out.println(a.getLeader());
		}

		for (Employee e : model.getEmployees()) {
			System.out.println("\n" + e.getEmail());
			System.out.println(e.getName());
		}
	}
}
