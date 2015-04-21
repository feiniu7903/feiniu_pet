package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;


public class OrdRefundmentItemProd implements Serializable {
	private static final long serialVersionUID = 5989619661970072482L;
	private Long refundmentItemProdId;
	private Long refundmentId;
	private Long ordItemProdId;
	private Long refundedAmount;
	
	public Long getRefundmentItemProdId() {
		return refundmentItemProdId;
	}
	public void setRefundmentItemProdId(Long refundmentItemProdId) {
		this.refundmentItemProdId = refundmentItemProdId;
	}
	public Long getRefundmentId() {
		return refundmentId;
	}
	public void setRefundmentId(Long refundmentId) {
		this.refundmentId = refundmentId;
	}

	public Long getOrdItemProdId() {
		return ordItemProdId;
	}
	public void setOrdItemProdId(Long ordItemProdId) {
		this.ordItemProdId = ordItemProdId;
	}
	public Long getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(Long refundedAmount) {
		this.refundedAmount = refundedAmount;
	}

}