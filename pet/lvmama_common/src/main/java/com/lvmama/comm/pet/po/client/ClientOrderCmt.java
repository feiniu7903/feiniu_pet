package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.StringUtil;

public class ClientOrderCmt implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5471931633238807715L;
	
	private String productName;
	private String orderId;
	/**
	 * 返现金额
	 */
	private String cashRefund;
	
	private String  point = "100";
	

	public String getCashRefundYuan() {
		
		if (StringUtils.isEmpty(this.cashRefund)) {
			return "";
		} else {
			if ("0".equals(this.cashRefund)){
				return"";
			}
			return (Long.valueOf(this.cashRefund) / 100)+"";
		}
	}
	
	
	public String getProductName() {
		return StringUtil.coverNullStrValue(productName);
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOrderId() {
		return StringUtil.coverNullStrValue(orderId);
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCashRefund() {
		return StringUtil.coverNullStrValue(cashRefund);
	}
	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}
	
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
}
