package com.lvmama.finance.settlement.ibatis.po;

import java.util.Date;

public class ExchangeRate {
	// ID
	private Integer rateId;
	// 外汇币种
	private String foreignCurrency;
	// 修改日期
	private Date createTime;
	// 汇率
	private Double rate;
	// 币种名称
	private String currencyName;
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public Integer getRateId() {
		return rateId;
	}
	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}
	public String getForeignCurrency() {
		return foreignCurrency;
	}
	public void setForeignCurrency(String foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}

}
