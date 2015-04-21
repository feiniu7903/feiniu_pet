package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付对象-扩展.
 * @author liwenzhan
 *
 */
public class PayPaymentDetail implements Serializable {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 237741476453736708L;
	/**
	 * 主键
	 */
	private Long paymentDetailId;
	/**
	 * 支付信息主键 与pay_payment表1对1关系
	 */
	private Long paymentId;
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
	
	public Long getPaymentDetailId() {
		return paymentDetailId;
	}
	public void setPaymentDetailId(Long paymentDetailId) {
		this.paymentDetailId = paymentDetailId;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
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
