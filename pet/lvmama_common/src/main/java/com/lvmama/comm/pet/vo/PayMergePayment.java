package com.lvmama.comm.pet.vo;

import java.io.Serializable;

/**
 * 合并支付
 * @author fengyu
 *
 */
public class PayMergePayment implements Serializable{
	

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 4251145920385657137L;
	
	/**
     * 支付金额.
     */
	private long amount;
	/**
	 * 业务订单ID.
	 */
	private long objectId;
	/**
	 * 哪个业务类型(自由行/代售).
	 */
	private String bizType;
	/**
	 * 对象类型(订单).
	 */
	private String objectType;
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType;
    
	/**
	 * 订单支付 等待时间.
	 */
	private String waitPayment;
	/**
	 * 审核通过时间.
	 */
	private String approveTime;
    /**
     * 游玩时间
     */
	private String visitTime;
	
	/**
	 * 银行标识.
	 */
	protected String bankid;

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getWaitPayment() {
		return waitPayment;
	}

	public void setWaitPayment(String waitPayment) {
		this.waitPayment = waitPayment;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
