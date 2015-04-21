package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

/**
 * @since 2013-09-24
 */
public class EbkProdContent implements Serializable {
	
	/**自助巴士班旅游服务保障**/
	public static final String SERVICE_SELFHELP_BUS="SERVICE_SELFHELP_BUS";
	
	/**短途跟团游旅游服务保障**/
	public static final String SERVICE_GROUP="SERVICE_GROUP";

    private static final long serialVersionUID = 138001284807674177L;

    /**
     * column EBK_PROD_CONTENT.CONTENT_ID
     */
    private Long contentId;

    /**
     * column EBK_PROD_CONTENT.PRODUCT_ID
     */
    private Long productId;

    /**
     * column EBK_PROD_CONTENT.CONTENT_TYPE
     */
    private String contentType;

    /**
     * column EBK_PROD_CONTENT.CONTENT
     */
    private String content;
    
    /**产品大类**/
    private String productType;
    
    /**产品小类**/
    private String subProductType;
    
    private Long multiJourneyId;
    

    public Long getMultiJourneyId() {
		return multiJourneyId;
	}

	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}

	public EbkProdContent() {
        super();
    }

    public EbkProdContent(Long contentId, Long productId, String contentType, String content) {
        this.contentId = contentId;
        this.productId = productId;
        this.contentType = contentType;
        this.content = content;
    }

    /**
     * getter for Column EBK_PROD_CONTENT.CONTENT_ID
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * setter for Column EBK_PROD_CONTENT.CONTENT_ID
     * @param contentId
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * getter for Column EBK_PROD_CONTENT.PRODUCT_ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * setter for Column EBK_PROD_CONTENT.PRODUCT_ID
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * getter for Column EBK_PROD_CONTENT.CONTENT_TYPE
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * setter for Column EBK_PROD_CONTENT.CONTENT_TYPE
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * getter for Column EBK_PROD_CONTENT.CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * setter for Column EBK_PROD_CONTENT.CONTENT
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
    
    

}