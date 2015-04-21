package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.bee.po.prod.ViewPage;


public class DistributionPlaceProduct implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8303328860642458502L;
	private String placeId;
	private String placeName;
	private String url;
	private Long marketPrice;
	private Long sellPrice;
	private String productId;
	private String productName;
	private String subProductType;
	private Date onlineTime;
	private Date offlineTime;
	private String departure;
	private String arrive;
	private String days;
	private ViewPage viewPage;
	private String info;//卖点
	private String imgsrc;
	private Integer sales;//产品销售数
	private Long commcount;//产品评论数
	private Float grade;//产品评分

	
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getArrive() {
		return arrive;
	}
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
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

	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	public Long getCommcount() {
		return commcount;
	}
	public void setCommcount(Long commcount) {
		this.commcount = commcount;
	}
	public Float getGrade() {
		return grade;
	}
	public void setGrade(Float grade) {
		this.grade = grade;
	}
	
}
