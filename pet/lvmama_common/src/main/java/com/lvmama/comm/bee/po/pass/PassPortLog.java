package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;

public class PassPortLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2620422030378040861L;
	/**
	 * 通关日志编号.
	 */
	private Long passPortLogId;
	/**
	 * 用户编号.
	 */
	private Long passPortUserId;
	/**
	 * 订单编号.
	 */
	private Long orderId;
	/**
	 * 采购产品编号.
	 */
	private Long orderItemMetaId;
	/**
	 * 创建时间.
	 */
	private Date createDate;
	/**
	 * 内容.
	 */
	private String content;

	private PassPortUser user;

	public Long getPassPortLogId() {
		return passPortLogId;
	}

	public void setPassPortLogId(Long passPortLogId) {
		this.passPortLogId = passPortLogId;
	}

	public Long getPassPortUserId() {
		return passPortUserId;
	}

	public void setPassPortUserId(Long passPortUserId) {
		this.passPortUserId = passPortUserId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public PassPortUser getUser() {
		return user;
	}

	public void setUser(PassPortUser user) {
		this.user = user;
	}
}