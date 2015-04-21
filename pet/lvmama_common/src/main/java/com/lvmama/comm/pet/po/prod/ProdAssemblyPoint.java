package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdAssemblyPoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801491402657197139L;
	private Long assemblyPointId;
	private Long productId;
	private String assemblyPoint;
	public Long getAssemblyPointId() {
		return assemblyPointId;
	}
	public void setAssemblyPointId(Long assemblyPointId) {
		this.assemblyPointId = assemblyPointId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getAssemblyPoint() {
		return assemblyPoint;
	}
	public void setAssemblyPoint(String assemblyPoint) {
		this.assemblyPoint = assemblyPoint;
	}
	
	
}
