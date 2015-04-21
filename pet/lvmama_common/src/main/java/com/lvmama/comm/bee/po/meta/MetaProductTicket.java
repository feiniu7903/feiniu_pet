package com.lvmama.comm.bee.po.meta;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class MetaProductTicket extends MetaProduct {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4188567994446714043L;
	private Long metaTicketId;

	private String todayOrderAble = "false";//是否手机当天预定
	
	private Long lastTicketTime = 0L;//最短换票间隔小时数
	
	private Long lastPassTime = 0L;//最晚入园前多少小时数可售
	
	private Float lastPassTimeHour;
	private Float lastTicketTimeHour;
 
	public Long getMetaTicketId() {
		return metaTicketId;
	}

	public void setMetaTicketId(Long metaTicketId) {
		this.metaTicketId = metaTicketId;
	}
	
 

	public String getProductType() {
		return Constant.PRODUCT_TYPE.TICKET.name();
	}
	
	/**
	 * 门票关联景区
	 */
	public boolean isRelatePlace() {
		return true;
	}
	
	public boolean hasTodayOrderAble(){
		if(StringUtils.isNotEmpty(this.todayOrderAble)) {
			if("true".equals(this.todayOrderAble)){
				return true;
			}
		}
		return false;
	}

	public Long getLastTicketTime() {
		return lastTicketTime;
	}

	public void setLastTicketTime(Long lastTicketTime) {
		this.lastTicketTime = lastTicketTime;
		this.lastTicketTimeHour = DateUtil.convertToHours(lastTicketTime);
	}

	public Long getLastPassTime() {
		return lastPassTime;
	}

	public void setLastPassTime(Long lastPassTime) {
		this.lastPassTime = lastPassTime;
		this.lastPassTimeHour = DateUtil.convertToHours(lastPassTime);
	}

	public String getTodayOrderAble() {
		return todayOrderAble;
	}

	public void setTodayOrderAble(String todayOrderAble) {
		this.todayOrderAble = todayOrderAble;
	}

	public Float getLastPassTimeHour() {
		return lastPassTimeHour;
	}

	public void setLastPassTimeHour(Float lastPassTimeHour) {
		this.lastPassTimeHour = lastPassTimeHour;
		this.lastPassTime = DateUtil.convertToMinutes(lastPassTimeHour);
	}

	public Float getLastTicketTimeHour() {
		return lastTicketTimeHour;
	}

	public void setLastTicketTimeHour(Float lastTicketTimeHour) {
		this.lastTicketTimeHour = lastTicketTimeHour;
		lastTicketTime = DateUtil.convertToMinutes(lastTicketTimeHour);	
	}
}