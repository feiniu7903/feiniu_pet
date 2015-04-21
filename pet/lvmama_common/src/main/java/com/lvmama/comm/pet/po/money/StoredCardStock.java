package com.lvmama.comm.pet.po.money;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author liwenzhan
 *
 */
public class StoredCardStock implements Serializable {

	/**
	 * 序列化.
	 */
	private static final long serialVersionUID = 4166627565889503508L;
	/**
	 * 库ID.
	 */
	private Long stockId;
    /**
     * 库类型.
     */
	private String stockType;
	/**
	 * 客户.
	 */
	private String customer;
	/**
	 * 接受者.
	 */
	private String accepter;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 备注.
	 */
	private String memo;
	/**
	 * 操作者.
	 */
	private String operator;
	/**
	 * 入库单状态.
	 */
	private String status;
	/**
	 * 支付类型.
	 */
	private String paymentType;
	/**
	 * 付款单位.
	 */
	private String paymentUnit;
	/**
	 * 实收金额.
	 */
	private Long actualReceived;
	/**
	 * 付款时间.
	 */
	private Date paymentTime;
	
	public Long getStockId() {
		return stockId;
	}
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	public String getStockType() {
		return stockType;
	}
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getAccepter() {
		return accepter;
	}
	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentUnit() {
		return paymentUnit;
	}
	public void setPaymentUnit(String paymentUnit) {
		this.paymentUnit = paymentUnit;
	}
	public Long getActualReceived() {
		return actualReceived;
	}
	public void setActualReceived(Long actualReceived) {
		this.actualReceived = actualReceived;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
}
