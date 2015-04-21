package com.lvmama.comm.bee.po.duijie;

import java.io.Serializable;

/**
 * 供应商产品 
 * @author yanzhirong
 */
public class SupplierProd implements Serializable{
	private static final long serialVersionUID = 6539700191066869480L;
	
	/** 主键id */
	private Long id;

	/** 供应商id */
	private Long supplierId;
	/** 供应商产品Id */
	private String supplierProdId;
	/** 驴妈妈销售产品id */
	private Long lvmamaProdId;
	/** 供应商产品名称 */
	private String supplierProdName;
	/** 目的地 */
	private String destinationCity;
	/** 供应商产品类型*/
	private String supplierProductType;
	/** 供应商渠道*/
	private String supplierChannel;
	/** 是否入库*/
	private Integer valid = new Integer(0);
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierProdId() {
		return supplierProdId;
	}
	public void setSupplierProdId(String supplierProdId) {
		this.supplierProdId = supplierProdId;
	}
	public Long getLvmamaProdId() {
		return lvmamaProdId;
	}
	public void setLvmamaProdId(Long lvmamaProdId) {
		this.lvmamaProdId = lvmamaProdId;
	}
	public String getSupplierProdName() {
		return supplierProdName;
	}
	public void setSupplierProdName(String supplierProdName) {
		this.supplierProdName = supplierProdName;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getSupplierProductType() {
		return supplierProductType;
	}
	public void setSupplierProductType(String supplierProductType) {
		this.supplierProductType = supplierProductType;
	}
	public String getSupplierChannel() {
		return supplierChannel;
	}
	public void setSupplierChannel(String supplierChannel) {
		this.supplierChannel = supplierChannel;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}	
	
}
