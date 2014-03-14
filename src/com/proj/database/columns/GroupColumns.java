package com.proj.database.columns;

public enum GroupColumns implements Columns {
	Id,
	Name;
	
	public int colNr() {
		return this.ordinal() + 1;
	}

}
