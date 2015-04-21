package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.List;

public class ClientRouteSearchVO extends SearchVO implements Serializable {
	private static final long serialVersionUID = -7623400665800730536L;
	public ClientRouteSearchVO() {
		super();
	}
	public ClientRouteSearchVO(String fromDest,  String keyword, Integer page, Integer pageSize) {
		super(fromDest, keyword, page, pageSize);
	}
	public ClientRouteSearchVO(String fromDest, String keyword) {
		super(fromDest, keyword);
	}
	/** 游玩天数**/
	private String visitDay ;
	/**按产品价格排序**/
	private String sort;
	/**按产品经济性筛选**/
	private String priceType;
	private String toDest ;
	/**
	 * 搜索省市树类型参数 ,
	 * 景点|酒店|自由行线路|所有三种|
	 * hasTicket|hasHotel|hasFreeness|ALL
	 **/
	private List<String> productType ;
	private List<String> subProductType ;
	/**渠道**/
	private String channel ;
	private String tag ;
	/**二次搜索关键字**/
	private String keyword2;
	/**对线路产品的名称进行搜索,支持多个以空格分词的关键字复合搜索,最大数有限制**/
	private String poductNameSearchKeywords ;
	private String cityId;
	private String city;
	
	private String subject;
	private String placeId;
	/**
	 * v4.0.1 景点
	 */
	protected String scenicPlace;
	protected String traffic;
	protected String playLine;
	protected String playFeature;
	protected String hotelType;
	protected String hotelLocation;
	protected String playBrand;
	protected String playNum;
	protected String landTraffic;
	protected String landFeature;
	protected String promotion;
	
	private List<Long> productIds;
	
	public String getVisitDay() {
		if(visitDay!=null&&visitDay.contains("天")){
			return visitDay.replace("天", "");
		}
		return visitDay;
	}
	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public String getToDest() {
		return toDest;
	}
	public void setToDest(String toDest) {
		this.toDest = toDest;
	}
	public List<String> getProductType() {
		return productType;
	}
	public void setProductType(List<String> productType) {
		this.productType = productType;
	}
	public List<String> getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(List<String> subProductType) {
		this.subProductType = subProductType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getPoductNameSearchKeywords() {
		return poductNameSearchKeywords;
	}
	public void setPoductNameSearchKeywords(String poductNameSearchKeywords) {
		this.poductNameSearchKeywords = poductNameSearchKeywords;
	}
	public String getCityId() {
		return cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String toString(){
		return this.getProductType()+"|" +this.getSubProductType()+" |" +this.getToDest()+"|"+this.getFromDest();
	}
	public String getScenicPlace() {
		return scenicPlace;
	}
	public void setScenicPlace(String scenicPlace) {
		this.scenicPlace = scenicPlace;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	public String getPlayLine() {
		return playLine;
	}
	public void setPlayLine(String playLine) {
		this.playLine = playLine;
	}
	public String getPlayFeature() {
		return playFeature;
	}
	public void setPlayFeature(String playFeature) {
		this.playFeature = playFeature;
	}
	public String getHotelType() {
		return hotelType;
	}
	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}
	public String getHotelLocation() {
		return hotelLocation;
	}
	public void setHotelLocation(String hotelLocation) {
		this.hotelLocation = hotelLocation;
	}
	public String getPlayNum() {
		return playNum;
	}
	public void setPlayNum(String playNum) {
		this.playNum = playNum;
	}
	public String getLandTraffic() {
		return landTraffic;
	}
	public void setLandTraffic(String landTraffic) {
		this.landTraffic = landTraffic;
	}
	public String getLandFeature() {
		return landFeature;
	}
	public void setLandFeature(String landFeature) {
		this.landFeature = landFeature;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public String getPlayBrand() {
		return playBrand;
	}
	public void setPlayBrand(String playBrand) {
		this.playBrand = playBrand;
	}
	public List<Long> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}
}
