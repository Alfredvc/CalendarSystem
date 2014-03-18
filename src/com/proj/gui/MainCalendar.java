package com.proj.gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.proj.model.*;

public class MainCalendar extends JFrame {
	private Model model;

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
		
	}
	
	public void chooseCalendars() {
		new ChooseCalendar(model, new DefaultListModel<Employee>());
	}
	

	public MainCalendar(Model model) {
		this.model = model;
		
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
