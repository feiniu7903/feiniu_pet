package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;

import com.lvmama.comm.utils.PriceUtil;

public class Place implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String pinyin;
	private Float longitude;
	private Float latitude;
	private String name;
	private Integer stage;
	private String juli;
	private Float avgScore;
	private Long cmtNum;
	private String middleImage;
	private Long marketPrice;
	private Float marketPriceYuan;
	private Long sellPrice;
	private Float sellPriceYuan;
	private String placeMainTitel;
	private String hotelType;
	private String placeTitel;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStage() {
		return stage;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}
	public String getJuli() {
		return juli;
	}
	public void setJuli(String juli) {
		this.juli = juli;
	}
	public Float getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}
	public Long getCmtNum() {
		return cmtNum;
	}
	public void setCmtNum(Long cmtNum) {
		this.cmtNum = cmtNum;
	}
	public String getMiddleImage() {
		return middleImage;
	}
	public void setMiddleImage(String middleImage) {
		this.middleImage = middleImage;
	}
	public Long getMarketPrice() {
		return marketPrice;
	}
	public Float getMarketPriceYuan() {
		return this.marketPriceYuan;
	}
	public void setMarketPriceYuan(Float marketPriceYuan) {
		this.marketPriceYuan = marketPriceYuan;
	}
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
		this.marketPriceYuan = PriceUtil.convertToYuan(this.marketPrice);
	}
	public Long getSellPrice() {
		return sellPrice;
	}
	public void setSellPriceYuan(Float sellPriceYuan) {
		this.sellPriceYuan = sellPriceYuan;
	}
	
	public Float getSellPriceYuan() {
		return this.sellPriceYuan;
	}
	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
		this.sellPriceYuan = PriceUtil.convertToYuan(this.sellPrice);
	}
	public String getPlaceMainTitel() {
		return placeMainTitel;
	}
	public void setPlaceMainTitel(String placeMainTitel) {
		this.placeMainTitel = placeMainTitel;
	}
	public String getHotelType() {
		return hotelType;
	}
	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}
	public String getPlaceTitel() {
		return placeTitel;
	}
	public void setPlaceTitel(String placeTitel) {
		this.placeTitel = placeTitel;
	}
	
	
}
