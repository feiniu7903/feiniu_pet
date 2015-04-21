package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.vo.product.RelatedProduct;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

public class ProdProductRelation  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4768535218051854580L;

	private Long relationId;

    private String saleNumType;
    private Long productId;
    /**
     * @deprecated 随着附件产品移动到pet系统中，此属性应该被废弃。
     */
    private Long prodBranchId;

    private Long relatProductId;
    /**
     * @deprecated 随着附件产品移动到pet系统中，此属性应该被废弃。
     */
    private ProdProductBranch branch;
    /**
     * @deprecated 随着附件产品移动到pet系统中，此属性应该被废弃。
     * @return
     */
    private ProdProduct relationProduct;
    
    private RelatedProduct relatedProduct;
    
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

    /**
     * @deprecated 随着附件产品移动到pet系统中，此方法应该被废弃。
     * @return
     */
    public Long getProdBranchId() {
        return prodBranchId;
    }
    /**
     * @deprecated 随着附件产品移动到pet系统中，此方法应该被废弃。
     * @return
     */
    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }

    public Long getRelatProductId() {
        return relatProductId;
    }

    public void setRelatProductId(Long relatProductId) {
        this.relatProductId = relatProductId;
    }

    /**
     * @deprecated 随着附件产品移动到pet系统中，此方法应该被废弃。
     * @return
     */
	public ProdProductBranch getBranch() {
		return branch;
	}

    /**
     * @deprecated 随着附件产品移动到pet系统中，此方法应该被废弃。
     * @return
     */
	public void setBranch(ProdProductBranch branch) {
		this.branch = branch;
	}

    /**
     * @deprecated 随着附件产品移动到pet系统中，此方法应该被废弃。
     * @return
     */
	public ProdProduct getRelationProduct() {
		return relationProduct;
	}

    /**
     * @deprecated 随着附件产品移动到pet系统中，此方法应该被废弃。
     * @return
     */
	public void setRelationProduct(ProdProduct relationProduct) {
		this.relationProduct = relationProduct;
	}

	/**
	 * @return the saleNumType
	 */
	public String getSaleNumType() {
		return saleNumType;
	}

	/**
	 * @param saleNumType the saleNumType to set
	 */
	public void setSaleNumType(String saleNumType) {
		this.saleNumType = saleNumType;
	}
	public String getSubProductTypeStr(){
		if(StringUtils.equals(relationProduct.getSubProductType(), Constant.SUB_PRODUCT_TYPE.INSURANCE.name())){
			return "保险";
		}else if(StringUtils.equals(relationProduct.getSubProductType(), Constant.SUB_PRODUCT_TYPE.VISA.name())){
			return "签证";
		}else if(StringUtils.equals(branch.getBranchType(), ProductUtil.Route.FANGCHA.name())){
			return "房差";
		}
		else{
			return "其它";
		}
	}
	public String getSubProductTypeStrTwo(){
		if(StringUtils.equals(relationProduct.getSubProductType(), Constant.SUB_PRODUCT_TYPE.INSURANCE.name())){
			return "保险";
		}else if(StringUtils.equals(relationProduct.getSubProductType(), Constant.SUB_PRODUCT_TYPE.TAX.name())){
			return "税金";
		}else if(StringUtils.equals(relationProduct.getSubProductType(), Constant.SUB_PRODUCT_TYPE.EXPRESS.name())){
			return "快递";
		}else if(StringUtils.equals(relationProduct.getSubProductType(), Constant.SUB_PRODUCT_TYPE.OWNEXPENSE.name())){
			return "自费产品";
		}else if(StringUtils.equals(branch.getBranchType(), ProductUtil.Route.FANGCHA.name())){
			return "房差";
		}else{
			return "其它";
		}
	}

	public RelatedProduct getRelatedProduct() {
		return relatedProduct;
	}

	public void setRelatedProduct(RelatedProduct relatedProduct) {
		this.relatedProduct = relatedProduct;
	}
	
	
    
}