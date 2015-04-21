package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class ProdRoute extends ProdProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2016340981673280663L;
	private Long routeId;
    private Long days;
    private Long initialNum;	//最少成团人数
    private String routeFrom;
    private String routeTo;
    private Integer aheadConfirmHours = 0;  //提前确认成团时间 
    private String eContract;
    private String travelGroupCode; //订单团号前辍
    private String routeCategory;
    private String isPlay;
    private String routeTitle;
    private String hotelType;
    private String routeStandard;
    private String departArea;
    private String routeTitle_one="";//用于页面线路主题1显示
    private String routeTitle_two="";//用于页面线路主题2显示
    private String selfPack;//自主打包属性，为true为自主打包
    private String qiFlag="false";//是否要加“起”的标志
    private String groupType;//组合方式
    private String travelTime;
    private String country;
    private String city;
    private String visaType;
    private boolean hasBusinessCoupon;
    private String isMultiJourney = "N";//是否为多行程
    
    public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getDays() {
    	return (days==null?1L:days);        
    }

    public void setDays(Long days) {
        this.days = days;
    }

	public Long getInitialNum() {
		return initialNum;
	}

	public void setInitialNum(Long initialNum) {
		this.initialNum = initialNum;
	}

	public String getRouteFrom() {
		return routeFrom;
	}

	public void setRouteFrom(String routeFrom) {
		this.routeFrom = routeFrom;
	}

	public String getRouteTo() {
		return routeTo;
	}

	public void setRouteTo(String routeTo) {
		this.routeTo = routeTo;
	}
	
	public String getProductType() {
		return Constant.PRODUCT_TYPE.ROUTE.name();
	}

	public Integer getAheadConfirmHours() {
		return aheadConfirmHours;
	}

	public void setAheadConfirmHours(Integer aheadConfirmHours) {
		this.aheadConfirmHours = aheadConfirmHours;
	}

	public String geteContract() {
		return eContract;
	}

	public void seteContract(String eContract) {
		this.eContract = eContract;
	}
 
	@Override
	public boolean isEContract() {
		return Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name().equalsIgnoreCase(eContract);

	}
	
	@Override
	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	} 

	public String getRouteCategory() {
		return routeCategory;
	}

	public void setRouteCategory(String routeCategory) {
		this.routeCategory = routeCategory;
	}

	public String getIsPlay() {
		return isPlay;
	}

	public void setIsPlay(String isPlay) {
		this.isPlay = isPlay;
	}

	public String getRouteTitle() {
		return routeTitle;
	}

	public void setRouteTitle(String routeTitle) {
		this.routeTitle = routeTitle;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getRouteStandard() {
		return routeStandard;
	}

	public void setRouteStandard(String routeStandard) {
		this.routeStandard = routeStandard;
	}

	public String getDepartArea() {
		return departArea;
	}

	public void setDepartArea(String departArea) {
		this.departArea = departArea;
	}

	public String getRouteTitle_one() {
		if(!StringUtil.isEmptyString(this.getRouteTitle())){
			this.routeTitle_one=this.getRouteTitle().split(",")[0];
		}
		return routeTitle_one;
	}

	public String getRouteTitle_two() {
		if(!StringUtil.isEmptyString(this.getRouteTitle())&&this.getRouteTitle().split(",").length>=2){
			String splie_two=this.getRouteTitle().split(",")[1];
			if(!"null".equals(splie_two)&&splie_two.length()>0){
				this.routeTitle_two=this.getRouteTitle().split(",")[1];
			}
		}
		return this.routeTitle_two;
	}

	public void setRouteTitle_one(String routeTitleOne) {
		routeTitle_one = routeTitleOne;
	}

	public void setRouteTitle_two(String routeTitleTwo) {
		routeTitle_two = routeTitleTwo;
	}

	/**
	 * @return the selfPack
	 */
	public String getSelfPack() {
		return selfPack;
	}

	/**
	 * @param selfPack the selfPack to set
	 */
	public void setSelfPack(String selfPack) {
		this.selfPack = selfPack;
	}

	@Override
	public boolean hasSelfPack(){
		return StringUtils.equals(selfPack, "true");
	}

	/**
	 * @return the qiFlag
	 */
	public String getQiFlag() {
		return qiFlag;
	}

	/**
	 * @param qiFlag the qiFlag to set
	 */
	public void setQiFlag(String qiFlag) {
		this.qiFlag = qiFlag;
	}
	
	public boolean hasQiFlag(){
		return StringUtils.equals("true", qiFlag);
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public boolean isHasBusinessCoupon() {
		return hasBusinessCoupon;
	}

	public void setHasBusinessCoupon(boolean hasBusinessCoupon) {
		this.hasBusinessCoupon = hasBusinessCoupon;
	}

	public String getCountry() {
		return country;
	}
	
	public String getCnCountry() {
		if(StringUtils.isNotBlank(country)){
		  int i=country.indexOf("(");
		  if(i!=-1){
			  return country.substring(0,i);
		  }else {
			  return country;
		  }
		}else {
			return country;
		}
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	
	public String getCnVisaType() {
		if (null != visaType) {
			String type=Constant.VISA_TYPE.BUSINESS_VISA.getCnName(visaType);
			
			return type.substring(type.length()-2);
		} else {
			return "";
		}
	}

	public String getZhMultiJourney() {
		if(StringUtils.isEmpty(isMultiJourney)) {
			return "单行程";
		}
		return "Y".equalsIgnoreCase(isMultiJourney)?"多行程":"单行程";
	}
	
	public boolean hasMultiJourney() {
		if(StringUtils.isEmpty(isMultiJourney)) {
			return false;
		}
		return "Y".equalsIgnoreCase(isMultiJourney)?true:false;
	}

	public String getIsMultiJourney() {
		return isMultiJourney;
	}

	public void setIsMultiJourney(String isMultiJourney) {
		this.isMultiJourney = isMultiJourney;
	}
}
