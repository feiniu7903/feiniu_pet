package com.lvmama.comm.vo;

import java.io.Serializable;

public class ViewCalendarModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2555173420850519833L;
	private int month;
	private int year;
	private int preMonth;
	private int nextMonth;
	private ViewTimePrice[][] viewTimePrice;
	
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getPreMonth() {
		return preMonth;
	}
	public void setPreMonth(int preMonth) {
		this.preMonth = preMonth;
	}
	public int getNextMonth() {
		return nextMonth;
	}
	public void setNextMonth(int nextMonth) {
		this.nextMonth = nextMonth;
	}
	public ViewTimePrice[][] getViewTimePrice() {
		return viewTimePrice;
	}
	public void setViewTimePrice(ViewTimePrice[][] viewTimePrice) {
		this.viewTimePrice = viewTimePrice;
	}
}
