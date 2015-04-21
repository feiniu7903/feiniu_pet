package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

/**
 * @since 2013-09-24
 */
public class EbkProdTarget implements Serializable {

    private static final long serialVersionUID = 138001285615505072L;

    /**
     * column EBK_PROD_TARGET.PRODUCT_TARGET_ID
     */
    private Long productTargetId;

    /**
     * column EBK_PROD_TARGET.TARGET_ID
     */
    private Long targetId;

    /**
     * column EBK_PROD_TARGET.PRODUCT_ID
     */
    private Long productId;

    /**
     * column EBK_PROD_TARGET.TARGET_TYPE
     */
    private String targetType;

    /**
     * 对象名称
     */
    private String targetName;

    public EbkProdTarget() {
        super();
    }

    public EbkProdTarget(Long productTargetId, Long targetId, Long productId, String targetType) {
        this.productTargetId = productTargetId;
        this.targetId = targetId;
        this.productId = productId;
        this.targetType = targetType;
    }

    /**
     * getter for Column EBK_PROD_TARGET.PRODUCT_TARGET_ID
     */
    public Long getProductTargetId() {
        return productTargetId;
    }

    /**
     * setter for Column EBK_PROD_TARGET.PRODUCT_TARGET_ID
     * @param productTargetId
     */
    public void setProductTargetId(Long productTargetId) {
        this.productTargetId = productTargetId;
    }

    /**
     * getter for Column EBK_PROD_TARGET.TARGET_ID
     */
    public Long getTargetId() {
        return targetId;
    }

    /**
     * setter for Column EBK_PROD_TARGET.TARGET_ID
     * @param targetId
     */
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * getter for Column EBK_PROD_TARGET.PRODUCT_ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * setter for Column EBK_PROD_TARGET.PRODUCT_ID
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * getter for Column EBK_PROD_TARGET.TARGET_TYPE
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * setter for Column EBK_PROD_TARGET.TARGET_TYPE
     * @param targetType
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

}