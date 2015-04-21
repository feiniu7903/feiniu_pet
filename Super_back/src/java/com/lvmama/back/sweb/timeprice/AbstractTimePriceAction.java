package com.lvmama.back.sweb.timeprice;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.jms.TopicMessageProducer;

public class AbstractTimePriceAction extends BaseAction{
	/**up上个月down下个月*/
	protected String monthType;
	protected List<CalendarModel> calendarList;
	protected TimePrice timePriceBean=new TimePrice();
	protected Date currPageDate;
	protected TopicMessageProducer productMessageProducer;
	private boolean editable=true;//代表是否能编辑.
	protected String skipSetPrice;//是否跳过结算价
	public String getMonthType() {
		return monthType;
	}
	public void setMonthType(String monthType) {
		this.monthType = monthType;
	}
	public List<CalendarModel> getCalendarList() {
		return calendarList;
	}
	public void setCalendarList(List<CalendarModel> calendarList) {
		this.calendarList = calendarList;
	}
	public TimePrice getTimePriceBean() {
		return timePriceBean;
	}
	public void setTimePriceBean(TimePrice timePriceBean) {
		this.timePriceBean = timePriceBean;
	}
	public Date getCurrPageDate() {
		return currPageDate;
	}
	public void setCurrPageDate(Date currPageDate) {
		this.currPageDate = currPageDate;
	}
	
	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	/**
	 * @param productMessageProducer the productMessageProducer to set
	 */
	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}
	/**
	 * @param skipSetPrice the skipSetPrice to set
	 */
	public void setSkipSetPrice(String skipSetPrice) {
		this.skipSetPrice = skipSetPrice;
	}
	
	protected boolean hasSkipSetPrice(){
		return StringUtils.equals("true", skipSetPrice);
	}
}
