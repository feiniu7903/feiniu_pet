package com.lvmama.tnt.order.vo;

import java.io.Serializable;

import com.lvmama.tnt.comm.util.PriceUtil;

public class TntProductRelation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4768535218051854580L;

	private Long relationId;

	private String saleNumType;

	private Long productId;

	private Long relatProductId;

	private String branchType;

	private Long prodBranchId;

	private String subProductType;

	private String branchName;

	private String productName;

	private Long sellPrice;

	private Long marketPrice;

	private Long minimum;

	private Long maximum;

	private Long stock;

	private boolean payToLvmama;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;

	public Long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getRelatProductId() {
		return relatProductId;
	}

	public void setRelatProductId(Long relatProductId) {
		this.relatProductId = relatProductId;
	}

	/**
	 * @return the saleNumType
	 */
	public String getSaleNumType() {
		return saleNumType;
	}

	/**
	 * @param saleNumType
	 *            the saleNumType to set
	 */
	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public Long getProdBranchId() {
		return prodBranchId;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public float getSellPriceYuan() {
		return sellPrice == null ? 0 : PriceUtil.convertToYuan(sellPrice);
	}

	public int getSellPriceInt() {
		return sellPrice == null ? 0 : PriceUtil.convertToYuanInt(sellPrice);
	}

	public String getDescriptionHtml() {
		if (description != null) {
			return description.replace("\n", "<br/>");
		}
		return null;
	}

	public Long getMinimum() {
		return minimum;
	}

	public void setMinimum(Long minimum) {
		this.minimum = minimum;
	}

	public Long getMaximum() {
		return maximum;
	}

	public void setMaximum(Long maximum) {
		this.maximum = maximum;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public float getMarketPriceYuan() {
		return marketPrice == null ? 0 : PriceUtil.convertToYuan(marketPrice);
	}

	public boolean isPayToLvmama() {
		return payToLvmama;
	}

	public void setPayToLvmama(boolean payToLvmama) {
		this.payToLvmama = payToLvmama;
	}
}