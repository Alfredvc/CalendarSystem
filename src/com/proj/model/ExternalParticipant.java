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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalParticipant that = (ExternalParticipant) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
