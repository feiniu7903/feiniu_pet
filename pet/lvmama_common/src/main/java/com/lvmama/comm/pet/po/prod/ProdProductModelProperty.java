package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdProductModelProperty implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -985516260897560411L;
	private long productId;
	private long modelPropertyId;
	private String isMaintain;
	public String getIsMaintain() {
		return isMaintain;
	}
	public void setIsMaintain(String isMaintain) {
		this.isMaintain = isMaintain;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getModelPropertyId() {
		return modelPropertyId;
	}
	public void setModelPropertyId(long modelPropertyId) {
		this.modelPropertyId = modelPropertyId;
	}
	
}

