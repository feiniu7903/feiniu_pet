package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

public class OrderParent implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderParentId;
	private String subOrderNum;
	private Long userId;
	private Date createTime;
	private String phoneNo;
	public Long getOrderParentId() {
		return orderParentId;
	}
	public void setOrderParentId(Long orderParentId) {
		this.orderParentId = orderParentId;
	}
	public String getSubOrderNum() {
		return subOrderNum;
	}
	public void setSubOrderNum(String subOrderNum) {
		this.subOrderNum = subOrderNum;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	
	
}
