package com.lvmama.distribution.model.qunar;

import java.util.List;


public class Days {
	private List<Day> dayList;
	
	@Override
	public String toString() {
		StringBuffer days = new StringBuffer();
		days.append("<days>");
		for (Day day : dayList) {
			days.append(day.toString());
		}
		days.append("</days>");
		
		return days.toString();
	}

	public void setDayList(List<Day> dayList) {
		this.dayList = dayList;
	}
	
	public int getNight(){
		return (dayList!=null && dayList.size()>0) ? dayList.size()-1 : 0;
	}
}
