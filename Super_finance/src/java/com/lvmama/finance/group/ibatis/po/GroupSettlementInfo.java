package com.lvmama.finance.group.ibatis.po;

import com.lvmama.comm.pet.po.pub.ComContact;


public class GroupSettlementInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long groupSettlementId;
	private String travelGroupCode;
	private String remark;
	private Double payAmount;
	private Double payedAmount;
	private String itemName;
	private String currency;
	private String targetName;
	private String settlementPeriod;
	private String bankName;
	private String bankAccountName;
	private String bankAccount;
	private String alipayName;
	private String alipayAccount;
	/**
	 * 结算对象ID
	 */
	private Integer targetId;
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	/**
	 * 联系人信息
	 */
	private ComContact contact;
	
	public Long getGroupSettlementId() {
		return groupSettlementId;
	}
	public void setGroupSettlementId(Long groupSettlementId) {
		this.groupSettlementId = groupSettlementId;
	}
	public String getTravelGroupCode() {
		return travelGroupCode;
	}
	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getPayedAmount() {
		return payedAmount;
	}
	public void setPayedAmount(Double payedAmount) {
		this.payedAmount = payedAmount;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getSettlementPeriod() {
		return settlementPeriod;
	}
	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getAlipayName() {
		return alipayName;
	}
	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	public ComContact getContact() {
		return contact;
	}
	public void setContact(ComContact contact) {
		this.contact = contact;
	}

}