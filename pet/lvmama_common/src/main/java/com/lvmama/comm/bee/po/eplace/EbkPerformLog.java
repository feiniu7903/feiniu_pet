package com.lvmama.comm.bee.po.eplace;

import java.io.Serializable;
import java.util.Date;

/**
 * 手机EBK通关数据记录
 * 
 * @author zhangkexing
 */

public class EbkPerformLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -689670943719972812L;

	private Long logId;
	private Date createTime;
	private Long ebkUserId;
	private String udid;
	private String addCode;
	private String quantity;
	private String orderItemMetaId;
	private Date performTime;
	private String status;
	private String uuid;
	private String memo;
	

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getEbkUserId() {
		return ebkUserId;
	}

	public void setEbkUserId(Long ebkUserId) {
		this.ebkUserId = ebkUserId;
	}


	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(String orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Date getPerformTime() {
		return performTime;
	}

	public void setPerformTime(Date performTime) {
		this.performTime = performTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EbkPerformLog [logId=");
		builder.append(logId);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", ebkUserId=");
		builder.append(ebkUserId);
		builder.append(", udid=");
		builder.append(udid);
		builder.append(", addCode=");
		builder.append(addCode);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", orderItemMetaId=");
		builder.append(orderItemMetaId);
		builder.append(", performTime=");
		builder.append(performTime);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
