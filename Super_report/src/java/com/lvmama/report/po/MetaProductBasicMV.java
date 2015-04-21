package com.lvmama.report.po;

import com.lvmama.comm.vo.Constant;




public class MetaProductBasicMV {

	private Long metaProductId;

	private String metaProductName;
	
	private String supplierName;
	
	private String metaProductType;
	
	private String sellProductId;
	
	private String sellProductName;
	
	private String sellProductType;
	
	private Long orderQuantity;
	
	private Long sellQuantity;
	
	private Double sellMoney;
	
	private String sellType;
	
	private String metaType;
	
	public String getSellType() {
		return sellType;
	}

	public void setSellType(String sellType) {
		this.sellType = sellType;
	}

	public String getMetaType() {
		return metaType;
	}

	public void setMetaType(String metaType) {
		this.metaType = metaType;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public String getMetaProductName() {
		return metaProductName;
	}

	public void setMetaProductName(String metaProductName) {
		this.metaProductName = metaProductName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getMetaProductType() {
		return metaProductType;
	}

	public void setMetaProductType(String metaProductType) {
		this.metaProductType = metaProductType;
	}

	public String getSellProductId() {
		return sellProductId;
	}

	public void setSellProductId(String sellProductId) {
		this.sellProductId = sellProductId;
	}

	public Long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getSellProductName() {
		return sellProductName;
	}

	public void setSellProductName(String sellProductName) {
		this.sellProductName = sellProductName;
	}

	public String getSellProductType() {
		return sellProductType;
	}

	public void setSellProductType(String sellProductType) {
		this.sellProductType = sellProductType;
	}
	
	public Long getSellQuantity() {
		return sellQuantity;
	}

	public void setSellQuantity(Long sellQuantity) {
		this.sellQuantity = sellQuantity;
	}

	public Double getSellMoney() {
		return sellMoney;
	}

	public void setSellMoney(Double sellMoney) {
		this.sellMoney = sellMoney;
	}
	
	public String getZhMetaProductType() {
		return Constant.PRODUCT_TYPE.getCnName(metaType);
	}
	
	public String getZhSellProductType() {
		return Constant.PRODUCT_TYPE.getCnName(sellType);
	}
}
