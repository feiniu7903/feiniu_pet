package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class ProdRelation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2964452984525875074L;

	private Long relationId;

    private Long productId;

    private Long relatProductId;
    
    private Long proGroupId;
    
    private String saleNumType;//销售数量类型

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

	public Long getRelatProductId() {
        return relatProductId;
    }

    public void setRelatProductId(Long relatProductId) {
        this.relatProductId = relatProductId;
    }
    
    public Long getProGroupId() {
		return proGroupId;
	}

	public void setProGroupId(Long proGroupId) {
		this.proGroupId = proGroupId;
	}

	public String getSaleNumType() {
		return saleNumType;
	}

	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}
	
	
}