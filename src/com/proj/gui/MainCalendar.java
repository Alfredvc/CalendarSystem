package com.proj.gui;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.proj.model.Appointment;
import com.proj.model.Employee;
import com.proj.model.Model;

public class MainCalendar extends JFrame {
	private Model model;
	private Employee currentEmployee;

	/**
	 * Test code...
	 */
	public static void main(String[] args) {
		new MainCalendar(new Model());
	}


	public void showThisWeek() {
		
	}
	
	public void showNextWeek() {
		
	}
	
	public void showPreviousWeek() {
		
	}
	
	public void showWeek() {
		
	}
	
	public void newAppointment() {
		new NewAppointment();
	}
	
	public void viewAppointment(Appointment appointment) {
		//TODO: La oss bare vise en tom foreløpig
		new NewAppointment();
	}
	
	public void chooseCalendars() {
		new ChooseCalendar(model, new DefaultListModel<Employee>());
	}
	
	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	public MainCalendar(Model model, Employee currentEmployee) {
		this.model = model;
		this.currentEmployee = currentEmployee;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		// Add toolbar
		add(new Toolbar(this));

		// Add calendarview
		JScrollPane scrollPane = new JScrollPane(new CalendarView());
		add(scrollPane);
		
		setVisible(true);
		
	}
}
