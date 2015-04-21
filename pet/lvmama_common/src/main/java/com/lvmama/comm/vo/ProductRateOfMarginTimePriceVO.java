package com.lvmama.comm.vo;

public class ProductRateOfMarginTimePriceVO {

	private String subProductType;
	
	private Long prodProductId;
	
	private String prodBranchName;
	
	private String productName;
	
	private String manager;
	
	private Long standardRate;
	
	private String overRateDate;

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public Long getProdProductId() {
		return prodProductId;
	}

	public void setProdProductId(Long prodProductId) {
		this.prodProductId = prodProductId;
	}

	public String getProdBranchName() {
		return prodBranchName;
	}

	public void setProdBranchName(String prodBranchName) {
		this.prodBranchName = prodBranchName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Long getStandardRate() {
		return standardRate;
	}

	public void setStandardRate(Long standardRate) {
		this.standardRate = standardRate;
	}

	public String getOverRateDate() {
		return overRateDate;
	}

	public void setOverRateDate(String overRateDate) {
		this.overRateDate = overRateDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
