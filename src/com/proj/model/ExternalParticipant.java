package com.proj.model;

public class ExternalParticipant implements Participant {
	 
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

}
