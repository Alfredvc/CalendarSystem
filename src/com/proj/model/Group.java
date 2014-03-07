package com.proj.model;

public class Group {
	private Employee[] employees;
	
	public Group(Employee[] employees) {
		this.employees = employees;
	}
	
	public Employee[] getEmployees() {
		return employees;
	}
}
