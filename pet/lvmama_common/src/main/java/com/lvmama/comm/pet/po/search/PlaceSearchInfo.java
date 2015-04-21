package com.lvmama.comm.pet.po.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdTag;

/**
 * 标的 ,景点,酒店相关搜索表
 * 
 * @author duanshuailiang
 * 
 */
public class PlaceSearchInfo implements Serializable {

	private Long placeId;

	private String name;

	private String summary;

	private String productsPrice;
	    
	private float distance;
	    
	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
			this.distance = distance;
	}

	private Float cmtAvgScore;

	private String cmtAvgScoreStr;

	public String getCmtAvgScoreStr() {
		return cmtAvgScoreStr;
	}

	public void setCmtAvgScoreStr(String cmtAvgScoreStr) {
		this.cmtAvgScoreStr = cmtAvgScoreStr;
	}

	private Long cmtNum;

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	private Long commentCount;
	private Float avgScore;

	public Float getAvgScore() {
		return avgScore;
	}

	/**
	 * 获取点评维度平均分的区间值
	 * 
	 * @return float
	 */
	public String getRoundHalfUpOfAvgScore() {
		float avg = getAvgScore();
		String value = "0";
		if (avg == 0) {
			value = "0";
		} else if (avg > 0 && avg <= 0.5) {
			value = "05";
		} else if (avg > 0.5 && avg <= 1) {
			value = "1";
		} else if (avg > 1 && avg <= 1.5) {
			value = "15";
		} else if (avg > 1.5 && avg <= 2) {
			value = "2";
		} else if (avg > 2 && avg <= 2.5) {
			value = "25";
		} else if (avg > 2.5 && avg <= 3) {
			value = "3";
		} else if (avg > 3 && avg <= 3.5) {
			value = "35";
		} else if (avg > 3.5 && avg <= 4) {
			value = "4";
		} else if (avg > 4 && avg <= 4.5) {
			value = "45";
		} else if (avg > 4.5 && avg <= 5) {
			value = "5";
		}
		return value;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public Float getCmtAvgScore() {
		return cmtAvgScore;
	}

	public void setCmtAvgScore(Float cmtAvgScore) {
		this.cmtAvgScore = cmtAvgScore;
	}

	public Float getCmtNiceRate() {
		return cmtNiceRate;
	}

	public void setCmtNiceRate(Float cmtNiceRate) {
		this.cmtNiceRate = cmtNiceRate;
	}

	private String province;

	private String city;

	private Date createdDate;

	private Date updatedDate;

	private Long seq;

	private Long cityId;

	private Long provinceId;

	private String smallImage;

	private Long productNum;

	private Long ticketNum;

	private Long hotleNum;

	private Long freenessNum;

	private Long routeNum;

	private Long stage;

//	private String hotelFacility;

	private String isValid;

	private Long maxProductsPrice;

	private String remarkes;

	private String weekSales;	
	public String getWeekSales() {
		return weekSales;
	}

	public void setWeekSales(String weekSales) {
		this.weekSales = weekSales;
	}

	public String getRemarkes() {
		return summary;
	}

	public void setRemarkes(String remarkes) {
		this.remarkes = remarkes;
	}

//	private String hotelStar;

	private String pinYinUrl;

	private String hfkw;

	private String pinYin;

	private String address;

	private String hasHotelAround;

	private String largeImage;

	private String roundPlaceName;

	private String destSubjects;

	private String destTagsName;

	private String destTagsDescript;

	private String destTagsGroup;

	private String destTagsCss;

	private List<ProdTag> tagList = new ArrayList<ProdTag>();

	private Map<String, List<ProdTag>> tagGroupMap = new HashMap<String, List<ProdTag>>();

	private String destPersentStr;

	private Float cmtNiceRate;

	private Double longitude;

	private Double latitude;

	private Long parentPlaceId;

	private String placeType;

	private Long marketPrice;

	private String placeSecondSubject;

	private String placeFirstSubject;

	private String middleImage;

	private String parentPlaceNameIds;

	private String scenicGrade;

//	private String hotelType;

//	private String hotelLevel;

	private String imgUrl;
	private String enName;
	
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	/**
	 * 奖金返现(单位:元)
	 * */
    private String cashRefund;
    /**
     * 是否今日可定
     */
    private Date todayOrderAbleTime;
    
    /**
     * 最晚预定时间
     */
    private Date todayOrderLastTime;
    
	public String getImgUrl() {
		return smallImage;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getProductsPrice() {
		return productsPrice;
	}

	public void setProductsPrice(String productsPrice) {
		this.productsPrice = productsPrice;
	}

	public Long getCmtNum() {
		return cmtNum;
	}

	public void setCmtNum(Long cmtNum) {
		this.cmtNum = cmtNum;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public Long getProductNum() {
		return productNum;
	}

	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}

	public Long getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Long getHotleNum() {
		return hotleNum;
	}

	public void setHotleNum(Long hotleNum) {
		this.hotleNum = hotleNum;
	}

	public Long getFreenessNum() {
		return freenessNum;
	}

	public void setFreenessNum(Long freenessNum) {
		this.freenessNum = freenessNum;
	}

	public Long getRouteNum() {
		return routeNum;
	}

	public void setRouteNum(Long routeNum) {
		this.routeNum = routeNum;
	}

	public Long getStage() {
		return stage;
	}

	public void setStage(Long stage) {
		this.stage = stage;
	}

//	public String getHotelFacility() {
//		return hotelFacility;
//	}
//
//	public void setHotelFacility(String hotelFacility) {
//		this.hotelFacility = hotelFacility;
//	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Long getMaxProductsPrice() {
		return maxProductsPrice;
	}

	public Integer getProductsPriceInteger() {
		Integer price = 0;
		if (this.productsPrice != null) {
			price = Integer.valueOf(productsPrice.toString());
		}
		return price / 100;
	}

	public void setMaxProductsPrice(Long maxProductsPrice) {
		this.maxProductsPrice = maxProductsPrice;
	}

//	public String getHotelStar() {
//		return hotelStar;
//	}
//
//	public void setHotelStar(String hotelStar) {
//		this.hotelStar = hotelStar;
//	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}

	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}

	public String getHfkw() {
		return hfkw;
	}

	public void setHfkw(String hfkw) {
		this.hfkw = hfkw;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHasHotelAround() {
		return hasHotelAround;
	}

	public void setHasHotelAround(String hasHotelAround) {
		this.hasHotelAround = hasHotelAround;
	}

	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public String getRoundPlaceName() {
		return roundPlaceName;
	}

	public void setRoundPlaceName(String roundPlaceName) {
		this.roundPlaceName = roundPlaceName;
	}

	public String getDestSubjects() {
		return destSubjects;
	}

	public void setDestSubjects(String destSubjects) {
		this.destSubjects = destSubjects;
	}

	public String getDestTagsName() {
		return destTagsName;
	}

	public void setDestTagsName(String destTagsName) {
		this.destTagsName = destTagsName;
	}

	public String getDestPersentStr() {
		return destPersentStr;
	}

	public void setDestPersentStr(String destPersentStr) {
		this.destPersentStr = destPersentStr;
	}

	public String getDestTagsDescript() {
		return destTagsDescript;
	}

	public void setDestTagsDescript(String destTagsDescript) {
		this.destTagsDescript = destTagsDescript;
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

	public Long getParentPlaceId() {
		return parentPlaceId;
	}

	public void setParentPlaceId(Long parentPlaceId) {
		this.parentPlaceId = parentPlaceId;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getPlaceSecondSubject() {
		return placeSecondSubject;
	}

	public void setPlaceSecondSubject(String placeSecondSubject) {
		this.placeSecondSubject = placeSecondSubject;
	}

	public String getPlaceFirstSubject() {
		return placeFirstSubject;
	}

	public void setPlaceFirstSubject(String placeFirstSubject) {
		this.placeFirstSubject = placeFirstSubject;
	}

	public String getMiddleImage() {
		return middleImage;
	}

	public void setMiddleImage(String middleImage) {
		this.middleImage = middleImage;
	}

	public String getParentPlaceNameIds() {
		return parentPlaceNameIds;
	}

	public void setParentPlaceNameIds(String parentPlaceNameIds) {
		this.parentPlaceNameIds = parentPlaceNameIds;
	}

	public String getScenicGrade() {
		return scenicGrade;
	}

	public void setScenicGrade(String scenicGrade) {
		this.scenicGrade = scenicGrade;
	}

//	public String getHotelType() {
//		return hotelType;
//	}
//
//	public void setHotelType(String hotelType) {
//		this.hotelType = hotelType;
//	}
//
//	public String getHotelLevel() {
//		return hotelLevel;
//	}
//
//	public void setHotelLevel(String hotelLevel) {
//		this.hotelLevel = hotelLevel;
//	}

	public String getDestTagsGroup() {
		return destTagsGroup;
	}

	public void setDestTagsGroup(String destTagsGroup) {
		this.destTagsGroup = destTagsGroup;
	}

	public String getDestTagsCss() {
		return destTagsCss;
	}

	public void setDestTagsCss(String destTagsCss) {
		this.destTagsCss = destTagsCss;
	}

	public List<ProdTag> getTagList() {
		if (tagList == null) {
			return new ArrayList<ProdTag>();
		}
		return tagList;
	}

	public void setTagList(List<ProdTag> tagList) {
		this.tagList = tagList;
	}

	public Map<String, List<ProdTag>> getTagGroupMap() {
		if (tagGroupMap == null) {
			return new HashMap<String, List<ProdTag>>();
		}
		return tagGroupMap;
	}

	public void setTagGroupMap(Map<String, List<ProdTag>> tagGroupMap) {
		this.tagGroupMap = tagGroupMap;
	}

	public String getCashRefund() {
		return cashRefund;
	}

	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}

	public Date getTodayOrderAbleTime() {
		return todayOrderAbleTime;
	}

	public void setTodayOrderAbleTime(Date todayOrderAbleTime) {
		this.todayOrderAbleTime = todayOrderAbleTime;
	}
	public boolean canOrderTodayCurrentTimeForPlace(){
		if(this.getTodayOrderAbleTime()==null){
			return false;
		}
		return new Date().before(this.todayOrderAbleTime);
	}

	public Date getTodayOrderLastTime() {
		return todayOrderLastTime;
	}

	public void setTodayOrderLastTime(Date todayOrderLastTime) {
		this.todayOrderLastTime = todayOrderLastTime;
	}
	
}
