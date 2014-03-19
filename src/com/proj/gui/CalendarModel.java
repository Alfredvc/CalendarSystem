package com.proj.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.proj.model.Appointment;
import com.proj.model.Employee;
import com.proj.model.Model;

public class CalendarModel extends AbstractListModel<Appointment> {
	public static String
			WEEK_PROP = "week",
			YEAR_PROP = "year";
	
	private Calendar week;
	private Model model;

	private ListModel<Employee> employeeListModel;
	private ArrayList<Appointment> appointments = new ArrayList<>();
	private ArrayList<Integer> counts = new ArrayList<>();
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public CalendarModel(Model model, ListModel<Employee> employeeListModel) {
		this.model = model;
		//TODO: Listen to the model for new or removed appointments!!
		this.employeeListModel = employeeListModel;
		employeeListModel.addListDataListener(new EmployeeDataListener());
		week = Calendar.getInstance();
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

	private void addCalendar(Employee e) {
			ArrayList<Appointment> appointments = model.getMyAppointments(e);
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
	
	
	private void removeCalendar(Employee e) {
		
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

	
	/**
	 * This class listens to the employee list model and updates the appointments in
	 * this calendar model!
	 */
	private class EmployeeDataListener implements ListDataListener {

		@Override
		public void contentsChanged(ListDataEvent arg0) {
			int endIndex = arg0.getIndex1();
			for (int i = arg0.getIndex0(); i < endIndex; i++) {
				Employee changed = employeeListModel.getElementAt(i);
				removeCalendar(changed);
				addCalendar(changed);
			}
			
		}

		@Override
		public void intervalAdded(ListDataEvent arg0) {
			int endIndex = arg0.getIndex1();
			for (int i = arg0.getIndex0(); i < endIndex; i++) {
				addCalendar(employeeListModel.getElementAt(i));
			}			
		}

		@Override
		public void intervalRemoved(ListDataEvent arg0) {
			int endIndex = arg0.getIndex1();
			for (int i = arg0.getIndex0(); i < endIndex; i++) {
				removeCalendar(employeeListModel.getElementAt(i));
			}
		}
		
	}
}
