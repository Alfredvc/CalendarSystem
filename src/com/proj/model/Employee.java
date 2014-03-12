package com.proj.model;


public class Employee {
	private String
		name,
		email;
	private int telephone;
	
	public Employee(String email, String name, int telephone) {
		this.name = name;
		this.email = email;
		this.telephone = telephone;
	}

    public Employee(){

    }

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public int getTelephone() {
		return telephone;
	}	
}
