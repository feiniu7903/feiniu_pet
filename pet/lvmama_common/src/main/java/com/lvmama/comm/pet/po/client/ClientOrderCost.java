package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientOrderCost implements Serializable{

	private static final long serialVersionUID = -7312357829665298070L;

	private String orderCostTitle;
	
	private String saveMoney;
	
	private String payMoney;
	
	private Float oughtPayFloat;

	public String getSaveMoney() {
		return saveMoney;
	}

	public void setSaveMoney(String saveMoney) {
		this.saveMoney = saveMoney;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	public Float getOughtPayFloat() {
		return oughtPayFloat;
	}

	public void setOughtPayFloat(Float oughtPayFloat) {
		this.oughtPayFloat = oughtPayFloat;
	}

	public String getOrderCostTitle() {
		return orderCostTitle;
	}

	public void setOrderCostTitle(String orderCostTitle) {
		this.orderCostTitle = orderCostTitle;
	}
}
