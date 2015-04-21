package com.lvmama.businessreply.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComPicture;

/**
 * 团购产品信息.
 * @author huyunyan
 */
public class EtaoProduct implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2076196786615338699L;
	/**
	 * 团购信息ID
	 */
	private Long groupBuyInfoId;
	/**
	 * 团购商户ID
	 */
	private Long supplierId;
	/**
	 * 城市（通过页面传递）.
	 */
	private String city;
	/**
	 * 销售产品ID.
	 */
	private Long productId;
	/**
	 * 销售产品名称.
	 */
	private String productName;
	/**
	 * 销售价格.
	 */
	private float sellPrice;
	/**
	 * 市场价格.
	 */
	private float marketPrice;
	/**
	 * 折扣（计算sellPrice/marketPrice）.
	 */
	private float disCount;
	/**
	 * 产品经理推荐.
	 */
	private String recommandInfo;
	/**
	 * 标签（最多5个）.
	 */
	private List<String> tags;
	/**
	 * 大图URL.
	 */
	private String pictureUrl;
	/**
	 * 链接地址.
	 */
	private String linkUrl;
	/**
	 * 上线时间.
	 */
	private Date onlineTime;
	/**
	 * 下线时间.
	 */
	private Date offlineTime;
	/**
	 * 已购买人数.
	 */
	private Long salesNum;
	/**
	 * 消费开始时间.
	 */
	private Date groupBuyStartTime;
	/**
	 * 消费结束时间.
	 */
	private Date groupBuyCloseTime;
	/**
	 * 销售产品类型.
	 */
	private String subProductType;
	/**
	 * 团购分类号（百度团购专用）.
	 */
	private String subcategoryBaidu;
	/**
	 * .
	 */
	private String tips;
	/**
	 * 经度(未使用).
	 */
	private String longitude;
	/**
	 * 纬度(未使用).
	 */
	private String latitude;
	
	/**
	 * 是否主打(百度专用).
	 */
	private String majoyBaidu;
	/**
	 * 自定义标题(360专用).
	 */
	private String title360;
	
	/**
	 * 谷歌补充图片
	 */
	private String googlePic;
	
	// setters and getters
	
	private List<ComPicture> pictures;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public float getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(float sellPrice) {
		this.sellPrice = sellPrice;
	}
	public float getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	public float getDisCount() {
		return disCount;
	}
	public void setDisCount(float disCount) {
		this.disCount = disCount;
	}
	public String getRecommandInfo() {
		return recommandInfo;
	}
	public void setRecommandInfo(String recommandInfo) {
		this.recommandInfo = recommandInfo;
	}
	public List<String> getTags() {
		if(this.tags==null || this.tags.isEmpty() ){
			tags = new ArrayList<String>();
			tags.add("旅游");
			tags.add("驴妈妈");
		}
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Date getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	public Date getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}
	
	public Long getSalesNum() {
		return salesNum;
	}
	public void setSalesNum(Long salesNum) {
		this.salesNum = salesNum;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getGroupBuyStartTime() {
		return groupBuyStartTime;
	}
	public void setGroupBuyStartTime(Date groupBuyStartTime) {
		this.groupBuyStartTime = groupBuyStartTime;
	}
	public Date getGroupBuyCloseTime() {
		return groupBuyCloseTime;
	}
	public void setGroupBuyCloseTime(Date groupBuyCloseTime) {
		this.groupBuyCloseTime = groupBuyCloseTime;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public String getSubcategoryBaidu() {
		return subcategoryBaidu;
	}
	public void setSubcategoryBaidu(String subcategoryBaidu) {
		this.subcategoryBaidu = subcategoryBaidu;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getGroupBuyInfoId() {
		return groupBuyInfoId;
	}
	public void setGroupBuyInfoId(Long groupBuyInfoId) {
		this.groupBuyInfoId = groupBuyInfoId;
	}
	public String getMajoyBaidu() {
		return majoyBaidu;
	}
	public void setMajoyBaidu(String majoyBaidu) {
		this.majoyBaidu = majoyBaidu;
	}
	public String getTitle360() {
		return title360;
	}
	public void setTitle360(String title360) {
		this.title360 = title360;
	}
	public String getGooglePic() {
		return googlePic;
	}
	public void setGooglePic(String googlePic) {
		this.googlePic = googlePic;
	}
	public List<ComPicture> getPictures() {
		return pictures;
	}
	public void setPictures(List<ComPicture> pictures) {
		this.pictures = pictures;
	}

}
