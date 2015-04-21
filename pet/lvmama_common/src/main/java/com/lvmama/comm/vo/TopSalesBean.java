package com.lvmama.comm.vo;

import java.io.Serializable;

public class TopSalesBean implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8583661640719220716L;
	private String sellPrice;
    private String productName;
    private String productUrl;
    private String recommendInfo;
    private Long prodBranchId;
    private Long productId;
    private String tagsImage;

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getRecommendInfo() {
        return recommendInfo;
    }

    public void setRecommendInfo(String recommendInfo) {
        this.recommendInfo = recommendInfo;
    }

    public Long getProdBranchId() {
        return prodBranchId;
    }

    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

	public String getTagsImage() {
		return tagsImage;
	}

	public void setTagsImage(String tagsImage) {
		this.tagsImage = tagsImage;
	}

}
