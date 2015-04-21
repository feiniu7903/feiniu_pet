package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.search.annotation.FilterParam;

public class HotelSearchVO extends SearchVO implements Serializable{
	
	private static final long serialVersionUID = 259443556582442903L;
	
	public HotelSearchVO() {
		super();
	}

	public HotelSearchVO(String fromDest, String keyword) {
		super(fromDest, keyword);
	}
	public HotelSearchVO(String fromDest, String keyword,  Integer page, Integer pageSize) {
		super(fromDest, keyword, page, pageSize);
	}

	/** 起始价格 */
	@FilterParam(value="K",transcode=false)
	protected String startPrice;
	
	/** 结束价格 */
	@FilterParam(value="O",transcode=false)
	protected String endPrice;
	
	/**
	 * 酒店星级 定义,可以多选，以数字代表 ， 例如 345=[三星|四星|五星] 五星，四星，三星，二星 分别为： 5 ,4 ,3 ,2
	 * **/
	@FilterParam(value="E",transcode=false)
	private List<String> star;
	
	/**
	 * 产品主题定义 ,可以多选 ， 例如[亲子|古镇|温泉]
	 * */
	@FilterParam("J")
	private List<String> prodTopics;
	
	/**
	 * 酒店主题 定义，可以多选, 例如：  夜色  小资  海景
	 * */
	@FilterParam("F")
	private List<String> hotelTopics;
	
	/**筛选条件 - 包含地区*/
	@FilterParam("A")
	private String city;
	 
	/**促销活动*/
	@FilterParam(value="V",transcode=false)
	protected String promotion;
	
	/** 二次搜索关键字 **/
	@FilterParam("Q")
	private String keyword2 = "";
	/**
	 * 距离 单位:公里 默认200公里
	 */
	@FilterParam(value="Z",transcode=false)
	private Integer distance;
	
	/**
	 * 本地度假酒店搜索 0.周边搜索 1.本地搜索
	 */
	@FilterParam(value="N",transcode=false)
	private Integer local;
	
	/**
	 * 本地搜索的名称
	 */
	private String localName;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 搜索关键词类型 , 为空是说明是直接搜索;不为空的时候是点击补全词,类型为补全词的类型
	 * CITY 城市
	 * SCENIC 景区
	 * LANDMARK 地标
	 * HOTEL 酒店
	 */
	private String keywordType;
	
	public List<String> getStar() {
		return star;
	}

	public void setStar(List<String> star) {
		this.star = star;
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
 
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public List<String> getProdTopics() {
		return prodTopics;
	}

	public void setProdTopics(List<String> prodTopics) {
		this.prodTopics = prodTopics;
	}

	public List<String> getHotelTopics() {
		return hotelTopics;
	}

	public void setHotelTopics(List<String> hotelTopics) {
		this.hotelTopics = hotelTopics;
	}

	public String getKeywordType() {
		return keywordType;
	}

	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getLocal() {
		return local;
	}

	public void setLocal(Integer local) {
		this.local = local;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}
	
}
