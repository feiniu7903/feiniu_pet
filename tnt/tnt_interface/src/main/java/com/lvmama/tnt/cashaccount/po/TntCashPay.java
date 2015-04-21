package com.lvmama.tnt.cashaccount.po;

import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.order.po.TntOrder;

/**
 * 现金帐户支付记录
 * 
 * @author gaoxin
 * @version 1.0
 */
public class TntCashPay implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * cashPayId
	 */
	private java.lang.Long cashPayId;
	/**
	 * 支付流水号
	 */
	private java.lang.String serial;
	/**
	 * 主键，与用户表USER_ID相同
	 */
	private java.lang.Long cashAccountId;
	/**
	 * tntOrderId
	 */
	private java.lang.Long tntOrderId;

	/**
	 * productName
	 */
	private String productName;
	/**
	 * 支付金额
	 */
	private java.lang.Long amount;
	/**
	 * 支付状态(CREATE,SUCCESS,FAIL)
	 */
	private java.lang.String status;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	
	private TntOrder tntOrder = new TntOrder();

	// columns END

	public TntCashPay() {
	}

	public TntCashPay(java.lang.Long cashPayId) {
		this.cashPayId = cashPayId;
	}

	public void setCashPayId(java.lang.Long value) {
		this.cashPayId = value;
	}

	public java.lang.Long getCashPayId() {
		return this.cashPayId;
	}

	public void setSerial(java.lang.String value) {
		this.serial = value;
	}

	public java.lang.String getSerial() {
		return this.serial;
	}

	public void setCashAccountId(java.lang.Long value) {
		this.cashAccountId = value;
	}

	public java.lang.Long getCashAccountId() {
		return this.cashAccountId;
	}

	public void setTntOrderId(java.lang.Long value) {
		this.tntOrderId = value;
	}

	public java.lang.Long getTntOrderId() {
		return this.tntOrderId;
	}

	public void setAmount(java.lang.Long value) {
		this.amount = value;
	}

	public java.lang.Long getAmount() {
		return this.amount;
	}

	public void setStatus(java.lang.String value) {
		this.status = value;
	}

	public java.lang.String getStatus() {
		return this.status;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCnCreateTime() {
		return TntUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm");
	}

	public void setAmountY(String amountY) {
		this.setAmount(PriceUtil.convertToFen(amountY));
	}

	public Float getAmountToYuan() {
		return PriceUtil.convertToYuan(this.getAmount());
	}
	
	
	public TntOrder getTntOrder() {
		return tntOrder;
	}

	public void setTntOrder(TntOrder tntOrder) {
		this.tntOrder = tntOrder;
	}

	@Override
	public String toString() {
		return "TntCashPay [cashPayId=" + cashPayId + ", serial=" + serial
				+ ", cashAccountId=" + cashAccountId + ", tntOrderId="
				+ tntOrderId + ", amount=" + amount + ", status=" + status
				+ ", createTime=" + createTime + "]";
	}

}
