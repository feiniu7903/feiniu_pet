package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Date;



public class ViewTimePrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2268866319172625310L;
	protected long timePriceId;
	protected Date specDate;
	private Float priceYuan;
	private long dayStock;
	
	private float priceF;
	private long marketPriceF;
	private float settlementPriceF;
	
	public long getTimePriceId() {
		return timePriceId;
	}
	public void setTimePriceId(long timePriceId) {
		this.timePriceId = timePriceId;
	}
	public Float getPriceYuan() {
		return priceYuan;
	}
	public void setPriceYuan(Float priceYuan) {
		this.priceYuan = priceYuan;
	}
	public long getDayStock() {
		return dayStock;
	}
	public void setDayStock(long dayStock) {
		this.dayStock = dayStock;
	}
	public Date getSpecDate() {
		return specDate;
	}
	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}
	public float getPriceF() {
		return priceF;
	}
	public void setPriceF(float priceF) {
		this.priceF = priceF;
	}
	public long getMarketPriceF() {
		return marketPriceF;
	}
	public void setMarketPriceF(long marketPriceF) {
		this.marketPriceF = marketPriceF;
	}
	public float getSettlementPriceF() {
		return settlementPriceF;
	}
	public void setSettlementPriceF(float settlementPriceF) {
		this.settlementPriceF = settlementPriceF;
	}

}
