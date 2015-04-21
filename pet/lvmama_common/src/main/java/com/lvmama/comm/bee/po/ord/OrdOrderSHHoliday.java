package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;

public class OrdOrderSHHoliday implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderSHHolidayId;
	private Long objectId;
	private String objectType;
	private String content;
	private Date createTime;
	
	public OrdOrderSHHoliday(){
		
	}
	
	public OrdOrderSHHoliday(Long objectId,String objectType,String content){
		this.content = content;
		this.objectId = objectId;
		this.objectType = objectType;
		this.createTime = new Date();
	}
	public Long getOrderSHHolidayId() {
		return orderSHHolidayId;
	}
	public void setOrderSHHolidayId(Long orderSHHolidayId) {
		this.orderSHHolidayId = orderSHHolidayId;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Object getString() {
		return "objectId = " + objectId + "  content=" + content + "  objectType=" + objectType + "  createTime=" + DateUtil.formatDate(createTime, "yyyy-MM-dd");
	}
	
}
