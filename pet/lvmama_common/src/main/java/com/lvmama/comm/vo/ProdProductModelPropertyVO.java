package com.lvmama.comm.vo;

import java.io.Serializable;

public class ProdProductModelPropertyVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 968465878776937060L;
	private long id;
	private long secondModelId;
	private String property;
	private String isMaintain;
	public String getIsMaintain() {
		return isMaintain;
	}

	public void setIsMaintain(String isMaintain) {
		this.isMaintain = isMaintain;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public long getSecondModelId() {
		return secondModelId;
	}

	public void setSecondModelId(long secondModelId) {
		this.secondModelId = secondModelId;
	}
	
}
