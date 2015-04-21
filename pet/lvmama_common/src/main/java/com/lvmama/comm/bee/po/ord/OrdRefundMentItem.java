package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;


public class OrdRefundMentItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6195032059989292120L;
	private Long refundmentItemId;
	private Long refundmentId;
	private Long orderItemMetaId;
	private String type;
	private Long amount;
	private String memo;
	private Long actualLoss;
	
	public Long getRefundmentItemId() {
		return refundmentItemId;
	}
	public void setRefundmentItemId(Long refundmentItemId) {
		this.refundmentItemId = refundmentItemId;
	}
	public Long getRefundmentId() {
		return refundmentId;
	}
	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}
	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}
	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getActualLoss() {
		return actualLoss;
	}
	public void setActualLoss(Long actualLoss) {
		this.actualLoss = actualLoss;
	}
}