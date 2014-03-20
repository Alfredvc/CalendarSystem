package com.proj.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.proj.client.Client;
import com.proj.model.Appointment;
import com.proj.model.Appointment.Flag;
import com.proj.model.Employee;
import com.proj.model.Model;
import com.proj.model.ModelChangeSupport.ModelChangedListener;
import com.proj.model.Notification;

public class MainCalendar extends JFrame {
	private Model model;
	private CalendarView calendarView;
	private CalendarModel calendarModel;
	private SelectedCalendarsListModel selectedCalendarsListModel;
	private ActionHandler actionHandler = new ActionHandler();
	
	public MainCalendar(Model model, Employee currentEmployee) {
		this.model = model;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(new Dimension(1000, 600));
		
		model.addModelChangeListener(new ModelChangeHandler());

		// Instantiate models
		selectedCalendarsListModel = new SelectedCalendarsListModel(Client.getCurrentEmployee());
		calendarModel = new CalendarModel(getModel(), selectedCalendarsListModel);
		
		// Add tool bar
		Toolbar toolbar = new Toolbar(calendarModel);
		toolbar.addActionListener(actionHandler);
		add(toolbar);
		
		// Add calendar view
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
			//TODO: bestemmer om ViewAppointment eller EditAppointment skal kjøres
			if(Client.getCurrentEmployee() == appointment.getLeader().getEmployee()){
			new EditAppointment(model, appointment);				
			}
			else{
			new ViewAppointment(appointment);				
			}
			
			
		}
	}
	
	public void chooseCalendars() {
		new ChooseCalendar(model, selectedCalendarsListModel);
	}
	
	public void showNotifications() {
		NotificationListModel nlm = new NotificationListModel(this.model, Client.getCurrentEmployee());
		new Notifications(nlm);
	}
	
	public Model getModel() {
		return model;
	}
	
	private void displayNotification(Notification notification) {
		new NotificationToast(notification);
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


	/**
	 * Handles changes to model and displays notification when notification appears
	 */
	private class ModelChangeHandler implements ModelChangedListener {

		@Override
		public void modelChanged(Appointment appointment, Flag flag, PropertyChangeEvent event) {
			String property = event.getPropertyName();
			Object oldObj = event.getOldValue();
			Object newObj = event.getNewValue();
			
			switch (property) {
			
			case "notifications":
				if (oldObj == null && newObj instanceof Notification) {
					Notification notification = (Notification) newObj;
					if (notification.isRelevantFor(Client.getCurrentEmployee())) {
						displayNotification(notification);
					}
				}
				break;
				
			case "appointments":
				if (oldObj == null && newObj instanceof Appointment) {
					Notification[] notifications = ((Appointment) newObj).getNotifications();
					for (Notification n : notifications) {
						if (n.isRelevantFor(Client.getCurrentEmployee())) {
							displayNotification(n);
							return; // Let's assume we're only interested in the first...
						}
					}
				}
				break;
			}			
		}
		
	}
}
