package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

public class ComCurrency implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4443829240721287440L;
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
