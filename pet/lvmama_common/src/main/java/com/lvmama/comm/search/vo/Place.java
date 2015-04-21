package com.lvmama.comm.search.vo;

import java.io.Serializable;

import com.lvmama.comm.search.SearchConstants;

/**
 * 该类为自动生成
 * 
 * @author yuzhibing
 * @since 2009-12-18 14:48:39 Company lvmama Descripion 目的地/景区表
 */
public class Place implements Serializable {
	public final static String DEFAULT_PIC = "http://pic.lvmama.com/cmt/images/img_90_90.jpg";

	private Long id;
	private String name;
	private String shortName;
	private String city;
	private String provice;
	private String address;
	private String placeGrade;
	private String description;
	private String lvmamaCode;
	private String lvmamaId;
	private String largeImage;
	private String middleImage;
	private String smallImage;
	private String isValid;
	private Integer seq;
	private String keystonePlace;
	private String stage;
	private String origin;
	private String keyWord;
	private String destId;
	private String recommendReason;
	private String isHid;
	private String placeType;
	private String phone;           // 酒店电话
	private String hotelType;      // 酒店类型
	private Integer roomNum;      // 房间数量
	private String hotelFacility;
	private String startTime;   // 开业时间
	private String entertainForeigner;
	private String defaultUrl;
	private String hotelStar;
	private String pinYin;
	private String guideUrl;
	private String pinYinUrl;

	private Float avgScore;
	private Float viewScore;
	private Float hotelScore;
	private Float foodScore;
	private Float joyScore;
	private Float shopScore;
	private Float transportScore;
	private Integer visitorCount;
	private Integer unsatisfyNum;
	private Integer satisfyNum;
	private Integer exponent;
	private Float latitude;
	private Float longitude;
	private String desc;// 最近访问过的酒店所使用的简短描述

	public String getEntertainForeignerStr() {
		return "true".equals(this.entertainForeigner) ? "涉外" : "不涉外";
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param _id
	 */
	public void setId(Long _id) {
		this.id = _id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param _name
	 */
	public void setName(String _name) {
		this.name = _name;
	}

	/**
	 * @return
	 */
	public String getShortName() {
		return this.shortName;
	}

	/**
	 * @param _shortName
	 */
	public void setShortName(String _shortName) {
		this.shortName = _shortName;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * @param _city
	 */
	public void setCity(String _city) {
		if (_city != null)
			_city = _city.trim();
		this.city = _city;
	}

	/**
	 * @return
	 */
	public String getProvice() {
		return this.provice;
	}

	/**
	 * @param _provice
	 */
	public void setProvice(String _provice) {
		if (_provice != null)
			_provice = _provice.trim();
		this.provice = _provice;
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * @param _address
	 */
	public void setAddress(String _address) {
		this.address = _address;
	}

	/**
	 * @return
	 */
	public String getPlaceGrade() {
		return this.placeGrade;
	}

	/**
	 * @param _placeGrade
	 */
	public void setPlaceGrade(String _placeGrade) {
		this.placeGrade = _placeGrade;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param _description
	 */
	public void setDescription(String _description) {
		this.description = _description;
	}

	/**
	 * @return
	 */
	public String getLvmamaCode() {
		return this.lvmamaCode;
	}

	/**
	 * @param _lvmamaCode
	 */
	public void setLvmamaCode(String _lvmamaCode) {
		this.lvmamaCode = _lvmamaCode;
	}

	/**
	 * @return
	 */
	public String getLvmamaId() {
		return this.lvmamaId;
	}

	/**
	 * @param _lvmamaId
	 */
	public void setLvmamaId(String _lvmamaId) {
		this.lvmamaId = _lvmamaId;
	}

	/**
	 * @return
	 */
	public String getLargeImage() {
		return this.largeImage;
	}

	/**
	 * @param _largeImage
	 */
	public void setLargeImage(String _largeImage) {
		this.largeImage = _largeImage;
	}

	/**
	 * @return
	 */
	public String getMiddleImage() {
		return this.middleImage;
	}

	/**
	 * @param _middleImage
	 */
	public void setMiddleImage(String _middleImage) {
		this.middleImage = _middleImage;
	}

	/**
	 * @return
	 */
	public String getSmallImage() {
		return this.smallImage;
	}

	public String getSmallImageUrl() {
		if (this.smallImage == null) {
			return DEFAULT_PIC;
		}
		return SearchConstants.getInstance().getPrefixPic() + getSmallImage();
	}

	/**
	 * @param _smallImage
	 */
	public void setSmallImage(String _smallImage) {
		this.smallImage = _smallImage;
	}

	/**
	 * @return
	 */
	public Float getAvgScore() {
		return this.avgScore;
	}

	/**
	 * @param _avgScore
	 */
	public void setAvgScore(Float _avgScore) {
		this.avgScore = _avgScore;
	}

	/**
	 * @return
	 */
	public Float getViewScore() {
		return this.viewScore;
	}

	/**
	 * @param _viewScore
	 */
	public void setViewScore(Float _viewScore) {
		this.viewScore = _viewScore;
	}

	/**
	 * @return
	 */
	public Float getHotelScore() {
		return this.hotelScore;
	}

	/**
	 * @param _hotelScore
	 */
	public void setHotelScore(Float _hotelScore) {
		this.hotelScore = _hotelScore;
	}

	/**
	 * @return
	 */
	public Float getFoodScore() {
		return this.foodScore;
	}

	/**
	 * @param _foodScore
	 */
	public void setFoodScore(Float _foodScore) {
		this.foodScore = _foodScore;
	}

	/**
	 * @return
	 */
	public Float getJoyScore() {
		return this.joyScore;
	}

	/**
	 * @param _joyScore
	 */
	public void setJoyScore(Float _joyScore) {
		this.joyScore = _joyScore;
	}

	/**
	 * @return
	 */
	public Float getShopScore() {
		return this.shopScore;
	}

	/**
	 * @param _shopScore
	 */
	public void setShopScore(Float _shopScore) {
		this.shopScore = _shopScore;
	}

	/**
	 * @return
	 */
	public Float getTransportScore() {
		return this.transportScore;
	}

	/**
	 * @param _transportScore
	 */
	public void setTransportScore(Float _transportScore) {
		this.transportScore = _transportScore;
	}

	/**
	 * @return
	 */
	public int getVisitorCount() {
		return this.visitorCount;
	}

	/**
	 * @param _visitorCount
	 */
	public void setVisitorCount(int _visitorCount) {
		this.visitorCount = _visitorCount;
	}

	/**
	 * @return
	 */
	public int getUnsatisfyNum() {
		return this.unsatisfyNum;
	}

	/**
	 * @param _unsatisfyNum
	 */
	public void setUnsatisfyNum(int _unsatisfyNum) {
		this.unsatisfyNum = _unsatisfyNum;
	}

	/**
	 * @return
	 */
	public int getSatisfyNum() {
		return this.satisfyNum;
	}

	/**
	 * @param _satisfyNum
	 */
	public void setSatisfyNum(int _satisfyNum) {
		this.satisfyNum = _satisfyNum;
	}

	/**
	 * @return
	 */
	public int getExponent() {
		return this.exponent;
	}

	/**
	 * @param _exponent
	 */
	public void setExponent(int _exponent) {
		this.exponent = _exponent;
	}

	/**
	 * @return
	 */
	public String getIsValid() {
		return this.isValid;
	}

	/**
	 * @param _isValid
	 */
	public void setIsValid(String _isValid) {
		this.isValid = _isValid;
	}

	/**
	 * @return
	 */
	public int getSeq() {
		return this.seq;
	}

	/**
	 * @param _seq
	 */
	public void setSeq(int _seq) {
		this.seq = _seq;
	}

	/**
	 * @return
	 */
	public String getKeystonePlace() {
		return this.keystonePlace;
	}

	/**
	 * @param _keystonePlace
	 */
	public void setKeystonePlace(String _keystonePlace) {
		this.keystonePlace = _keystonePlace;
	}

	/**
	 * @return
	 */
	public String getStage() {
		return this.stage;
	}

	/**
	 * @param _stage
	 */
	public void setStage(String _stage) {
		this.stage = _stage;
	}

	/**
	 * @return
	 */
	public Float getLatitude() {
		return this.latitude;
	}

	/**
	 * @param _latitude
	 */
	public void setLatitude(Float _latitude) {
		this.latitude = _latitude;
	}

	/**
	 * @return
	 */
	public Float getLongitude() {
		return this.longitude;
	}

	/**
	 * @param _longitude
	 */
	public void setLongitude(Float _longitude) {
		this.longitude = _longitude;
	}

	/**
	 * @return
	 */
	public String getOrigin() {
		return this.origin;
	}

	/**
	 * @param _origin
	 */
	public void setOrigin(String _origin) {
		this.origin = _origin;
	}

	public String getRecommendReason() {
		return recommendReason;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getEntertainForeigner() {
		return entertainForeigner;
	}

	public void setEntertainForeigner(String entertainForeigner) {
		this.entertainForeigner = entertainForeigner;
	}

	public String getHotelStar() {
		return hotelStar;
	}

	public void setHotelStar(String hotelStar) {
		this.hotelStar = hotelStar;
	}

	public String getDefaultUrl() {
		return defaultUrl;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public String getGuideUrl() {
		return guideUrl;
	}

	public void setGuideUrl(String guideUrl) {
		this.guideUrl = guideUrl;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}

	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getIsHid() {
		return isHid;
	}

	public void setIsHid(String isHid) {
		this.isHid = isHid;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getHotelFacility() {
		return hotelFacility;
	}

	public void setHotelFacility(String hotelFacility) {
		this.hotelFacility = hotelFacility;
	}
}