package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PaymentUtil;

public class CashPaymentComboVO implements Serializable {
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 6017067307823145090L;
	private Long paymentId;
	/**
	 * 流水号.
	 */
    private String serial;
    /**
     * 业务类型(哪个业务类型(自由行/代售)).
     */
	private String bizType;
	/**
	 * 对象ID.
	 */
	private Long objectId;
	/**
	 * 对象类型(订单).
	 */
	private String objectType;
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType;
	/**
	 * 支付网关(渠道).
	 */
	private String paymentGateway;
	/**
	 * 支付金额.
	 */
	private Long amount;
	/**
	 * 支付状态(CREATE,PRE_SUCCESS,CANCEL,SUCCESS,FAIL).
	 */
	private String status;
	/**
	 * 回馈时间.
	 */
	private Date callbackTime;
	/**
	 * 支付回馈信息.
	 */
	private String callbackInfo;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 支付网关交易流水号.
	 */
	private String gatewayTradeNo;
	/**
	 * 汇付天下标注银行卡所在行.
	 */
	private String gateId;
	/**
	 * 发出去的交易流水号.
	 */
	private String paymentTradeNo;
	/**
	 * 退款的流水号(中行与招行的分期与支付网关交易流水号不一样，其它的一样).
	 */
	private String refundSerial;
	/**
	 * 是否已通知业务系统.
	 */
	private String notified;
	/**
	 * 操作人(SYSTEM/CSxxx/LVxxx).
	 */
	private String operator;
	/**
	 * 通知时间.
	 */
	private Date notifyTime;
	/**
	 * 原对象ID,当发生支付转移时使用
	 */
	private Long oriObjectId;
	
	
	
	
	/**
	 * 主键
	 */
	private Long paymentDetailId;
	/**
	 * 我方收款公司主键
	 */
	private Long receivingCompanyId;
	/**
	 * 我方收款银行主键
	 */
	private Long receivingBankId;
	/**
	 * 我方收款人
	 */
	private String receivingPerson;
	/**
	 * 支付方户名
	 */
	private String paymentAccount;
	
	/**
	 * 支付方银行名称
	 */
	private String paymentBankName;
	/**
	 * 支付方银行卡号
	 */
	private String paymentBankCardNo;
	/**
	 * 现金-解款人(只有现金支付的使用)
	 */
	private String cashLiberateMoneyPerson;
	/**
	 * 现金-解款时间(只有现金支付的使用)
	 */
	private Date cashLiberateMoneyDate;
	/**
	 * 现金-审核状态(未解款=UNLIBERATED、已解款=LIBERATE、已审核=VERIFIED)   
	 */
	private String cashAuditStatus;
	/**
	 * 审核人
	 */
	private String auditPerson;
	/**
	 * 线下-其它支付审核状态(未确认=UNCONFIRMED、已确认=CONFIRM)
	 */
	private String otherAuditStatus;
	/**
	 * POS机终端号(只有POS机使用)
	 */
	private String posTerminalNo;
	
	/**
	 * 所属中心
	 */
	private String belongCenter;
	/**
	 * 所属部门
	 */
	private String belongDepartment;
	/**
	 * 是否可以输入名称(如:市场营销活动名称、福利活动名称、代金券活动名称等)
	 */
	private String detailName;
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 收款公司名称
	 */
	private String receivingCompanyName;
	
	
	/**
	 * 公司名、户名 
	 */
	private String receivingAccount;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 账户性质及用途
	 */
	private String receivingAccountType;
	/**
	 * 银行卡号
	 */
	private String bankCardNo;

	
	public String getPaymentGatewayZH(){
		return PaymentUtil.getGatewayNameByGateway(paymentGateway);
	}
	
	public String getCashAuditStatusZH(){
		return Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.getCnName(cashAuditStatus);
	}
	
	public String getOtherAuditStatusZH(){
		return Constant.PAYMENT_DETAIL_OTHER_AUDIT_STATUS.getCnName(otherAuditStatus);
	}
	
	public String getCallbackTimeToSimpleDate() {
		return DateUtil.getFormatDate(callbackTime, "yyyy-MM-dd HH:mm:ss");
	}
	public String getCashLiberateMoneyDateToSimpleDate() {
		return DateUtil.getFormatDate(cashLiberateMoneyDate, "yyyy-MM-dd HH:mm:ss");
	}
	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
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

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

	public String getCallbackInfo() {
		return callbackInfo;
	}

	public void setCallbackInfo(String callbackInfo) {
		this.callbackInfo = callbackInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}

	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

	public String getRefundSerial() {
		return refundSerial;
	}

	public void setRefundSerial(String refundSerial) {
		this.refundSerial = refundSerial;
	}

	public String getNotified() {
		return notified;
	}

	public void setNotified(String notified) {
		this.notified = notified;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public Long getOriObjectId() {
		return oriObjectId;
	}

	public void setOriObjectId(Long oriObjectId) {
		this.oriObjectId = oriObjectId;
	}

	public Long getPaymentDetailId() {
		return paymentDetailId;
	}

	public void setPaymentDetailId(Long paymentDetailId) {
		this.paymentDetailId = paymentDetailId;
	}

	public Long getReceivingCompanyId() {
		return receivingCompanyId;
	}

	public void setReceivingCompanyId(Long receivingCompanyId) {
		this.receivingCompanyId = receivingCompanyId;
	}

	public Long getReceivingBankId() {
		return receivingBankId;
	}

	public void setReceivingBankId(Long receivingBankId) {
		this.receivingBankId = receivingBankId;
	}
	public void setReceivingBankId(String receivingBankId) {
		this.receivingBankId = Long.valueOf(receivingBankId);
	}
	public String getReceivingPerson() {
		return receivingPerson;
	}

	public void setReceivingPerson(String receivingPerson) {
		this.receivingPerson = receivingPerson;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getPaymentBankCardNo() {
		return paymentBankCardNo;
	}

	public void setPaymentBankCardNo(String paymentBankCardNo) {
		this.paymentBankCardNo = paymentBankCardNo;
	}

	public String getCashLiberateMoneyPerson() {
		return cashLiberateMoneyPerson;
	}

	public void setCashLiberateMoneyPerson(String cashLiberateMoneyPerson) {
		this.cashLiberateMoneyPerson = cashLiberateMoneyPerson;
	}

	public Date getCashLiberateMoneyDate() {
		return cashLiberateMoneyDate;
	}

	public void setCashLiberateMoneyDate(Date cashLiberateMoneyDate) {
		this.cashLiberateMoneyDate = cashLiberateMoneyDate;
	}

	public String getCashAuditStatus() {
		return cashAuditStatus;
	}

	public void setCashAuditStatus(String cashAuditStatus) {
		this.cashAuditStatus = cashAuditStatus;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public String getOtherAuditStatus() {
		return otherAuditStatus;
	}

	public void setOtherAuditStatus(String otherAuditStatus) {
		this.otherAuditStatus = otherAuditStatus;
	}

	public String getPosTerminalNo() {
		return posTerminalNo;
	}

	public void setPosTerminalNo(String posTerminalNo) {
		this.posTerminalNo = posTerminalNo;
	}

	public String getReceivingCompanyName() {
		return receivingCompanyName;
	}

	public void setReceivingCompanyName(String receivingCompanyName) {
		this.receivingCompanyName = receivingCompanyName;
	}

	public String getReceivingAccount() {
		return receivingAccount;
	}

	public void setReceivingAccount(String receivingAccount) {
		this.receivingAccount = receivingAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getReceivingAccountType() {
		return receivingAccountType;
	}

	public void setReceivingAccountType(String receivingAccountType) {
		this.receivingAccountType = receivingAccountType;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getPaymentBankName() {
		return paymentBankName;
	}

	public void setPaymentBankName(String paymentBankName) {
		this.paymentBankName = paymentBankName;
	}

	public String getBelongCenter() {
		return belongCenter;
	}

	public void setBelongCenter(String belongCenter) {
		this.belongCenter = belongCenter;
	}

	public String getBelongDepartment() {
		return belongDepartment;
	}

	public void setBelongDepartment(String belongDepartment) {
		this.belongDepartment = belongDepartment;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
