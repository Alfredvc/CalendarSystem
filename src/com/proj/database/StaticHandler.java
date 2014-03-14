package com.proj.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.proj.database.columns.EmployeeColumns;
import com.proj.database.columns.GroupColumns;
import com.proj.database.columns.MeetingRoomColumns;
import com.proj.database.columns.MemberOfColumns;
import com.proj.model.*;

public class StaticHandler {
	private Model model;
	
	public StaticHandler(Model model) {
		this.model = model;
	}
	
	
	/**
	 * Loads all employees from database and adds them to the model.
	 */
	public void instantiateEmployees(ResultSet resultSet) throws SQLException {

		
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
	public void instantiateGroups(ResultSet groupResultSet, ResultSet memberResultSet) throws SQLException {
		// Handle group names first

		HashMap<Integer, Group> groups = new HashMap<>();
		
		while (groupResultSet.next()) {
			String name = groupResultSet.getString(GroupColumns.Name.colNr());
			int groupId = groupResultSet.getInt(GroupColumns.Id.colNr());
			
			groups.put(new Integer(groupId), new Group(name));
		}
		
		// Handle members of groups

		
		while (memberResultSet.next()) {
			// Get relevant employee
			Employee employee = model.getEmployee(
					memberResultSet.getString(MemberOfColumns.EmployeeEmail.colNr())
				);
			
			// Find group and add employee
			int groupId = memberResultSet.getInt(MemberOfColumns.GroupId.colNr());
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
	public void instantiateMeetingRooms(ResultSet resultSet) throws SQLException {

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


}
