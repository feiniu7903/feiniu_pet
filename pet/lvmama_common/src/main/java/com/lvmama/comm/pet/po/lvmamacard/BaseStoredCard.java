package com.lvmama.comm.pet.po.lvmamacard;

import java.io.Serializable;

public class BaseStoredCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 143532452L;
	private Integer amount;
	private String cardNoBegin;
	private String cardNoEnd;
 	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getCardNoBegin() {
		return cardNoBegin;
	}
	public void setCardNoBegin(String cardNoBegin) {
		this.cardNoBegin = cardNoBegin;
	}
	public String getCardNoEnd() {
		return cardNoEnd;
	}
	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}
	
}

