package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

/**
 * @since 2013-09-24
 */
public class EbkProdRejectInfo implements Serializable {

    private static final long serialVersionUID = 138001285423391438L;

    /**
     * column EBK_PROD_REJECT_INFO.REJECT_INFO_ID
     */
    private Long rejectInfoId;

    /**
     * column EBK_PROD_REJECT_INFO.PRODUCT_ID
     */
    private Long productId;

    /**
     * column EBK_PROD_REJECT_INFO.TYPE
     */
    private String type;

    /**
     * column EBK_PROD_REJECT_INFO.MESSAGE
     */
    private String message;

    public String getTypeCh(){
    	return Constant.EBK_AUDIT_TABS_NAME.getCnNameByCode(type);
    }
    public EbkProdRejectInfo() {
        super();
    }

    public EbkProdRejectInfo(Long rejectInfoId, Long productId, String type, String message) {
        this.rejectInfoId = rejectInfoId;
        this.productId = productId;
        this.type = type;
        this.message = message;
    }

    /**
     * getter for Column EBK_PROD_REJECT_INFO.REJECT_INFO_ID
     */
    public Long getRejectInfoId() {
        return rejectInfoId;
    }

    /**
     * setter for Column EBK_PROD_REJECT_INFO.REJECT_INFO_ID
     * @param rejectInfoId
     */
    public void setRejectInfoId(Long rejectInfoId) {
        this.rejectInfoId = rejectInfoId;
    }

    /**
     * getter for Column EBK_PROD_REJECT_INFO.PRODUCT_ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * setter for Column EBK_PROD_REJECT_INFO.PRODUCT_ID
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * getter for Column EBK_PROD_REJECT_INFO.TYPE
     */
    public String getType() {
        return type;
    }

    /**
     * setter for Column EBK_PROD_REJECT_INFO.TYPE
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for Column EBK_PROD_REJECT_INFO.MESSAGE
     */
    public String getMessage() {
        return message;
    }

    /**
     * setter for Column EBK_PROD_REJECT_INFO.MESSAGE
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}