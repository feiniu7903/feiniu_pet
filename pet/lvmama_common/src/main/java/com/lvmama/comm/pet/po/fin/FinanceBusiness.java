package com.lvmama.comm.pet.po.fin;

import java.io.Serializable;

public class FinanceBusiness implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8160846773073696827L;
	private String businessName;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
