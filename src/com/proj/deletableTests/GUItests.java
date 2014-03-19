package com.proj.deletableTests;

import java.util.ArrayList;

import com.proj.gui.Login;
import com.proj.gui.NewAppointment;
import com.proj.model.Employee;
import com.proj.model.Group;
import com.proj.model.MeetingRoom;
import com.proj.model.Model;
import com.proj.model.Participant;
import com.proj.test.RandomGenerator;;

public class GUItests {

//	public static ArrayList<Employee> testEmployeeList = new ArrayList<Employee>(); 
//	public static ArrayList<Group> testGroupList = new ArrayList<Group>();
	
	public static void main(String[] args) {
		
		Model model = new Model();
		
		for (int i = 0; i < 10; i++) {
			Employee e1 = RandomGenerator.generateEmployee();
//			testEmployeeList.add(e1);
			model.addEmployee(e1);
		}
		for (int i = 0; i < 5; i++) {
			Group g1 = RandomGenerator.generateGroup();
//			testGroupList.add(g1);	
			model.addGroup(g1);
		}
		
		for (int i = 0; i < 10; i++) {
			model.addMeetingRoom(RandomGenerator.generateMeetingRoom());
		}
		
		
		
		
		//new Login();
		new NewAppointment(model);
	
	
	}
}
