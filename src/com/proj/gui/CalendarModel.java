package com.proj.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractListModel;

import com.proj.model.Appointment;
import com.proj.model.Employee;
import com.proj.model.Model;
import com.proj.test.RandomGenerator;

public class CalendarModel extends AbstractListModel<Appointment> {
	public static String
			WEEK_PROP = "week",
			YEAR_PROP = "year";
	
	private Calendar week;
	
	private Model model;

	private Set<Employee> calendars = new HashSet<>();
	private ArrayList<Appointment> appointments = new ArrayList<>();
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public CalendarModel(Model model, Employee calendar) {
		this.model = model;
		week = Calendar.getInstance();
		addCalendar(calendar);
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

	public void addCalendar(Employee e) {
		calendars.add(e);
		//TODO: Update appointment list!
		appointments.addAll(Arrays.asList(new Appointment[]{
				RandomGenerator.generateAppointment(),
				RandomGenerator.generateAppointment(),
				RandomGenerator.generateAppointment()
		}));
	}
	
	public void removeCalendar(Employee e) {
		//TODO: Remove all appointments that should not be here!
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
}
