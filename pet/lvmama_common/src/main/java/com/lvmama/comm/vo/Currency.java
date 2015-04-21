package com.lvmama.comm.vo;

import java.io.Serializable;

public class Currency implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -774030780215378313L;
	private Long id;
	private String currency;
	private String currencyName;
	private String symbol;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
