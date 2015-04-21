package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;

public class ProdProductBranchItem implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3971670451112431417L;

	private Long branchItemId;

    private Date createTime;

    private Long prodBranchId;

    private Long metaBranchId;
    private MetaProductBranch metaBranch;
    private MetaProduct metaProduct;
    private Long quantity;

    private Long metaProductId;
    
    public Long getBranchItemId() {
        return branchItemId;
    }

    public void setBranchItemId(Long branchItemId) {
        this.branchItemId = branchItemId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getProdBranchId() {
        return prodBranchId;
    }

    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }

    public Long getMetaBranchId() {
        return metaBranchId;
    }

    public void setMetaBranchId(Long metaBranchId) {
        this.metaBranchId = metaBranchId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	/**
	 * @return the metaBranch
	 */
	public MetaProductBranch getMetaBranch() {
		return metaBranch;
	}

	/**
	 * @param metaBranch the metaBranch to set
	 */
	public void setMetaBranch(MetaProductBranch metaBranch) {
		this.metaBranch = metaBranch;
	}

	/**
	 * @return the metaProduct
	 */
	public MetaProduct getMetaProduct() {
		return metaProduct;
	}

	/**
	 * @param metaProduct the metaProduct to set
	 */
	public void setMetaProduct(MetaProduct metaProduct) {
		this.metaProduct = metaProduct;
	}

    
}