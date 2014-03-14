package com.proj.database.columns;


public enum EmployeeColumns implements Columns {
	Email,
	Password,
	Name,
	Telephone;
	
	public int colNr() {
		return this.ordinal() + 1;
	}

}
