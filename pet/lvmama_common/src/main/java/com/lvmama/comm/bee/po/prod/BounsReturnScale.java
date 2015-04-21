package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class BounsReturnScale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7985382508913218966L;

	private Long bounsScaleId;
	
	private String productType;
	
	private String productSubType;
	
	private Integer scale;

	public Long getBounsScaleId() {
		return bounsScaleId;
	}

	public void setBounsScaleId(Long bounsScaleId) {
		this.bounsScaleId = bounsScaleId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}
}
