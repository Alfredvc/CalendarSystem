package com.proj.model;


public class Employee {
	private String
		name,
		email,
		telephone;
	
	public Employee(String email, String name, String telephone) {
		this.name = name;
		this.email = email;
		this.telephone = telephone;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}	
}
