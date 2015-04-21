package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

/**
 * @author fangweiquan
 * @description E景通门票数量和游玩人数统计
 * @version 
 * @time 20130306
 */
public class OrdEplaceOrderQuantity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -31313195772886809L;
	
	/**
	 * E景通门票时间统计
	 */
	private String queryDate;

	/**
	 * E景通门票预定数量
	 */
	private Long sumBookTicketQuantity;
	/**
	 * E景通已取门票数量
	 */
	private Long sumPickupTicketQuantity;
	/**
	 * E景通未取门票数量
	 */
	private Long sumUnpickupTicketQuantity;
	/**
	 * E景通预计游玩人数
	 */
	private Long sumExpectPlayQuantity;
	/**
	 * E景通已经游玩人数
	 */
	private Long sumPlayQuantity;
	/**
	 * E景通未游玩人数
	 */
	private Long sumUnplayQuantity;
	public Long getSumBookTicketQuantity() {
		return sumBookTicketQuantity;
	}
	public void setSumBookTicketQuantity(Long sumBookTicketQuantity) {
		this.sumBookTicketQuantity = sumBookTicketQuantity;
	}
	public Long getSumPickupTicketQuantity() {
		return sumPickupTicketQuantity;
	}
	public void setSumPickupTicketQuantity(Long sumPickupTicketQuantity) {
		this.sumPickupTicketQuantity = sumPickupTicketQuantity;
	}
	public Long getSumUnpickupTicketQuantity() {
		return sumUnpickupTicketQuantity;
	}
	public void setSumUnpickupTicketQuantity(Long sumUnpickupTicketQuantity) {
		this.sumUnpickupTicketQuantity = sumUnpickupTicketQuantity;
	}
	public Long getSumExpectPlayQuantity() {
		return sumExpectPlayQuantity;
	}
	public void setSumExpectPlayQuantity(Long sumExpectPlayQuantity) {
		this.sumExpectPlayQuantity = sumExpectPlayQuantity;
	}
	public Long getSumPlayQuantity() {
		return sumPlayQuantity;
	}
	public void setSumPlayQuantity(Long sumPlayQuantity) {
		this.sumPlayQuantity = sumPlayQuantity;
	}
	public Long getSumUnplayQuantity() {
		return sumUnplayQuantity;
	}
	public void setSumUnplayQuantity(Long sumUnplayQuantity) {
		this.sumUnplayQuantity = sumUnplayQuantity;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	
}
