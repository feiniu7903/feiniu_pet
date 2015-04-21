package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.pet.po.sup.SupSupplier;

public class EplaceSupplier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1619179395106280344L;
	/**
	 * E景通供应商编号.
	 */
	private Long eplaceSupplierId;
	/**
	 * 供应商编号.
	 */
	private Long supplierId;
	/**
	 * 
	 */
	private String supplierName;
	/**
	 * 状态.(开通：OPEN;关闭：CLOSE;正常：NORMAL)
	 */
	private String status;
	/**
	 * 产品经理.
	 */
	private String productManager;
	/**
	 * 联系方式是否可见.
	 */
	private String customerVisible;
	/**
	 * 联系方式.
	 */
	private String mobile;
	/**
	 * 创建时间.
	 */
	private Date createDate;
	/**
	 * 供应商对象.
	 */
	private SupSupplier supSupplier;

	private String openlinkVisible = "false";
	private String editlinkVisible = "false";
	private String closelinkVisible = "false";

	public Long getEplaceSupplierId() {
		return eplaceSupplierId;
	}

	public void setEplaceSupplierId(Long eplaceSupplierId) {
		this.eplaceSupplierId = eplaceSupplierId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductManager() {
		return productManager;
	}

	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}

	public String getCustomerVisible() {
		return customerVisible;
	}

	public void setCustomerVisible(String customerVisible) {
		this.customerVisible = customerVisible;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public SupSupplier getSupSupplier() {
		return supSupplier;
	}

	public void setSupSupplier(SupSupplier supSupplier) {
		this.supSupplier = supSupplier;
	}
	public String getOpenlinkVisible() {
		String visible = "false";
		if (this.eplaceSupplierId == null) {
			visible = "true";
		}
		return visible;
	}

	public String getEditlinkVisible() {
		String visible = "false";
		if (this.eplaceSupplierId != null) {
			visible = "true";
		}
		return visible;
	}

	public String getCloselinkVisible() {
		String visible = "false";
		if (this.eplaceSupplierId != null&&!"CLOSE".equalsIgnoreCase(this.status)) {
			visible = "true";
		}
		return visible;
	}
	public boolean isCustomerVisible(){
		 boolean flag=false;
		 if("true".equalsIgnoreCase(this.customerVisible.trim())){
			 flag=true;
		 }
		 return flag;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}