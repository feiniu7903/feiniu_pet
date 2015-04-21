package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

public class OrdExpress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1867434457659603269L;
	private Long expressId;
	private String companyName;
	private String expressNo;
	private Date sendTime;
	private Long objectId;
	private String objectType;
	
	public Long getExpressId() {
		return expressId;
	}
	public void setExpressId(Long expressId) {
		this.expressId = expressId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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
}
