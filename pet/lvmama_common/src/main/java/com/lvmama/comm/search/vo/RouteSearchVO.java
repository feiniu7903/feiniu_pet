package com.lvmama.comm.search.vo;

import java.io.Serializable;

import com.lvmama.comm.search.SearchConstants.SEARCH_TYPE;
import com.lvmama.comm.search.annotation.FilterParam;

public class RouteSearchVO extends SearchVO implements Serializable {
	public RouteSearchVO() {
		super();
	}
	public RouteSearchVO(String fromDest,  String keyword, Integer page, Integer pageSize) {
		super(fromDest, keyword, page, pageSize);
	}
	public RouteSearchVO(String fromDest, String keyword) {
		super(fromDest, keyword);
	}

	private static final long serialVersionUID = 3522672712388312309L;
	/**
	 * 查询类型
	 */
	private SEARCH_TYPE type;
	/** 二次搜索关键字 **/
	@FilterParam("Q")
	private String keyword2 = "";
	/** 起始价格 */
	@FilterParam(value="K",transcode=false)
	protected String startPrice;
	/** 结束价格 */
	@FilterParam(value="O",transcode=false)
	protected String endPrice;
	/** 景点 **/
	@FilterParam("D")
	protected String scenicPlace;
	/** 游玩人数 **/
	@FilterParam("M")
	protected String playNum;
	/** 包含地区 **/
	/** 筛选条件 - 包含地区 */
	@FilterParam("A")
	protected String city;
	/** 筛选条件 - 主题 */
	@FilterParam("C")
	protected String subject;
	/** 标签 **/
	@FilterParam("T")
	protected String tag;
	/** 筛选条件 - 游玩天数 */
	@FilterParam(value="I",transcode=false)
	private String visitDay;
	/** 酒店类型 **/
	@FilterParam("J")
	protected String hotelType;
	/** 特色品牌 **/
	@FilterParam("B")
	protected String playBrand;
	/** 游玩线路 */
	@FilterParam("L")
	protected String playLine;
	/** 游玩特色 */
	@FilterParam("G")
	protected String playFeature;
	/** 往返交通 */
	@FilterParam("H")
	protected String traffic;
	/** 酒店位置 */
	@FilterParam("X")
	protected String hotelLocation;
	/** 上岛交通 **/
	@FilterParam("Z")
	protected String landTraffic;
	/** 岛屿特色 **/
	@FilterParam("Y")
	protected String landFeature;
	/**促销活动*/
	@FilterParam(value="V",transcode=false)
	protected String promotion;
	/** 产品名称*/
	protected String productName;
	
	/** 游玩区域 */
	protected String playArea;
	/** 线路主题 */
	protected String routeTopic;
	/** 出发班期 */
	protected String travelTime;
	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}

	public String getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
	}

	public String getScenicPlace() {
		return scenicPlace;
	}

	public void setScenicPlace(String scenicPlace) {
		this.scenicPlace = scenicPlace;
	}

	public String getPlayNum() {
		return playNum;
	}

	public void setPlayNum(String playNum) {
		this.playNum = playNum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getVisitDay() {
		return visitDay;
	}

	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getPlayBrand() {
		return playBrand;
	}

	public void setPlayBrand(String playBrand) {
		this.playBrand = playBrand;
	}

	public String getPlayArea() {
		return playArea;
	}

	public void setPlayArea(String playArea) {
		this.playArea = playArea;
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

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getHotelLocation() {
		return hotelLocation;
	}

	public void setHotelLocation(String hotelLocation) {
		this.hotelLocation = hotelLocation;
	}

	public String getRouteTopic() {
		return routeTopic;
	}

	public void setRouteTopic(String routeTopic) {
		this.routeTopic = routeTopic;
	}

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	public SEARCH_TYPE getType() {
		return type;
	}

	public void setType(SEARCH_TYPE type) {
		this.type = type;
	}

	public String getProductName() {
		return productName;
	}

	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
