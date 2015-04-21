package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;

public class SupplierRelateProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8069461643610001780L;
	/**
	 * 主键.
	 */
	private Long supplierRelateProductId;
	/**
	 * 采购产品编号.
	 */
	private Long metaProductId;
	
	/**
	 * 采购产品类别编号
	 */
	private Long metaProductBranchId;
	/**
	 * E景通供应商编号.
	 */
	private Long eplaceSupplierId;
	private boolean checked = false;
	/**
	 * 采购产品对像.
	 * @return
	 */
	private MetaProduct metaProduct;
	
	/**
	 * 采购产品类别
	 * @return
	 */
	private MetaProductBranch metaProductBranch;
	
	public Long getSupplierRelateProductId() {
		return supplierRelateProductId;
	}

	public void setSupplierRelateProductId(Long supplierRelateProductId) {
		this.supplierRelateProductId = supplierRelateProductId;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public Long getEplaceSupplierId() {
		return eplaceSupplierId;
	}

	public void setEplaceSupplierId(Long eplaceSupplierId) {
		this.eplaceSupplierId = eplaceSupplierId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public MetaProduct getMetaProduct() {
		return metaProduct;
	}

	public void setMetaProduct(MetaProduct metaProduct) {
		this.metaProduct = metaProduct;
	}

	public Long getMetaProductBranchId() {
		return metaProductBranchId;
	}

	public void setMetaProductBranchId(Long metaProductBranchId) {
		this.metaProductBranchId = metaProductBranchId;
	}

	public MetaProductBranch getMetaProductBranch() {
		return metaProductBranch;
	}

	public void setMetaProductBranch(MetaProductBranch metaProductBranch) {
		this.metaProductBranch = metaProductBranch;
	}
	

}