package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.search.annotation.FilterParam;

public class VerHotelSearchVO extends SearchVO implements Serializable{
	
	private static final long serialVersionUID = 259443556582442903L;
	
	public VerHotelSearchVO() {
		super();
	}

	public VerHotelSearchVO(String fromDest, String keyword) {
		super(fromDest, keyword);
	}
	public VerHotelSearchVO(String fromDest, String keyword,  Integer page, Integer pageSize) {
		super(fromDest, keyword, page, pageSize);
	}


	
	/**
	 * 自动补全的id
	 */
	private String searchId;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	
	/**
	 * 半径
	 */
	private String distance;
	/**
	 * 自动补全的type
	 *
	 */
	private String searchType;
	//自动补全上一级的id
	private String parentId;
	
	private String hotelStar;
	
	private String hotelStarName;
	
	//1 是600元以上 2是300-600  3是150-300 4是150 以下
	private String hotelprice;
	
	private String beginBookTime;
	
	private String endBookTime;
	
	private String beginBookTimeDate;
	
	private String endBookTimeDate;
	//排序规则  1是按照驴妈妈推荐 2是按照距离,3价格从高到底，4是价格从低到高
	private String ranktype;
	
	private String maxproductsprice;
	
	private String minproductsprice;
	//设施
	private String facilities;
	//房型 1大床房，2双床房，3三人间 4家庭房 5套房
	private String room_type;
	//品牌
	private String hotel_brand;
	
	private String hotel_brand_name;
	//是否抢购
	private String issale;
	//是否返现
	private String returnmoney;
	//查询时间段
	private String queryTimes;
	//酒店设施及房型是否收缩
	private String activeFlag;
	
	private String districtName;
	
	private String districtPinYin;
	
	private String landMarkName;
	
	public String getLandMarkName() {
		return landMarkName;
	}

	public void setLandMarkName(String landMarkName) {
		this.landMarkName = landMarkName;
	}

	public String getHotelStarName() {
		return hotelStarName;
	}

	public void setHotelStarName(String hotelStarName) {
		this.hotelStarName = hotelStarName;
	}

	public String getHotel_brand_name() {
		return hotel_brand_name;
	}

	public void setHotel_brand_name(String hotel_brand_name) {
		this.hotel_brand_name = hotel_brand_name;
	}

	// actionUrl
	private String actionUrl;
	
	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
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

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getHotelStar() {
		return hotelStar;
	}

	public void setHotelStar(String hotelStar) {
		this.hotelStar = hotelStar;
	}

	public String getHotelprice() {
		return hotelprice;
	}

	public void setHotelprice(String hotelprice) {
		this.hotelprice = hotelprice;
	}

	public String getBeginBookTime() {
		return beginBookTime;
	}

	public void setBeginBookTime(String beginBookTime) {
		this.beginBookTime = beginBookTime;
	}

	public String getEndBookTime() {
		return endBookTime;
	}

	public void setEndBookTime(String endBookTime) {
		this.endBookTime = endBookTime;
	}

	public String getRanktype() {
		return ranktype;
	}

	public void setRanktype(String ranktype) {
		this.ranktype = ranktype;
	}

	public String getMaxproductsprice() {
		return maxproductsprice;
	}

	public void setMaxproductsprice(String maxproductsprice) {
		this.maxproductsprice = maxproductsprice;
	}

	public String getMinproductsprice() {
		return minproductsprice;
	}

	public void setMinproductsprice(String minproductsprice) {
		this.minproductsprice = minproductsprice;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public String getHotel_brand() {
		return hotel_brand;
	}

	public void setHotel_brand(String hotel_brand) {
		this.hotel_brand = hotel_brand;
	}

	public String getIssale() {
		return issale;
	}

	public void setIssale(String issale) {
		this.issale = issale;
	}

	public String getReturnmoney() {
		return returnmoney;
	}

	public void setReturnmoney(String returnmoney) {
		this.returnmoney = returnmoney;
	}

	public String getBeginBookTimeDate() {
		return beginBookTimeDate;
	}

	public void setBeginBookTimeDate(String beginBookTimeDate) {
		this.beginBookTimeDate = beginBookTimeDate;
	}

	public String getEndBookTimeDate() {
		return endBookTimeDate;
	}

	public void setEndBookTimeDate(String endBookTimeDate) {
		this.endBookTimeDate = endBookTimeDate;
	}

	public String getQueryTimes() {
		return queryTimes;
	}

	public void setQueryTimes(String queryTimes) {
		this.queryTimes = queryTimes;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictPinYin() {
		return districtPinYin;
	}

	public void setDistrictPinYin(String districtPinYin) {
		this.districtPinYin = districtPinYin;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	
}
