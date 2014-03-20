package com.proj.deletableTests;

import java.util.ArrayList;
import java.util.Date;

import com.proj.gui.Login;
import com.proj.gui.NewAppointment;
import com.proj.model.Appointment;
import com.proj.model.Employee;
import com.proj.model.Group;
import com.proj.model.InternalParticipant;
import com.proj.model.MeetingRoom;
import com.proj.model.Model;
import com.proj.model.Participant;
import com.proj.test.RandomGenerator;;

public class GUItests {

	private static Employee currentEmployee = RandomGenerator.generateEmployee();
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Date sDate = new Date();
		Date eDate = new Date();
		eDate.setHours(eDate.getHours()+1);
		InternalParticipant iIP = new InternalParticipant(currentEmployee);
		Model model = new Model();
		Appointment appTest = new Appointment(iIP, sDate, eDate, "testlocation");
		
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
	public static Employee getCurrentEmployee() {
		return currentEmployee;
	}
}
