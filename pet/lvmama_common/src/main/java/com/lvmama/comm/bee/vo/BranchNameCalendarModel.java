package com.lvmama.comm.bee.vo;

import com.lvmama.comm.bee.po.ebooking.EbkHousePrice;

public class BranchNameCalendarModel {

	
	private EbkHousePrice ebkHousePrice;
	
	private CalendarModel calendarModel;
	private CalendarModel nextCalendarModel;
	public EbkHousePrice getEbkHousePrice() {
		return ebkHousePrice;
	}
	public void setEbkHousePrice(EbkHousePrice ebkHousePrice) {
		this.ebkHousePrice = ebkHousePrice;
	}
	public CalendarModel getCalendarModel() {
		return calendarModel;
	}
	public void setCalendarModel(CalendarModel calendarModel) {
		this.calendarModel = calendarModel;
	}
	public CalendarModel getNextCalendarModel() {
		return nextCalendarModel;
	}
	public void setNextCalendarModel(CalendarModel nextCalendarModel) {
		this.nextCalendarModel = nextCalendarModel;
	}
	
}
