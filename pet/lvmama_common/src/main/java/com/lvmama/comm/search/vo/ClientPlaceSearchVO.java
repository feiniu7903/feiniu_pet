package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.List;

public class ClientPlaceSearchVO extends SearchVO  implements Serializable{
	private static final long serialVersionUID = 5763009330523013785L;

	/** 周边景区 对酒店搜索有用 */
	private String spot ;
	/** 目的地城市ID*/
	private String cityId ;
	/** 目的地城市 */
	private String cityName ;
	/** 主题 */
	private String subject ;
	/** 目的地类型 */
	private List<String> stage ;
	/** 排序：距离||seq||伪字典顺序||价格升序||点评数量||点评综合分降序 */
	private String sort;
	/** 酒店价格范围 */
	private String priceRange ;
	/** 酒店星级 */
	private String star ;
	/**
	 * 搜索省市树类型参数 ,
	 * 景点|酒店|自由行线路|所有三种|
	 * hasTicket|hasHotel|hasFreeness|ALL
	 **/
	private List<String> productType ;
	/**渠道**/
	private String channel ;
	/** 排序经纬度 */
	private String x ;
	/** 排序经纬度 */
	private String y;
	/**景点或者酒店ID,转换为x,y 用*/
	private String spotId;
	/** 邻近搜索中心点经纬度 */
	private String longitude  ;
	/** 邻近搜索中心点经纬度 */
	private String latitude;	
	/** 邻近范围半径单位米(m) 默认3公里 **/
	private String windage ;
	/** 酒店类型 */
	private List<String> hotelType;
	/** 包含自由行 */
	private boolean hasFreenes;
	
	private List<Long> placeIds;
	
	public String getSpot() {
		return spot;
	}
	public void setSpot(String spot) {
		this.spot = spot;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<String> getStage() {
		return stage;
	}
	public void setStage(List<String> stage) {
		this.stage = stage;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public List<String> getProductType() {
		return productType;
	}
	public void setProductType(List<String> productType) {
		this.productType = productType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getSpotId() {
		return spotId;
	}
	public void setSpotId(String spotId) {
		this.spotId = spotId;
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
	public String getWindage() {
		return windage;
	}
	public void setWindage(String windage) {
		this.windage = windage;
	}

	public List<String> getHotelType() {
		return hotelType;
	}
	public void setHotelType(List<String> hotelType) {
		this.hotelType = hotelType;
	}
	public boolean isHasFreenes() {
		return hasFreenes;
	}
	public void setHasFreenes(boolean hasFreenes) {
		this.hasFreenes = hasFreenes;
	}
	public List<Long> getPlaceIds() {
		return placeIds;
	}
	public void setPlaceIds(List<Long> placeIds) {
		this.placeIds = placeIds;
	}
	
		
}
