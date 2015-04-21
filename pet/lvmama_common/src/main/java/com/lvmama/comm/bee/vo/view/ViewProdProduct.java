package com.lvmama.comm.bee.vo.view;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;

public class ViewProdProduct implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5912910521563482153L;
    private Long productId;
    private String bizcode;
    private String productName;
    private String description;
    private Date createTime;
    private String additional;
    private Date onlineTime;
    private Date offlineTime;
    private Integer aheadHour;
    private String productType;
    private String smsContent;
    private String wrapPage;
    private Long stock;
    private Long marketPrice;
    private String priceUnit;
    private Long sellPrice;
    private String payToLvmama;
    private Long cashRefund;
    private String payToSupplier;
    /** 产品默认图片 */ 
    private String smallImage;
    /**
     * 子类型.
     */
    private String subProductType;
    /**
     * 是否需要物流
     */
    private String physical="false";
    
    /**
     * 酒店房型专有信息
     * @return
     */
    private String broadband;//宽带
    private String bed;//床形
    private String saleNumType;
    private Date lastCancelTime;//最晚能取消的时间
    public String getPhysical() {
        return physical;
    }
    public void setPhysical(String physical) {
        this.physical = physical;
    }
    /**
     * getSubProductType.
     *
     * @return 子类型
     */
    public String getSubProductType() {
        return subProductType;
    }
    
    private String couponAble;
    
    
    public String getSubProductTypeStr() {
        if("INSURE".equals(this.subProductType)){
            return "保险";
        }else if(this.subProductType!=null){
            return "其他";
        }
        return subProductType;
    }
    /**
     * setSubProductType.
     *
     * @param subProductType
     *            子类型
     */
    public void setSubProductType(final String subProductType) {
        this.subProductType = subProductType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBizcode() {
        return bizcode;
    }

    public void setBizcode(String bizcode) {
        this.bizcode = bizcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description == null ? "费用包含：\n费用不包含：\n行程安排：\n购物说明" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isAdditional() {
        return Boolean.parseBoolean(additional);
    }

    public String getAdditional() {
        return additional == null ? "false" : additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getViewOnlineTime() {
        return onlineTime == null ? "" : DateUtil.getFormatDate(onlineTime, "yyyy-MM-dd");
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getViewOfflineTime() {
        return offlineTime == null ? "" : DateUtil.getFormatDate(offlineTime, "yyyy-MM-dd");
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public Integer getAheadHour() {
        return aheadHour;
    }

    public void setAheadHour(Integer aheadHour) {
        this.aheadHour = aheadHour;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getZhProductType() {
        return this.productType;//Constant.getInstance().getChineseStr(Constant.CODE_TYPE.PRODUCT_TYPE, productType);
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public float getSellPriceFloat() {
        if(this.sellPrice!=null) return PriceUtil.convertToYuan(this.sellPrice);
        return 0;
    }

    public float getSellPriceYuan() {
        return sellPrice==null?0:PriceUtil.convertToYuan(sellPrice);
    }

    public float getMarketPriceYuan() {
        return marketPrice==null?0:PriceUtil.convertToYuan(marketPrice);
    }

    public float getDiscount() {
        if (this.getMarketPrice() > 0) {
            float dis = (float)this.sellPrice / (float)this.marketPrice;
            DecimalFormat df = new DecimalFormat("#.#");
            return new Float(df.format(dis * 10));
        } else {
            return 0;
        }
    }

    public String getWrapPage() {
        return wrapPage;
    }

    public void setWrapPage(String wrapPage) {
        this.wrapPage = wrapPage;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    /**
     * 检查产品是否可售,如果当前时间在产品上线时间之后、下线时间之前则可售
     * @return
     */
    public boolean isSellable() {
        if (onlineTime != null && offlineTime != null) {
            Date now = new Date();
            if (now.after(onlineTime) && now.before(offlineTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDisabled() {
        if (sellPrice==null) {
            return true;
        }else if (sellPrice<=0){
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ViewProdProduct) {
            ViewProdProduct cc = (ViewProdProduct) obj;
            if (productId == null) {
                return cc.getProductId() == null;
            } else {
                return productId.equals(cc.getProductId());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (productId != null)
            return productId.hashCode();
        else
            return 0;
    }

    @Override
    public String toString() {
        if (productId != null)
            return "ProdProduct" + productId.toString();
        else
            return "ProdProduct_null";
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }
    
    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getPayToLvmama() {
        return payToLvmama;
    }
    public void setPayToLvmama(String payToLvmama) {
        this.payToLvmama = payToLvmama;
    }
    public String getPayToSupplier() {
        return payToSupplier;
    }
    public void setPayToSupplier(String payToSupplier) {
        this.payToSupplier = payToSupplier;
    }
    public Long getCashRefund() {
        return cashRefund;
    }
    public float getCashRefundFloat(){
        if(this.cashRefund>0){
            return this.cashRefund/100;    
        }
        return 0;
    }
    public void setCashRefund(Long cashRefund) {
        this.cashRefund = cashRefund;
    }
    public String getSmallImage() {
        return smallImage;
    }
    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }
    public String getBroadband() {
        return broadband;
    }
    public void setBroadband(String broadband) {
        this.broadband = broadband;
    }
    public String getBed() {
        return bed;
    }
    public void setBed(String bed) {
        this.bed = bed;
    }
    public Integer getMarkPriceInteger(){
        Integer price = 0;
        if(this.marketPrice!=null){
            price =  Integer.valueOf(marketPrice.toString());
        }
        return price/100;
    }
    
    public Integer getSellPriceInteger(){
        Integer price = 0;
        if(this.sellPrice!=null){
            price =  Integer.valueOf(sellPrice.toString());
        }
        return price/100;
    }
    public String getSaleNumType() {
        return saleNumType;
    }
    public void setSaleNumType(String saleNumType) {
        this.saleNumType = saleNumType;
    }
    public String getCouponAble() {
        return couponAble;
    }
    public void setCouponAble(String couponAble) {
        this.couponAble = couponAble;
    }
    /**
     * @return the lastCancelTime
     */
    public Date getLastCancelTime() {
        return lastCancelTime;
    }
    /**
     * @param lastCancelTime the lastCancelTime to set
     */
    public void setLastCancelTime(Date vistTime,Integer lastCancelHour) {
        if(vistTime==null||lastCancelHour==null){
            lastCancelTime=null;
        }else{
            this.lastCancelTime = DateUtils.addMinutes(vistTime, -lastCancelHour);
        }
    }
    
    
}
