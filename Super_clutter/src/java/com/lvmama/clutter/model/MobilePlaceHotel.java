package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.List;

public class MobilePlaceHotel implements Serializable {
	private static final long serialVersionUID = -902309513147833835L;
	/** 酒店ID **/
	private String placeId;
	/** 酒店名称 **/
	private String name;
	/** 酒店地址 **/
	private String address;
	/** 酒店类型 **/
	private String placeType;
	/** 图片（列表） **/
	private String images;
	/** 图片（小） **/
	private List<String> smallImages;
	/** 图片（中） **/
	private List<String> middleImages;
	/** 图片（大） **/
	private List<String> largeImages;
	/** 销售价格 **/
	private Double sellPrice;
	/** 距离 **/
	private Double distance;
	/** 点评总数 **/
	private Long commentCount;
	/** 好评率 **/
	private String commentGoodRate;
	/** 纬度 **/
	private String latitude;
	/** 经度 **/
	private String longitude;
	/** 酒店介绍 **/
	private String introEditor;
	/** 酒店描述 **/
	private String description;
	/** 综合设施 **/
	private String generalAmenities;
	/** 房间设施 **/
	private String roomAmenities;
	/** 娱乐设施 **/
	private String recreationAmenities;
	/** 交通状况 **/
	private String traffic;
	/**网络情况**/
	private String netDesc;
	/**返现**/
	private String cashBack;
//	/**返现**/
//	private Double cashBack;
//	private String wifi;
//	private String broadnet;
//	private String park;
//	private String pickAircraft;
//	private String swimmingPool;
//	private String gym;
//	private String businessCenter;
//	private String meettingRoom;
//	private String dining;

	private List<MobileProductHotel> mobileProductHotels;

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public List<String> getSmallImages() {
		return smallImages;
	}

	public void setSmallImages(List<String> smallImages) {
		this.smallImages = smallImages;
	}

	public List<String> getMiddleImages() {
		return middleImages;
	}

	public void setMiddleImages(List<String> middleImages) {
		this.middleImages = middleImages;
	}

	public List<String> getLargeImages() {
		return largeImages;
	}

	public void setLargeImages(List<String> largeImages) {
		this.largeImages = largeImages;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public String getCommentGoodRate() {
		return commentGoodRate;
	}

	public void setCommentGoodRate(String commentGoodRate) {
		this.commentGoodRate = commentGoodRate;
	}

	public List<MobileProductHotel> getMobileProductHotels() {
		return mobileProductHotels;
	}

	public void setMobileProductHotels(
			List<MobileProductHotel> mobileProductHotels) {
		this.mobileProductHotels = mobileProductHotels;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getIntroEditor() {
		return introEditor;
	}

	public void setIntroEditor(String introEditor) {
		this.introEditor = introEditor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGeneralAmenities() {
		return generalAmenities;
	}

	public void setGeneralAmenities(String generalAmenities) {
		this.generalAmenities = generalAmenities;
	}

	public String getRoomAmenities() {
		return roomAmenities;
	}

	public void setRoomAmenities(String roomAmenities) {
		this.roomAmenities = roomAmenities;
	}

	public String getRecreationAmenities() {
		return recreationAmenities;
	}

	public void setRecreationAmenities(String recreationAmenities) {
		this.recreationAmenities = recreationAmenities;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getNetDesc() {
		return netDesc;
	}

	public void setNetDesc(String netDesc) {
		this.netDesc = netDesc;
	}
	public String getShareContent(){
		if(this.getSellPrice()==null){
			return null;
		}
		String content =String.format("我在@驴妈妈旅游网 上看到“%s”这家酒店不错，好评率%s，一晚%s起。 ", this.getName(),this.getCommentGoodRate(),this.getSellPrice()
				);
		return content;
	}
	public String getWapUrl(){
		return "";
	}
//	public Double getCashBack() {
//		return cashBack;
//	}
//
//	public void setCashBack(Double cashBack) {
//		this.cashBack = cashBack;
//	}

	public String getCashBack() {
		return cashBack;
	}

	public void setCashBack(String cashBack) {
		this.cashBack = cashBack;
	}

}
