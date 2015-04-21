package com.lvmama.comm.pet.po.fin;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

/**
 * 订单结算项导出的Excel的数据模型
 * 
 * @author yanggan
 * 
 */
public class SetSettlementItemExcel extends SetSettlementItem {

	private static final long serialVersionUID = 1L;
	/* 结算单的打款时间 */
	private Date settlementPaymentTime;
	private String bankName;
	private String bankAccountName;
	private String bankAccount;
	
	public Date getSettlementPaymentTime() {
		return settlementPaymentTime;
	}

	public String getSettlementPaymentTimeStr() {
		return settlementPaymentTime == null ? "" : DateUtil.formatDate(settlementPaymentTime, "yyyy-MM-dd HH:mm");
	}

	public void setSettlementPaymentTime(Date settlementPaymentTime) {
		this.settlementPaymentTime = settlementPaymentTime;
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
}
