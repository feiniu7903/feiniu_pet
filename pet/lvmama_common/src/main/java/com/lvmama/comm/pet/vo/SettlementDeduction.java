package com.lvmama.comm.pet.vo;

import java.io.Serializable;

public class SettlementDeduction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1450914461957525966L;

	private Long amount;
	
	private Long subSettlementItemId;
	
	private Long orderItemMetaId;

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Long getSubSettlementItemId() {
		return subSettlementItemId;
	}

	public void setSubSettlementItemId(Long subSettlementItemId) {
		this.subSettlementItemId = subSettlementItemId;
	}


	
}
