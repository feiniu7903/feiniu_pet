package com.lvmama.finance.settlement.ibatis.po;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SupplierInfo {
	// 供应商名称
	private String supplierName;
	// 我方结算主体
	private String targetName;
	// 手机
	private String telephone;
	// 网址
	private String website;
	// 传真
	private String fax;
	// 地址
	private String address;
	// 邮编
	private String postCode;
	// 座机
	private String mobile;
	// 押金余额
	private Double foregiftsBal;
	// 预存款余额
	private Double advancedepositsBal;
	// 押金预警时间
	private Date foregiftsAlert;
	// 预存款预警金额
	private Double advancedepositsAlert;
	// 担保函金额
	private Double guaranteeLimit;
	// 预存款币种
	private String advCurrency;
	// 押金币种
	private String foreCurrency;
	
	public String getAdvCurrency() {
		return advCurrency;
	}
	public void setAdvCurrency(String advCurrency) {
		this.advCurrency = advCurrency;
	}
	public String getForeCurrency() {
		return foreCurrency;
	}
	public void setForeCurrency(String foreCurrency) {
		this.foreCurrency = foreCurrency;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getForegiftsAlert() {
		String df = "";
		if(null != foregiftsAlert && !"".equals(foregiftsAlert)){
			df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(foregiftsAlert);					
		}
		return df;
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
	public Double getGuaranteeLimit() {
		return guaranteeLimit;
	}
	public void setGuaranteeLimit(Double guaranteeLimit) {
		this.guaranteeLimit = guaranteeLimit;
	}
	
}
