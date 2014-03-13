package com.proj.database;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {
	/**
	 * Converts a java.util.Date to a java.sql.Timestamp
	 */
	public static Timestamp getSqlTimestamp(Date date){
		return new Timestamp(date.getTime());
	}
	
	/**
	 * Converts a java.sql.Timestamp to a java.util.Date
	 */
	public static Date getDate(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		return calendar.getTime();
	}
}
