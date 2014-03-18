package com.proj.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.proj.database.columns.NotificationColumns;
import com.proj.model.*;

public class NotificationHandler {
	private Model model;
	
	public NotificationHandler(Model model) {
		this.model = model;
	}
	
	public void mapNotification(UUID appointmentId, int id, Notification notification, PreparedStatement statement) throws SQLException {
		statement.setInt(1, id);
		statement.setTimestamp(2, DateConverter.getSqlTimestamp(notification.getTimestamp()));
		statement.setString(3, notification.getText());
		statement.setString(4, appointmentId.toString());
		
	}
	
	
	/**
	 * Loads and adds all notifications from database to model.
	 */
	public void instantiateNotifications(ResultSet resultSet) throws SQLException {

		
		while (resultSet.next()) {
			// Get appointment
			String appointmentId = resultSet.getString(NotificationColumns.Appointment.colNr());
			
			Appointment appointment = model.getAppointment(UUID.fromString(appointmentId));
			if (appointment == null) {
				System.out.println("Database: Could not find appointment " + appointmentId);
				continue;
			}
			
			// Instantiate notification
			Notification notification = new Notification(
					resultSet.getString(NotificationColumns.Text.colNr()),
					resultSet.getTimestamp(NotificationColumns.CreationTime.colNr())
				);
			
			// Add notification to appointmnet
			appointment.addNotification(notification);
		}
	}

}
