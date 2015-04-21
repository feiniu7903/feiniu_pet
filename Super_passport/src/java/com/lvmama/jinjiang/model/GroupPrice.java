package com.lvmama.jinjiang.model;

import java.math.BigDecimal;
/**
 * 价格列表
 * @author chenkeke
 *
 */
public class GroupPrice {
	private String code;
	private String category;
	private BigDecimal salePrice;
	private BigDecimal settlementPrice;
	private String quoteMemo;
	private String type;
	private Boolean activated;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public String getQuoteMemo() {
		return quoteMemo;
	}
	public void setQuoteMemo(String quoteMemo) {
		this.quoteMemo = quoteMemo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getActivated() {
		return activated;
	}
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
}
