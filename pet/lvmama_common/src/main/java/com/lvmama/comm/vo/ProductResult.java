package com.lvmama.comm.vo;

import java.io.Serializable;

public class ProductResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3410481666473189180L;
	private Long productId;
	private Status status=Status.NotFound;
	public static enum Status{
		Find,/*当前产品可取到*/
		Branch,/*类别可取到*/
		NotFound/*找不到*/
	}
	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	
	public boolean isExists(){
		return status!=Status.NotFound;
	}
	
	public boolean isBranch(){
		return status==Status.Branch;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
}

