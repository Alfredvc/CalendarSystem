package com.proj.database;

public enum GroupColumns implements Columns {
	Id,
	Name;
	
	public int colNr() {
		return this.ordinal() + 1;
	}

}
