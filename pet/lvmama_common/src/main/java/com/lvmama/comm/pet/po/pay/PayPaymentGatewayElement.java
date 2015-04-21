package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;


public class PayPaymentGatewayElement implements Serializable{
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 4242600495669040430L;
	/**
	 * 主键
	 */
	private Long paymentGatewayElementId;
	/**
	 * 网关code
	 */
	private String gateway;
	/**
	 * 是否可以输入对账流水号
	 */
	private String isPaymentTradeNo=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入银行交易流水号
	 */
	private String isGatewayTradeNo=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入退款流水号（只在分期退款有用）
	 */
	private String isRefundSerial=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入交易时间
	 */
	private String isCallbackTime=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入备注
	 */
	private String isCallbackInfo=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入收款公司
	 */
	private String isReceivingCompany=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入收款人
	 */
	private String isReceivingPerson=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入收款银行
	 */
	private String isReceivingBank=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 使用状态（禁用=FORBIDDEN、启动=ENABLE）
	 */
	private String status=Constant.PAYMENT_GATEWAY_ELEMENT_STATUS.FORBIDDEN.name();
	/**
	 * 是否可以输入对方户名 
	 */
	private String isPaymentAccount=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入对方银行名称
	 */
	private String isPaymentBankName=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入对方银行账号
	 */
	private String isPaymentBankCardNo=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入摘要
	 */
	private String isSummary=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入名称(如:市场营销活动名称、福利活动名称、代金券活动名称等)
	 */
	private String isDetailName=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入所属中心
	 */
	private String isBelongCenter=Boolean.FALSE.toString().toUpperCase();
	/**
	 * 是否可以输入所属部门
	 */
	private String isBelongDepartment=Boolean.FALSE.toString().toUpperCase();
	
	public Long getPaymentGatewayElementId() {
		return paymentGatewayElementId;
	}
	public void setPaymentGatewayElementId(Long paymentGatewayElementId) {
		this.paymentGatewayElementId = paymentGatewayElementId;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getIsPaymentTradeNo() {
		return isPaymentTradeNo;
	}
	public void setIsPaymentTradeNo(String isPaymentTradeNo) {
		this.isPaymentTradeNo = isPaymentTradeNo;
	}
	public String getIsGatewayTradeNo() {
		return isGatewayTradeNo;
	}
	public void setIsGatewayTradeNo(String isGatewayTradeNo) {
		this.isGatewayTradeNo = isGatewayTradeNo;
	}
	public String getIsRefundSerial() {
		return isRefundSerial;
	}
	public void setIsRefundSerial(String isRefundSerial) {
		this.isRefundSerial = isRefundSerial;
	}
	public String getIsCallbackTime() {
		return isCallbackTime;
	}
	public void setIsCallbackTime(String isCallbackTime) {
		this.isCallbackTime = isCallbackTime;
	}
	public String getIsCallbackInfo() {
		return isCallbackInfo;
	}
	public void setIsCallbackInfo(String isCallbackInfo) {
		this.isCallbackInfo = isCallbackInfo;
	}
	public String getIsReceivingCompany() {
		return isReceivingCompany;
	}
	public void setIsReceivingCompany(String isReceivingCompany) {
		this.isReceivingCompany = isReceivingCompany;
	}
	public String getIsReceivingPerson() {
		return isReceivingPerson;
	}
	public void setIsReceivingPerson(String isReceivingPerson) {
		this.isReceivingPerson = isReceivingPerson;
	}
	public String getIsReceivingBank() {
		return isReceivingBank;
	}
	public void setIsReceivingBank(String isReceivingBank) {
		this.isReceivingBank = isReceivingBank;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsPaymentAccount() {
		return isPaymentAccount;
	}
	public void setIsPaymentAccount(String isPaymentAccount) {
		this.isPaymentAccount = isPaymentAccount;
	}
	public String getIsPaymentBankName() {
		return isPaymentBankName;
	}
	public void setIsPaymentBankName(String isPaymentBankName) {
		this.isPaymentBankName = isPaymentBankName;
	}
	public String getIsPaymentBankCardNo() {
		return isPaymentBankCardNo;
	}
	public void setIsPaymentBankCardNo(String isPaymentBankCardNo) {
		this.isPaymentBankCardNo = isPaymentBankCardNo;
	}
	public String getIsSummary() {
		return isSummary;
	}
	public void setIsSummary(String isSummary) {
		this.isSummary = isSummary;
	}
	public String getIsDetailName() {
		return isDetailName;
	}
	public void setIsDetailName(String isDetailName) {
		this.isDetailName = isDetailName;
	}
	public String getIsBelongCenter() {
		return isBelongCenter;
	}
	public void setIsBelongCenter(String isBelongCenter) {
		this.isBelongCenter = isBelongCenter;
	}
	public String getIsBelongDepartment() {
		return isBelongDepartment;
	}
	public void setIsBelongDepartment(String isBelongDepartment) {
		this.isBelongDepartment = isBelongDepartment;
	}
}
