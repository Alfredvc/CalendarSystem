package com.proj.database.columns;

public enum MemberOfColumns implements Columns {
	GroupId,
	EmployeeEmail;
	
	public int colNr() {
		return this.ordinal() + 1;
	}

}
