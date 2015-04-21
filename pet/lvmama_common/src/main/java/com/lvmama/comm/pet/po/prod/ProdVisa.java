package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdVisa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 203434751709897208L;
	private Long visaId;
	private Long productId;
	public Long getVisaId() {
		return visaId;
	}
	public void setVisaId(Long visaId) {
		this.visaId = visaId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
}
