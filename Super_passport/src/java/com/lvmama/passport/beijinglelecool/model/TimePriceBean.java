package com.lvmama.passport.beijinglelecool.model;

public class TimePriceBean {

	private String date;//日期
	private String marketPrice;// 市场价格
	private String salePrice;// 售价
	private String remainNum;// 库存
	private String settlementPrice;// 结算价

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(String remainNum) {
		this.remainNum = remainNum;
	}

	public String getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(String settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

}
