package com.proj.model;


import java.io.Serializable;

public class Employee implements Invitable, Serializable{
	private String
		name,
		email;
	private int telephone;

    public Employee(Employee employee){
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.telephone = employee.getTelephone();
    }
	
	public Employee(String email, String name, int telephone) {
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

	public int getTelephone() {
		return telephone;
	}	
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Employee){
			
			if(this.email.equals(((Employee)obj).getEmail()) && this.name.equals(((Employee)obj).getName())
					&& this.telephone==((Employee)obj).getTelephone()){
				return true;
			}
	
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.telephone%40;
	}
	public String toString() {
		return name;
	}
}
