package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;

public class UserRelateSupplierProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1450861147325086584L;
	/**
	 * 主键
	 */
	private Long userRelateSupplierProducId;
	/**
	 * 用户编号.
	 */
	private Long passPortUserId;
	/**
	 * 采购产品编号.
	 */
	private Long metaProductId;
	
	/**
	 * 采购产品类别ID
	 */
	private Long metaProductBranchId;
	/**
	 * 采购产品对象.
	 * @return
	 */
	private MetaProduct metaProduct;
	
	/**
	 * 采购产品类别对象
	 */
	private MetaProductBranch metaProductBranch;
	
	/**
	 * 履行对象
	 */
	private SupPerformTarget supPerformTarget;
	
	public Long getUserRelateSupplierProducId() {
		return userRelateSupplierProducId;
	}

	public void setUserRelateSupplierProducId(Long userRelateSupplierProducId) {
		this.userRelateSupplierProducId = userRelateSupplierProducId;
	}

	public Long getPassPortUserId() {
		return passPortUserId;
	}

	public void setPassPortUserId(Long passPortUserId) {
		this.passPortUserId = passPortUserId;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public MetaProduct getMetaProduct() {
		return metaProduct;
	}

	public void setMetaProduct(MetaProduct metaProduct) {
		this.metaProduct = metaProduct;
	}

	public SupPerformTarget getSupPerformTarget() {
		return supPerformTarget;
	}

	public void setSupPerformTarget(SupPerformTarget supPerformTarget) {
		this.supPerformTarget = supPerformTarget;
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