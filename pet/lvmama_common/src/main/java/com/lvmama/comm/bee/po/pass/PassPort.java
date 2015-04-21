package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;

/**
 * 通关点，现在就是履行对象
 * 对应SUP_PERFORM_TARGET表
 */
public class PassPort implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1846298765806630266L;
	private String name;
	private String providerName;
	private Long targetId;
	private Long supplierId;
	private Long providerId;

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}