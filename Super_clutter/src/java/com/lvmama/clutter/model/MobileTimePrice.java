package com.lvmama.clutter.model;

import java.io.Serializable;

public class MobileTimePrice implements Serializable{
	
	private String date;
	private Float sellPriceYuan;
	private Float marketPriceYuan;
	protected long dayStock = -2;
	private boolean isSellable;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getDayStock() {
		return dayStock;
	}
	public void setDayStock(long dayStock) {
		this.dayStock = dayStock;
	}
	public boolean isSellable() {
		return isSellable;
	}
	public void setSellable(boolean isSellable) {
		this.isSellable = isSellable;
	}
	public Float getSellPriceYuan() {
		return sellPriceYuan;
	}
	public void setSellPriceYuan(Float sellPriceYuan) {
		this.sellPriceYuan = sellPriceYuan;
	}
	public Float getMarketPriceYuan() {
		return marketPriceYuan;
	}
	public void setMarketPriceYuan(Float marketPriceYuan) {
		this.marketPriceYuan = marketPriceYuan;
	}
}
