/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.pet.po.money;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FINC_CASH_STATUS;

/**
 * CashMoneyDraw 的modle 用于封装与应用程序的业务逻辑相关的数据.
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class CashMoneyDraw implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1668982788641636874L;
	//columns START
	/** 变量 moneyDrawId . */
	private java.lang.Long moneyDrawId;
	/** 变量 cashAccountId . */
	private java.lang.Long cashAccountId;
	/** 变量 drawAmount . */
	private Long drawAmount;
	/** 变量 bankName . */
	private java.lang.String bankName;
	/** 变量 bankAccount . */
	private java.lang.String bankAccount;
	/** 变量 bankAccountName . */
	private java.lang.String bankAccountName;
	/** 变量 kaiHuHang . */
	private java.lang.String kaiHuHang;
	/** 变量 province . */
	private java.lang.String province;
	/** 变量 city . */
	private java.lang.String city;
	/** 变量 createTime . */
	private java.util.Date createTime;
	/** 变量 aduitStatus . */
	private java.lang.String auditStatus;
	/** 变量 payStatus . */
	private java.lang.String payStatus;
	/** 变量 drawMoneyChannel . */
	private java.lang.String drawMoneyChannel;
	/** 变量 memo . */
	private java.lang.String memo;
	/** 变量 isCompensation . */
	private java.lang.String isCompensation;
	/**对公对私标志 1:对公   2:对私**/  
	private String flag="2";
	
	//columns END
	/**
	* CashMoneyDraw 的构造函数
	*/
	public CashMoneyDraw() {
	}
	/**
	* CashMoneyDraw 的构造函数
	*/
	public CashMoneyDraw(
		java.lang.Long moneyDrawId
	) {
		this.moneyDrawId = moneyDrawId;
	}
	public java.lang.Long getMoneyDrawId() {
		return moneyDrawId;
	}
	public void setMoneyDrawId(java.lang.Long moneyDrawId) {
		this.moneyDrawId = moneyDrawId;
	}
	public java.lang.Long getCashAccountId() {
		return cashAccountId;
	}
	public void setCashAccountId(java.lang.Long cashAccountId) {
		this.cashAccountId = cashAccountId;
	}
	public Long getDrawAmount() {
		return drawAmount;
	}
	public void setDrawAmount(Long drawAmount) {
		this.drawAmount = drawAmount;
	}
	public java.lang.String getBankName() {
		return bankName;
	}
	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}
	public java.lang.String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(java.lang.String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public java.lang.String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(java.lang.String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public java.lang.String getKaiHuHang() {
		return kaiHuHang;
	}
	public void setKaiHuHang(java.lang.String kaiHuHang) {
		this.kaiHuHang = kaiHuHang;
	}
	public java.lang.String getProvince() {
		return province;
	}
	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	public java.lang.String getCity() {
		return city;
	}
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(java.lang.String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public java.lang.String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	public java.lang.String getDrawMoneyChannel() {
		return drawMoneyChannel;
	}
	public void setDrawMoneyChannel(java.lang.String drawMoneyChannel) {
		this.drawMoneyChannel = drawMoneyChannel;
	}
	public java.lang.String getMemo() {
		return memo;
	}
	public void setMemo(java.lang.String memo) {
		this.memo = memo;
	}
	public java.lang.String getIsCompensation() {
		return isCompensation;
	}
	public void setIsCompensation(java.lang.String isCompensation) {
		this.isCompensation = isCompensation;
	}
	/**
	 * getDrawAmountYuan.
	 *
	 * @return 提现金额，以元为单位
	 */
	public float getDrawAmountYuan() {
		return PriceUtil.convertToYuan(drawAmount);

	}
	public String getAuditStatusName(){
		return Constant.FINC_CASH_STATUS.getCnName(this.getAuditStatus());
	}

	public String getPayStatusName(){
		return Constant.FINC_CASH_STATUS.getCnName(this.getPayStatus());
	}
	
	/**
	 * 是否打款到支付宝.
	 * @return 结果
	 */
	public boolean isFromAlipayToAlipay() {
		return Constant.DRAW_MONEY_CHANNEL.ALIPAY.name().equalsIgnoreCase(drawMoneyChannel);
	}
	
	/**
	 * 是否通过支付宝打款到银行.
	 * @return 结果
	 */
	public boolean isFromAlipayToBank() {
		return Constant.DRAW_MONEY_CHANNEL.BANK.name().equalsIgnoreCase(drawMoneyChannel);
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	/**
	 * isCanManualHandle.
	 * 
	 * @return 可以手工操作标识.<br>
	 *         true代表可以，false代表不可以
	 */
	public boolean isCanManualHandle() {
		return this.getPayStatus().equals(FINC_CASH_STATUS.ApplyPayCashSuccess.name())||this.getPayStatus().equals(FINC_CASH_STATUS.ApplyPayCashRejected.name()) ? true: false;
	}

}


