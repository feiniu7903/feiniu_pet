package com.ejingtong.help;

import java.util.Calendar;

public class DateHelper {

	private Calendar calendar = Calendar.getInstance();
	
	public int getDay(){
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
}
