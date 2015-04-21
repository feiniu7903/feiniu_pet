package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

/**
 * 订单三种金额的统计使用
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public class OrdOrderSum implements Serializable{

	private long orderPay;
	private long oughtPay;
	private long actualPay;
	/**
	 * @return the orderPay
	 */
	public long getOrderPay() {
		return orderPay;
	}
	/**
	 * @param orderPay the orderPay to set
	 */
	public void setOrderPay(long orderPay) {
		this.orderPay = orderPay;
	}
	/**
	 * @return the oughtPay
	 */
	public long getOughtPay() {
		return oughtPay;
	}
	/**
	 * @param oughtPay the oughtPay to set
	 */
	public void setOughtPay(long oughtPay) {
		this.oughtPay = oughtPay;
	}
	/**
	 * @return the actualPay
	 */
	public long getActualPay() {
		return actualPay;
	}
	/**
	 * @param actualPay the actualPay to set
	 */
	public void setActualPay(long actualPay) {
		this.actualPay = actualPay;
	}
	
	
	public long getOrderPayYan(){
		return orderPay/100;
	}
	
	public long getOughtPayYan(){
		return oughtPay/100;
	}
	public long getActualPayYan(){
		return actualPay/100;
	}
	
}
