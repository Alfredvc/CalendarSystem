package com.proj.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private CalendarView calendarView;
	private CalendarModel calendarModel;
	private ActionHandler actionHandler = new ActionHandler();
	
	public MainCalendar(Model model, Employee currentEmployee) {
		this.model = model;
		this.currentEmployee = currentEmployee;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(new Dimension(1019, 600));
		
		calendarModel = new CalendarModel(getModel(), getCurrentEmployee());
		
		// Add toolbar
		Toolbar toolbar = new Toolbar(calendarModel);
		toolbar.addActionListener(actionHandler);
		add(toolbar);
		
		// Add calendarview
		calendarView = new CalendarView(calendarModel);
		calendarView.addActionListener(actionHandler);
		add(new JScrollPane(calendarView));
		
		setVisible(true);
		
	}

	/**
	 * Test code...
	 */
	public static void main(String[] args) {
		new MainCalendar(new Model(), new Employee("notun@ntohu.com", "NT onu", 58473653));
	}
	
	public void newAppointment() {
		new NewAppointment(model);
	}
	
	public void viewAppointment(Appointment appointment) {
		if (appointment != null) {
			//TODO: La oss bare vise en tom foreløpig
			new NewAppointment(model);
		}
	}
	
	public void chooseCalendars() {
		new ChooseCalendar(model, calendarModel);
	}
	
	public void showNotifications() {
		//TODO: ...
	}
	
	public Employee getCurrentEmployee() {
		return currentEmployee;
	}
	
	public Model getModel() {
		return model;
	}

	
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String command = arg0.getActionCommand();
			switch (command) {
			case "notifications": showNotifications(); break;
			case "chooseCalendars": chooseCalendars(); break;
			case "newAppointment": newAppointment(); break;
			case "itemSelected": viewAppointment(calendarView.getSelectedItem()); break;
			}
		}
		
	}
}
