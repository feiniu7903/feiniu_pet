package com.lvmama.comm.pet.vo;

import java.io.Serializable;

import com.lvmama.comm.pet.po.pub.ComContact;

/**
 * 结算单按信息 包含结算对象，供应商，联系人等信息
 * 
 * @author yanggan
 * 
 */
public class SimpleOrdSettlement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3243672771226445350L;
	/**
	 * 结算单号
	 */
	private Integer settlementId;
	/**
	 * 结算对象ID
	 */
	private Integer targetId;
	/**
	 * 结算对象名称
	 */
	private String targetName;
	/**
	 * 应结算金额
	 */
	private Float payAmount;
	/**
	 * 已结算金额
	 */
	private Float payedAmount;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 开户行名称
	 */
	private String bankName;
	/**
	 * 开户名称
	 */
	private String bankAccountName;
	/**
	 * 开户帐号
	 */
	private String bankAccount;
	/**
	 * 支付宝用户名
	 */
	private String alipayName;
	/**
	 * 支付宝帐号
	 */
	private String alipayAccount;
	/**
	 * 联系人信息
	 */
	private ComContact contact;
	/**
	 * 结算单状态
	 */
	private String status;

	private Long syncVersion;
	/**
	 * 我方结算主体
	 */
	private String companyIdStr;
	
	/**
	 * 结算周期
	 */
	private String settlementPeriodStr;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Integer settlementId) {
		this.settlementId = settlementId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public Float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}

	public Float getPayedAmount() {
		return payedAmount;
	}

	public void setPayedAmount(Float payedAmount) {
		this.payedAmount = payedAmount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSyncVersion() {
		return syncVersion;
	}

	public void setSyncVersion(Long syncVersion) {
		this.syncVersion = syncVersion;
	}

	public String getSettlementPeriodStr() {
		return settlementPeriodStr;
	}

	public void setSettlementPeriodStr(String settlementPeriodStr) {
		this.settlementPeriodStr = settlementPeriodStr;
	}

	public String getCompanyIdStr() {
		return companyIdStr;
	}

	public void setCompanyIdStr(String companyIdStr) {
		this.companyIdStr = companyIdStr;
	}

	
}
