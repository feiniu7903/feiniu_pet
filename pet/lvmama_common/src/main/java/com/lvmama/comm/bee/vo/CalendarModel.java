package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.DateUtil;

public class CalendarModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4779337640619556380L;
	private int month;
	private int year;
	/** 当中旬时，是否需要当前月显示的月份 */
	private int flagNextMonth;
	private int preMonth;
	private int nextMonth;
	private TimePrice[][] calendar;
	final static String format = "yyyy-MM-dd";
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public TimePrice[][] getCalendar() {
		return calendar;
	}

	public void setCalendar(TimePrice[][] calendar) {
		this.calendar = calendar;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPreMonth() {
		if (this.month - 1 == 0) {
			return 12;
		} else {
			return month - 1;
		}
	}

	public void setPreMonth(int preMonth) {
		this.preMonth = preMonth;
	}

	public int getNextMonth() {
		if (this.month == 12) {
			return 1;
		} else {
			return this.month + 1;
		}
	}

	public void setNextMonth(int nextMonth) {
		this.nextMonth = nextMonth;
	}

	public void setTimePrice(List<TimePrice> timePriceList, Date beginDate) {
		Map<String, TimePrice> timePriceMap = new HashMap<String, TimePrice>();
		if (timePriceList != null && timePriceList.size() > 0) {
			for (int i = 0; i < timePriceList.size(); i++) {
				TimePrice timePrice = timePriceList.get(i);
				String key = DateUtil.getDateTime(format, timePrice
						.getSpecDate());
				timePriceMap.put(key, timePrice);
			}
		}
		Calendar c = Calendar.getInstance();

		c.setTime(beginDate);
		c.add(Calendar.DATE, -1);
		int k = 0;

		this.calendar = new TimePrice[6][7];
		for (int i = 0; i < calendar.length; i++) {
			TimePrice[] objH = calendar[i];
			for (int j = 0; j < objH.length; j++) {
				TimePrice tp = null;
				c.add(Calendar.DATE, 1);
				String key = DateUtil.getDateTime(format, c.getTime());
				tp = timePriceMap.get(key);
				if (tp != null) {
					objH[j] = tp;
				} else {
					tp = new TimePrice();
					tp.setSpecDate(c.getTime());

					objH[j] = tp;
				}
				k++;

			}
		}
	}

	public int getFlagNextMonth() {
		return flagNextMonth;
	}

	public void setFlagNextMonth(int flagNextMonth) {
		this.flagNextMonth = flagNextMonth;
	}

	public boolean isShowNextMonth(Date date) {
		int month = DateUtil.getMonth(date);
		return this.flagNextMonth > 0 && this.flagNextMonth == month;
	}
}
