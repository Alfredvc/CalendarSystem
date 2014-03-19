package com.proj.gui;

import com.proj.model.Employee;

public class SelectedCalendarsListModel extends ArrayListModel<Employee> {
	private Employee defaultEmployee;
	
	public SelectedCalendarsListModel(Employee defaultEmployee) {
		this.defaultEmployee = defaultEmployee;
		add(defaultEmployee);
	}
	
	private SelectedCalendarsListModel(SelectedCalendarsListModel source) {
		this.defaultEmployee = source.getDefaultEmployee();
		addAll(source);
	}
	
	public void reset() {
		clear();
		add(defaultEmployee);
	}
	
	public Employee getDefaultEmployee() {
		return defaultEmployee;
	}
	
	@Override
	public SelectedCalendarsListModel clone() {
		return new SelectedCalendarsListModel(this);
	}
}
