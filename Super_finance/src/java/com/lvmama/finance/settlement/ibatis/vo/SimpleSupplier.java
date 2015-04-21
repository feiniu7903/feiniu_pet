package com.lvmama.finance.settlement.ibatis.vo;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;



/**
 * 供应商押金、预存款、担保函信息
 * 
 * @author yanggan
 *
 */
public class SimpleSupplier {
	
	/**
	 * 供应商ID
	 */
	public Integer supplierId;
	
	/**
	 * 供应商名称
	 */
	public String supplierName;
	
	/**
	 * 押金金额
	 */
	public Double foregiftsBal;
	/**
	 * 押金预警时间
	 */
	public Date foregiftsAlert;
	
	
	/**
	 * 担保函额度
	 */
	public Double guaranteeLimit;
	
	/**
	 * 预存款金额
	 */
	public Double advancedepositsBal;
	
	/**
	 * 预存款预警值
	 */
	public Double advancedepositsAlert;
		
	/**
	 * 预存款总额
	 */
	public Double advancedepositsAll;
	
	/**
	 * 预存款结算总额
	 */
	public Double advancedepositsPay;
	
	/**
	 * 预存款币种中文 名称
	 */
	public String currencyName;

	/**
	 * 预存款币种
	 */
	public String currency;
	
	/**
	 * 押金币种
	 */
	public String foreCurrency;
	
	public String getForeCurrency() {
		return foreCurrency;
	}

	public void setForeCurrency(String foreCurrency) {
		this.foreCurrency = foreCurrency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Double getForegiftsBal() {
		return foregiftsBal;
	}

	public void setForegiftsBal(Double foregiftsBal) {
		this.foregiftsBal = foregiftsBal;
	}

	public Double getAdvancedepositsBal() {
		return advancedepositsBal;
	}

	public void setAdvancedepositsBal(Double advancedepositsBal) {
		this.advancedepositsBal = advancedepositsBal;
	}

	public Double getGuaranteeLimit() {
		return guaranteeLimit;
	}

	public void setGuaranteeLimit(Double guaranteeLimit) {
		this.guaranteeLimit = guaranteeLimit;
	}

	public Double getAdvancedepositsAll() {
		return advancedepositsAll;
	}

	public void setAdvancedepositsAll(Double advancedepositsAll) {
		this.advancedepositsAll = advancedepositsAll;
	}

	public Double getAdvancedepositsPay() {
		return advancedepositsPay;
	}

	public void setAdvancedepositsPay(Double advancedepositsPay) {
		this.advancedepositsPay = advancedepositsPay;
	}

	public Date getForegiftsAlert() {
		return foregiftsAlert;
	}

	public String getForegiftsAlertStr() {
		if(foregiftsAlert!=null){
			return DateUtil.getFormatDate(foregiftsAlert, "yyyy-MM-dd");
		}
		return "";
	}
	
	public void setForegiftsAlert(Date foregiftsAlert) {
		this.foregiftsAlert = foregiftsAlert;
	}

	public Double getAdvancedepositsAlert() {
		return advancedepositsAlert;
	}

	public void setAdvancedepositsAlert(Double advancedepositsAlert) {
		this.advancedepositsAlert = advancedepositsAlert;
	}


	
}
