package com.proj.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

import com.proj.model.Appointment;
import com.proj.model.Employee;
import com.proj.model.Model;

public class CalendarModel extends AbstractListModel<Appointment> {
	public static String
			WEEK_PROP = "week",
			YEAR_PROP = "year";
	
	private Calendar week;
	private Model model;
	private Employee defaultCalendar;

	private ArrayList<Employee> calendars = new ArrayList<>();
	private ArrayList<Appointment> appointments = new ArrayList<>();
	private ArrayList<Integer> counts = new ArrayList<>();
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private SelectedCalendarListModel selectedCalendarListModel = new SelectedCalendarListModel();
	
	public CalendarModel(Model model, Employee calendar) {
		this.model = model;
		week = Calendar.getInstance();
		addCalendar(calendar);
		defaultCalendar = calendar;
	}
	
	public void resetWeek() {
		int oldWeek = getWeek();
		int oldYear = getYear();
		week = Calendar.getInstance();
		pcs.firePropertyChange(WEEK_PROP, oldWeek, getWeek());
		pcs.firePropertyChange(YEAR_PROP, oldYear, getYear());	}
	
	public void setWeek(int week) {
		if (week < 1 || week > 52) {
			throw new IllegalArgumentException("What kind of week is " + week + "?");
		}
		int oldWeek = getWeek();
		int oldYear = getYear();
		this.week.set(Calendar.WEEK_OF_YEAR, week);
		pcs.firePropertyChange(WEEK_PROP, oldWeek, getWeek());
		pcs.firePropertyChange(YEAR_PROP, oldYear, getYear());
	}
	
	public void setWeekRelative(int dist) {
		int oldWeek = getWeek();
		int oldYear = getYear();
		week.add(Calendar.WEEK_OF_YEAR, dist);
		pcs.firePropertyChange(WEEK_PROP, oldWeek, getWeek());
		pcs.firePropertyChange(YEAR_PROP, oldYear, getYear());
	}

	public int getWeek() {
		return week.get(Calendar.WEEK_OF_YEAR);
	}

	public int getYear() {
		return week.get(Calendar.YEAR);
	}

	
	public void resetCalendar() {
		for (Employee c : calendars) {
			removeCalendar(c);
		}
		addCalendar(defaultCalendar);
	}
	
	
	public void addCalendar(Employee e) {
		if (calendars.contains(e)) {
			return; //Nothing changed
		}
		calendars.add(e);
		selectedCalendarListModel.fireAdded(calendars.size() - 1);
		
		for (Employee calendar : calendars) {
			ArrayList<Appointment> appointments = model.getMyAppointments(calendar);
			int startIndex = this.appointments.size() - 1;
			
			for (Appointment a : appointments) {
				int index = this.appointments.indexOf(a);
				if (index < 0) {
					this.appointments.add(a);
					counts.add(1);
				} else {
					counts.set(index, counts.get(index) + 1);
				}
			}
			
			// Should we fire an event?
			int endIndex = this.appointments.size() - 1;
			if (endIndex > startIndex) {
				fireIntervalAdded(this, startIndex, this.appointments.size() - 1);
			}
		}
	}
	
	
	public void removeCalendar(Employee e) {
		int index = calendars.indexOf(e);
		if (index < 0) {
			return;
		}
		
		calendars.remove(index);
		selectedCalendarListModel.fireRemoved(index);
		
		ArrayList<Appointment> appointments = model.getMyAppointments(e);
		
		for (Appointment a : appointments) {
			int appointmentIndex = this.appointments.indexOf(a);
			
			if (counts.get(appointmentIndex) == 1) {
				this.appointments.remove(appointmentIndex);
				counts.remove(appointmentIndex);
				fireIntervalRemoved(this, appointmentIndex, appointmentIndex);
			} else {
				counts.set(appointmentIndex, counts.get(appointmentIndex) - 1);
			}
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	@Override
	public Appointment getElementAt(int index) {
		return appointments.get(index);
	}

	@Override
	public int getSize() {
		return appointments.size();
	}
	
	public ListModel<Employee> getCalendarListModel() {
		return selectedCalendarListModel;
	}
	
	/**
	 * This class exposes the data in the employee array as a list model
	 */
	private class SelectedCalendarListModel extends AbstractListModel<Employee> {
		@Override
		public Employee getElementAt(int index) {
			return calendars.get(index);
		}
		@Override
		public int getSize() {
			return calendars.size();
		}
		
		public void fireAdded(int index) {
			// Note: This method belongs to the SelectedCalendarListModel!
			fireIntervalAdded(this, index, index);
		}
		
		public void fireRemoved(int index) {
			fireIntervalRemoved(this, index, index);
		}
	}
}
