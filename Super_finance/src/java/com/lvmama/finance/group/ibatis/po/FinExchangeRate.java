package com.lvmama.finance.group.ibatis.po;

import java.sql.Timestamp;
import java.util.Date;

public class FinExchangeRate implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String foreignCurrency;
	private Date createDate;
	private Double rate;

	public FinExchangeRate() {
	}

	public FinExchangeRate(Long id, String foreignCurrency) {
		this.id = id;
		this.foreignCurrency = foreignCurrency;
	}

	public FinExchangeRate(Long id, String foreignCurrency,
			Timestamp createDate, Double rate) {
		this.id = id;
		this.foreignCurrency = foreignCurrency;
		this.createDate = createDate;
		this.rate = rate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getForeignCurrency() {
		return this.foreignCurrency;
	}

	public void setForeignCurrency(String foreignCurrency) {
		this.foreignCurrency = foreignCurrency;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}