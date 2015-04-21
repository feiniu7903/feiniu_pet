package com.lvmama.comm.vo.duijie;

import java.io.Serializable;

/**
 *  供应商对接传值对象 
 * @author yanzhirong
 */
public class SupplierVO implements Serializable{

	private static final long serialVersionUID = -3999664281212402289L;
	
	private Long supplierId;
	
	private String supplierName;

	public SupplierVO(){
	}
	
	public SupplierVO(Long supplierId, String supplierName) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
	}


	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
}
