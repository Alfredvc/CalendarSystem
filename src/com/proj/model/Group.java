package com.proj.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Invitable, Serializable{
	private final String name;
	private ArrayList<Employee> employees = new ArrayList<Employee>();
	
	public Group(String name) {
		this.name = name;
	}
	
	public Employee[] getEmployees() {
		return (Employee[]) employees.toArray(new Employee[employees.size()]);
	}
	
	public void addEmployee(Employee employee) {
		employees.add(employee);
	}
	
	public String getName() {
		return name;
	}
}
