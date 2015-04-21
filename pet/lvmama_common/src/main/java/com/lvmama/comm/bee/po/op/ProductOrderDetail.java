package com.lvmama.comm.bee.po.op;

import java.io.Serializable;

public class ProductOrderDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 565096416422384954L;
	private Long orderId;
	private Double settlePrice;
	private Long quantity;
	private Double settleAmount;
	private Double payedAmount;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Double getSettlePrice() {
		return settlePrice;
	}
	public void setSettlePrice(Double settlePrice) {
		this.settlePrice = settlePrice;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Double getSettleAmount() {
		return settleAmount;
	}
	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}
	public Double getPayedAmount() {
		return payedAmount;
	}
	public void setPayedAmount(Double payedAmount) {
		this.payedAmount = payedAmount;
	}
	
	
}
