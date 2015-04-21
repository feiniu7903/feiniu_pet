package com.lvmama.comm.pet.po.shop;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户赠送积分日志类
 * @author Brian
 *
 */
public class ShopCustomerPresentPoint implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -3174190874714172281L;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 赠送事由
	 */
	private String putThing;
	/**
	 * 赠送积分
	 */
	private Long putPoint;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 用户标识
	 */
	private Long userId;
	/**
	 * 操作人
	 */
	private String csName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getPutThing() {
		return putThing;
	}
	public void setPutThing(String putThing) {
		this.putThing = putThing;
	}
	public Long getPutPoint() {
		return putPoint;
	}
	public void setPutPoint(Long putPoint) {
		this.putPoint = putPoint;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCsName() {
		return csName;
	}
	public void setCsName(String csName) {
		this.csName = csName;
	}
}
