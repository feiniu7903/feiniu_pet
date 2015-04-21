package com.lvmama.comm.pet.po.money;

import java.io.Serializable;
import java.util.Date;

/**
 * 现金账户金额冻结队列
 */
public class CashFreezeQueue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5462461682675309879L;

	/**
	 * 主键
	 */
	private Long freezeQueueId;
	
	/**
	 * 主键，与用户表USER_ID相同
	 */
	private Long cashAccountId;
	
	/**
	 * 提现记录ID
	 */
	private Long cashDrawId;
	
	/**
	 * 冻结金额，以分为单位
	 */
	private Long freezeAmount;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getFreezeQueueId() {
		return freezeQueueId;
	}

	public void setFreezeQueueId(Long freezeQueueId) {
		this.freezeQueueId = freezeQueueId;
	}

	public Long getCashAccountId() {
		return cashAccountId;
	}

	public void setCashAccountId(Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}

	public Long getCashDrawId() {
		return cashDrawId;
	}

	public void setCashDrawId(Long cashDrawId) {
		this.cashDrawId = cashDrawId;
	}

	public Long getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Long freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
