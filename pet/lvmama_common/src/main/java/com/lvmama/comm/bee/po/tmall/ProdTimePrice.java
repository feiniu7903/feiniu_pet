package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;
import java.util.Date;

public class ProdTimePrice implements Serializable {
	private static final long serialVersionUID = 5479694076687358041L;
	/**
	 * 标识
	 */
	private Long timePriceId;
	/**
	 * 游玩时间
	 */
	private Date specDate;
	private Long productId;
	private Long price;
	private Long prodBranchId;
	private Long cancelHour;
	private Long aheadHour;
	private String priceType;
	private Long ratePrice;
	private Long fixedAddPrice;
	private String cancelStrategy;
	private String productType;
	private Long multiJourneyId;
	
	// 库存
	private Long dayStock;
	
	public Long getTimePriceId() {
		return timePriceId;
	}
	public void setTimePriceId(Long timePriceId) {
		this.timePriceId = timePriceId;
	}
	public Date getSpecDate() {
		return specDate;
	}
	public void setSpecDate(Date specDate) {
		this.specDate = specDate;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getProdBranchId() {
		return prodBranchId;
	}
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}
	public Long getCancelHour() {
		return cancelHour;
	}
	public void setCancelHour(Long cancelHour) {
		this.cancelHour = cancelHour;
	}
	public Long getAheadHour() {
		return aheadHour;
	}
	public void setAheadHour(Long aheadHour) {
		this.aheadHour = aheadHour;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public Long getRatePrice() {
		return ratePrice;
	}
	public void setRatePrice(Long ratePrice) {
		this.ratePrice = ratePrice;
	}
	public Long getFixedAddPrice() {
		return fixedAddPrice;
	}
	public void setFixedAddPrice(Long fixedAddPrice) {
		this.fixedAddPrice = fixedAddPrice;
	}
	public String getCancelStrategy() {
		return cancelStrategy;
	}
	public void setCancelStrategy(String cancelStrategy) {
		this.cancelStrategy = cancelStrategy;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Long getMultiJourneyId() {
		return multiJourneyId;
	}
	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}
	public Long getDayStock() {
		return dayStock;
	}
	public void setDayStock(Long dayStock) {
		this.dayStock = dayStock;
	}

}
