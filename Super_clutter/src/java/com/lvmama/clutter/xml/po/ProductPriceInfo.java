package com.lvmama.clutter.xml.po;

public class ProductPriceInfo {
	private Long productId;
	private String marketPrice;
	private String sellPrice;
	private Integer mininum;
	private Integer maxinum;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Integer getMininum() {
		return mininum;
	}
	public void setMininum(Integer mininum) {
		this.mininum = mininum;
	}
	public Integer getMaxinum() {
		return maxinum;
	}
	public void setMaxinum(Integer maxinum) {
		this.maxinum = maxinum;
	}
	
}
