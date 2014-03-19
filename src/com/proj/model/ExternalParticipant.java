package com.proj.model;

import java.io.Serializable;

public class ExternalParticipant implements Participant, Serializable, Invitable {
	 
	private String email;
	
	
	public ExternalParticipant (String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
		
	@Override
	public String getDisplayName() {
		return getEmail();
	}
	public String toString() {
		return getEmail();
	}
	

}
