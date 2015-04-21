package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class ProdHotSellSeq implements Serializable {
    private static final long serialVersionUID = 73743886319838963L;
    
    private Long prodHotSellId;
    private Long productId;
    private String productName;
    private String description;
    private Long sellPrice;
    private Long marketPrice;
    private String prodType;
    private String subProdType;
    private String imgUrl;
    private Long orderQuantity;
    private String channel;
    private String baseChannel;
    
    
    public Long getProdHotSellId() {
        return prodHotSellId;
    }
    public void setProdHotSellId(Long prodHotSellId) {
        this.prodHotSellId = prodHotSellId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }
    public Long getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getProdType() {
        return prodType;
    }
    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
    public String getSubProdType() {
        return subProdType;
    }
    public void setSubProdType(String subProdType) {
        this.subProdType = subProdType;
    }
    public Long getOrderQuantity() {
        return orderQuantity;
    }
    public void setOrderQuantity(Long orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getBaseChannel() {
        return baseChannel;
    }
    public void setBaseChannel(String baseChannel) {
        this.baseChannel = baseChannel;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
}
