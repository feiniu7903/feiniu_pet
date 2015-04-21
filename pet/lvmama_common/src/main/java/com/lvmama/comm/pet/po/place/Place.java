package com.lvmama.comm.pet.po.place;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

public class Place  implements Serializable {
	private static final long serialVersionUID = -5341886404300639437L;
	
	public static enum PLACE_TEMPLATE {
		/* 国内模板 */
		TEMPLATE_ZHONGGUO, 
		/* 境外模板 */
		TEMPLATE_ABROAD 
	};

	public final static String DEFAULT_PIC = "http://pic.lvmama.com/img/cmt/img_120_60.jpg";
	
	private Long placeId;

    private String name;

    private String pinYin;

    private String pinYinUrl;

    private String seoName;

    private String seoTitle;

    private String seoContent;

    private String seoKeyword;

    private String seoDescription;

    private String template;
    
    private String isExit;

    private String hfkw;

    private String placeType;

    private String remarkes;

    private Long parentPlaceId;
    
    private String parentPlaceName;

    private Long seq;

    private Long seqActivity;

    private String isBottom;

    private String prodEnable;

    private String stage;
    
    private String smallImage;
    
	private String middleImage;
	
	private String largeImage;
	
	private String guideUrl;
	
	private Long marketPrice;
	
	private Long sellPrice;
	
	private String isClient;
	
	private String mainTitle;
	
	private String cmtTitle;
	
    private String isValid;

    private String address;

    private String scenicOpenTime;

    private String scenicRecommendTime;

    private String scenicGrade;

    private String scenicSecondTopic;

    private String firstTopic;
    
    private String scenicSecondTopicOld;

    private String firstTopicOld;

    private String feature;

    private String orderNotice;

    private String importantTips;

    private String description;

    private Date createTime;

    private Date updateTime;

    private String defaultUrl;
    
    private String isHasActivity;
    
    private List<PlacePhoto> placePhoto;
    private List<PlaceActivity> placeActivity; 
    
	private String cashRefund;
	private String productsPrice;
	private Integer ticketNum;
	private Integer hotelNum;
	private Long commentCount;
	private String trafficInfo;
	private String from;
	private String to;
	private String parentPinYin;
	private String  destinationExplore; //新增目的地探索
	private String  kuaiSuRuYuan; //新增快速入园
	private String  guiJiuPei; //新增贵就赔
	private String  suiShiTui; //新增随时退
	private String  ruYuanBaoZhang;//新增入园保障 	
	private String hasSensitiveWord;
	/**
	 * 搜索关键字转码id
	 */
	private Long codeId;
	
	/**
	 * 酒店信息
	 */
	private PlaceHotel placeHotel;
	
	
	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	private Float avgScore;
    
	private String imgUrl;
	/**分页开始**/
	private Long startRows;
	
	/**分页结束**/
	private Long endRows;
	private String phone;
	private String province;
	private String city;
	private String placeProvince;
	private String placeCity;
	//英文名
	private  String enName;
	//机场编码
	private String airportCode;
	
	/**
	 * 获取点评维度平均分的区间值
	 * @return float
	 */
	public String getRoundHalfUpOfAvgScore() {
		Float avgF = getAvgScore();
		float avg = 0;
		
		if(avgF!=null)
			avg = getAvgScore();
		
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
	
    public Long getPlaceId() {
        return placeId;
    }

    public String getPlaceProvince() {
		return placeProvince;
	}

	public void setPlaceProvince(String placeProvince) {
		this.placeProvince = placeProvince;
	}

	public String getPlaceCity() {
		return placeCity;
	}

	public void setPlaceCity(String placeCity) {
		this.placeCity = placeCity;
	}

	public String getScenicSecondTopicOld() {
		return scenicSecondTopicOld;
	}

	public void setScenicSecondTopicOld(String scenicSecondTopicOld) {
		this.scenicSecondTopicOld = scenicSecondTopicOld;
	}

	public String getFirstTopicOld() {
		return firstTopicOld;
	}

	public void setFirstTopicOld(String firstTopicOld) {
		this.firstTopicOld = firstTopicOld;
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

    public String getSeoName() {
        return seoName;
    }

    public void setSeoName(String seoName) {
        this.seoName = seoName;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoContent() {
        return seoContent;
    }

    public void setSeoContent(String seoContent) {
        this.seoContent = seoContent;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getIsExit() {
		return isExit;
	}

	public void setIsExit(String isExit) {
		this.isExit = isExit;
	}

	public String getHfkw() {
        return hfkw;
    }

    public void setHfkw(String hfkw) {
        this.hfkw = hfkw;
    }

    public String getPlaceType() {
        return placeType;
    }
    public String getPlaceTypeCn() {
        return Constant.PLACE_TYPE.getCnName(this.placeType);
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getRemarkes() {
        return remarkes;
    }

    public List<PlaceActivity> getPlaceActivity() {
		return placeActivity;
	}

	public void setPlaceActivity(List<PlaceActivity> placeActivity) {
		this.placeActivity = placeActivity;
	}

	public void setRemarkes(String remarkes) {
        this.remarkes = remarkes;
    }

    public Long getParentPlaceId() {
        return parentPlaceId;
    }

    public void setParentPlaceId(Long parentPlaceId) {
        this.parentPlaceId = parentPlaceId;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getSeqActivity() {
        return seqActivity;
    }

    public void setSeqActivity(Long seqActivity) {
        this.seqActivity = seqActivity;
    }

    public String getIsBottom() {
        return isBottom;
    }

    public void setIsBottom(String isBottom) {
        this.isBottom = isBottom;
    }

    public String getProdEnable() {
        return prodEnable;
    }

    public void setProdEnable(String prodEnable) {
        this.prodEnable = prodEnable;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScenicOpenTime() {
        return scenicOpenTime;
    }

    public void setScenicOpenTime(String scenicOpenTime) {
        this.scenicOpenTime = scenicOpenTime;
    }

    public String getScenicRecommendTime() {
        return scenicRecommendTime;
    }

    public void setScenicRecommendTime(String scenicRecommendTime) {
        this.scenicRecommendTime = scenicRecommendTime;
    }

    public String getScenicGrade() {
        return scenicGrade;
    }

    public void setScenicGrade(String scenicGrade) {
        this.scenicGrade = scenicGrade;
    }

    public String getScenicSecondTopic() {
        return scenicSecondTopic;
    }

    public void setScenicSecondTopic(String scenicSecondTopic) {
        this.scenicSecondTopic = scenicSecondTopic;
    }

    public String getFirstTopic() {
        return firstTopic;
    }

    public void setFirstTopic(String firstTopic) {
        this.firstTopic = firstTopic;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getOrderNotice() {
        return orderNotice;
    }

    public void setOrderNotice(String orderNotice) {
        this.orderNotice = orderNotice;
    }

    public String getImportantTips() {
        return importantTips;
    }

    public void setImportantTips(String importantTips) {
        this.importantTips = importantTips;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHotelOpenTime() {
    	if(this.placeHotel!=null){
    		return this.placeHotel.getHotelOpenTimeStr();
    	}
        return null;
    }

    public String getHotelPhone() {
    	if(this.placeHotel!=null){
    		return this.placeHotel.getHotelPhone();
    	}
        return null;
    }

    public Long getHotelRoomNum() {
    	if(this.placeHotel!=null){
    		return this.placeHotel.getHotelRoomNum();
    	}
        return null;
    }


    public String getHotelLevel() {
    	if(this.placeHotel!=null){
    		return this.placeHotel.getHotelLevel();
    	}
        return null;
    }

    public String getHotelType() {
    	if(this.placeHotel!=null){
    		return this.placeHotel.getHotelType();
    	}
        return null;
    }

    public String getHotelStar() {
    	if(this.placeHotel!=null){
    		return this.placeHotel.getHotelStar();
    	}
        return null;
    }

    public String getHotelForeigner() {
    	if(this.placeHotel!=null){
    		return this.placeHotel.getHotelForeigner();
    	}
        return null;
    }
    
	public String getHotelForeignerStr() {
		return "true".equals(this.getHotelForeigner()) ? "涉外" : "不涉外";
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

	public String getIsHasActivity() {
		return isHasActivity;
	}

	public void setIsHasActivity(String isHasActivity) {
		this.isHasActivity = isHasActivity;
	}

	public String getCashRefund() {
		return cashRefund;
	}

	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}
	

	public Float getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public Long getStartRows() {
		return startRows;
	}

	public void setStartRows(Long startRows) {
		this.startRows = startRows;
	}

	public Long getEndRows() {
		return endRows;
	}

	public void setEndRows(Long endRows) {
		this.endRows = endRows;
	}

	public String getProductsPrice() {
		return productsPrice;
	}
	
	public Integer getProductsPriceInteger() {
		Integer price = 0;
		if (this.productsPrice != null) {
			price = Integer.valueOf(productsPrice.toString());
		}
		return price / 100;
	}

	public void setProductsPrice(String productsPrice) {
		this.productsPrice = productsPrice;
	}

	public Integer getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Integer ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Integer getHotelNum() {
		return hotelNum;
	}

	public void setHotelNum(Integer hotelNum) {
		this.hotelNum = hotelNum;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	/*
	 * 排序：存在门票产品的景区向前靠（ticketNum为place_search_info字段）
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public static class compareTicketNumDesc implements Comparator<Place> {
		public int compare(Place p1, Place p2) {
			Place s1 = (Place) p1;
			if (s1.ticketNum < 1)
				return 1;
			else
				return 0;
		  }
	}

	/*
	 * 排序：存在酒店产品的酒店向前靠（ticketNum为place_search_info字段）
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public static class compareHotelNumDesc implements Comparator<Place> {
		public int compare(Place p1, Place p2) {
			Place s1 = (Place) p1;
			if (s1.hotelNum < 1)
				return 1;
			else
				return 0;
		}
	}
	
	/**
	 * 是否是国外的模板
	 * @return 
	 */
	public boolean isAbroadTemplate() {
		return "template_abroad".equalsIgnoreCase(template);
	}
	
	/**
	 * 是否是国内的模板
	 * @return
	 */
	public boolean isHomeTemplate() {
		return "template_zhongguo".equalsIgnoreCase(template);
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getMiddleImage() {
		return middleImage;
	}

	public void setMiddleImage(String middleImage) {
		this.middleImage = middleImage;
	}

	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public String getGuideUrl() {
		return guideUrl;
	}

	public void setGuideUrl(String guideUrl) {
		this.guideUrl = guideUrl;
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
	
	public Integer getMarketPriceInteger() {
		Integer price = 0;
		if (this.marketPrice != null) {
			price = Integer.valueOf(marketPrice.toString());
		}
		return price / 100;
	}

	public Integer getSellPriceInteger() {
		Integer price = 0;
		if (this.sellPrice != null) {
			price = Integer.valueOf(sellPrice.toString());
		}
		return price / 100;
	}

	public String getIsClient() {
		return isClient;
	}

	public void setIsClient(String isClient) {
		this.isClient = isClient;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getCmtTitle() {
		return cmtTitle;
	}

	public void setCmtTitle(String cmtTitle) {
		this.cmtTitle = cmtTitle;
	}
	public String getLargeImageUrl(){
		if(StringUtils.isBlank(getLargeImage())){
			return Constant.DEFAULT_PIC;
		}
		return "http://pic.lvmama.com/"+getLargeImage();
	}
	public String getSmallImageUrl(){
		if(StringUtils.isBlank(getSmallImage())){
			return Constant.DEFAULT_PIC;
		}
		return "http://pic.lvmama.com/"+getSmallImage();
	}

	public List<PlacePhoto> getPlacePhoto() {
		return placePhoto;
	}

	public void setPlacePhoto(List<PlacePhoto> placePhoto) {
		this.placePhoto = placePhoto;
	}

	public String getParentPlaceName() {
		return parentPlaceName;
	}

	public void setParentPlaceName(String parentPlaceName) {
		this.parentPlaceName = parentPlaceName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getTrafficInfo() {
		return trafficInfo;
	}

	public void setTrafficInfo(String trafficInfo) {
		this.trafficInfo = trafficInfo;
	}

	public PlaceHotel getPlaceHotel() {
		return placeHotel;
	}

	public void setPlaceHotel(PlaceHotel placeHotel) {
		this.placeHotel = placeHotel;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}
	@Override
	public String toString() {
		return "Place [placeId=" + placeId + ", name=" + name + ", pinYin=" + pinYin + ", pinYinUrl=" + pinYinUrl + ", seoName=" + seoName + ", seoTitle=" + seoTitle + ", seoContent=" + seoContent + ", seoKeyword=" + seoKeyword + ", seoDescription=" + seoDescription + ", template=" + template + ", isExit=" + isExit + ", hfkw=" + hfkw + ", placeType=" + placeType + ", remarkes=" + remarkes
				+ ", parentPlaceId=" + parentPlaceId + ", parentPlaceName=" + parentPlaceName + ", seq=" + seq + ", seqActivity=" + seqActivity + ", isBottom=" + isBottom + ", prodEnable=" + prodEnable + ", stage=" + stage + ", smallImage=" + smallImage + ", middleImage=" + middleImage + ", largeImage=" + largeImage + ", guideUrl=" + guideUrl + ", marketPrice=" + marketPrice + ", sellPrice="
				+ sellPrice + ", isClient=" + isClient + ", mainTitle=" + mainTitle + ", cmtTitle=" + cmtTitle + ", isValid=" + isValid + ", address=" + address + ", scenicOpenTime=" + scenicOpenTime + ", scenicRecommendTime=" + scenicRecommendTime + ", scenicGrade=" + scenicGrade + ", scenicSecondTopic=" + scenicSecondTopic + ", firstTopic=" + firstTopic + ", scenicSecondTopicOld="
				+ scenicSecondTopicOld + ", firstTopicOld=" + firstTopicOld + ", feature=" + feature + ", orderNotice=" + orderNotice + ", importantTips=" + importantTips + ", description=" + description + ", createTime=" + createTime + ", updateTime=" + updateTime + ", defaultUrl=" + defaultUrl + ", isHasActivity=" + isHasActivity + ", placePhoto=" + placePhoto + ", placeActivity="
				+ placeActivity + ", cashRefund=" + cashRefund + ", productsPrice=" + productsPrice + ", ticketNum=" + ticketNum + ", hotelNum=" + hotelNum + ", commentCount=" + commentCount + ", trafficInfo=" + trafficInfo + ",  codeId=" + codeId + ", placeHotel=" + placeHotel + ", avgScore=" + avgScore + ", imgUrl=" + imgUrl + ", startRows="
				+ startRows + ", endRows=" + endRows + ", phone=" + phone + ", province=" + province + ", city=" + city + ", placeProvince=" + placeProvince + ", placeCity=" + placeCity + ", enName=" + enName + ", airportCode=" + airportCode + "]";
	}
	public String getDestinationExplore() {
		return destinationExplore;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getParentPinYin() {
		return parentPinYin;
	}

	public void setParentPinYin(String parentPinYin) {
		this.parentPinYin = parentPinYin;
	}
	
	public void setDestinationExplore(String destinationExplore) {
		this.destinationExplore = destinationExplore;
	}

	public String getKuaiSuRuYuan() {
		return kuaiSuRuYuan;
	}

	public void setKuaiSuRuYuan(String kuaiSuRuYuan) {
		this.kuaiSuRuYuan = kuaiSuRuYuan;
	}

	public String getGuiJiuPei() {
		return guiJiuPei;
	}

	public void setGuiJiuPei(String guiJiuPei) {
		this.guiJiuPei = guiJiuPei;
	}

	public String getSuiShiTui() {
		return suiShiTui;
	}

	public void setSuiShiTui(String suiShiTui) {
		this.suiShiTui = suiShiTui;
	}

	public String getRuYuanBaoZhang() {
		return ruYuanBaoZhang;
	}

	public void setRuYuanBaoZhang(String ruYuanBaoZhang) {
		this.ruYuanBaoZhang = ruYuanBaoZhang;
	}

	public String getHasSensitiveWord() {
		return hasSensitiveWord;
	}

	public void setHasSensitiveWord(String hasSensitiveWord) {
		this.hasSensitiveWord = hasSensitiveWord;
	}
}