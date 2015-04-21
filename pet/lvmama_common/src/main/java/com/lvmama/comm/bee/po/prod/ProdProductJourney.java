package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.utils.TimeUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Time;

@SuppressWarnings("serial")
public class ProdProductJourney implements Serializable{
    private Long prodJourenyId;

    private String ticketPolicy;

    private String hotelPolicy;

    private String trafficPolicy;

    private String routePolicy;

    private Long fromPlaceId;

    private Long toPlaceId;
    
    private Place fromPlace;
    private Place toPlace;

    private Long productId;
    
    /**
     * 行程当中的时间区域.
     * 格式:AA-BB===CC-DD
     * 前面的两个值为最小的行程天晚，
     * 后面的两个值为最大的行程天晚，
     * 如果值相等时为定天数
     */
    private String journeyTime;
    private Time minTime=new Time();
    private Time maxTime=new Time();
    
    private Map<String,List<ProdJourneyProduct>> prodJourneyGroupMap=new HashMap<String,List<ProdJourneyProduct>>();
    
    public Long getProdJourenyId() {
        return prodJourenyId;
    }

    public void setProdJourenyId(Long prodJourenyId) {
        this.prodJourenyId = prodJourenyId;
    }

    public String getTicketPolicy() {
        return ticketPolicy;
    }

    public void setTicketPolicy(String ticketPolicy) {
        this.ticketPolicy = ticketPolicy == null ? null : ticketPolicy.trim();
    }

    public String getHotelPolicy() {
        return hotelPolicy;
    }

    public void setHotelPolicy(String hotelPolicy) {
        this.hotelPolicy = hotelPolicy == null ? null : hotelPolicy.trim();
    }

    public String getTrafficPolicy() {
        return trafficPolicy;
    }

    public void setTrafficPolicy(String trafficPolicy) {
        this.trafficPolicy = trafficPolicy == null ? null : trafficPolicy.trim();
    }

    public String getRoutePolicy() {
        return routePolicy;
    }

    public void setRoutePolicy(String routePolicy) {
        this.routePolicy = routePolicy == null ? null : routePolicy.trim();
    }

    public Long getFromPlaceId() {
        return fromPlaceId;
    }

    public void setFromPlaceId(Long fromPlaceId) {
        this.fromPlaceId = fromPlaceId;
    }

    public Long getToPlaceId() {
        return toPlaceId;
    }

    public void setToPlaceId(Long toPlaceId) {
        this.toPlaceId = toPlaceId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

	/**
	 * @return the journeyTime
	 */
	public String getJourneyTime() {
		//return journeyTime;
		if(minTime!=null&&maxTime!=null){
			journeyTime=TimeUtil.conver(minTime, maxTime);
		}
		return journeyTime;
	}

	/**
	 * @param journeyTime the journeyTime to set
	 */
	public void setJourneyTime(String journeyTime) {
		this.journeyTime = journeyTime;
		
		//初始化minTime,maxTime
		if(StringUtils.isNotEmpty(this.journeyTime)){
			String arr[]=StringUtils.split(this.journeyTime,TimeUtil.TOKEN);
			if(!ArrayUtils.isEmpty(arr)&&arr.length>=2){
				minTime=Time.create(arr[0]);
				maxTime=Time.create(arr[1]);
			}
		}
	}

	/**
	 * @return the minTime
	 */
	public Time getMinTime() {
		return minTime;
	}

	/**
	 * @param minTime the minTime to set
	 */
	public void setMinTime(Time minTime) {
		this.minTime = minTime;
	}

	/**
	 * @return the maxTime
	 */
	public Time getMaxTime() {
		return maxTime;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(Time maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * @return the fromPlace
	 */
	public Place getFromPlace() {
		return fromPlace;
	}

	/**
	 * @param fromPlace the fromPlace to set
	 */
	public void setFromPlace(Place fromPlace) {
		this.fromPlace = fromPlace;
	}

	/**
	 * @return the toPlace
	 */
	public Place getToPlace() {
		return toPlace;
	}

	/**
	 * @param toPlace the toPlace to set
	 */
	public void setToPlace(Place toPlace) {
		this.toPlace = toPlace;
	}

	

	/**
	 * @param prodJourneyGroup the prodJourneyGroup to set
	 */
	public void setProdJourneyGroup(Map<String,List<ProdJourneyProduct>> map) {
		this.prodJourneyGroupMap=map;
	}
	
	
	
	/**
	 * @return the prodJourneyGroupMap
	 */
	public Map<String, List<ProdJourneyProduct>> getProdJourneyGroupMap() {
		return prodJourneyGroupMap;
	}

	public List<ProdJourneyProduct> getTicketList(){		
		return getProductList(Constant.PRODUCT_TYPE.TICKET);
	}
	private List<ProdJourneyProduct> getProductList(Constant.PRODUCT_TYPE pt){
		if(prodJourneyGroupMap==null){
			return null;
		}
		return prodJourneyGroupMap.get(pt.name());
	}
	public List<ProdJourneyProduct> getHotelList(){
		return getProductList(Constant.PRODUCT_TYPE.HOTEL);
	}
	
	public List<ProdJourneyProduct> getTrafficList(){
		return getProductList(Constant.PRODUCT_TYPE.TRAFFIC);
	}
	public List<ProdJourneyProduct> getRouteList(){
		return getProductList(Constant.PRODUCT_TYPE.ROUTE);
	}
	


	public boolean isPolicy(String productType){
		String val=null;
		if (StringUtils.equalsIgnoreCase(Constant.PRODUCT_TYPE.HOTEL.name(),
				productType)) {
			val = this.hotelPolicy;
		} else if (StringUtils.equalsIgnoreCase(
				Constant.PRODUCT_TYPE.ROUTE.name(), productType)) {
			val = this.routePolicy;
		} else if (StringUtils.equalsIgnoreCase(Constant.PRODUCT_TYPE.TICKET
				.name(), productType)) {
			val = this.ticketPolicy;
		} else if (StringUtils.equalsIgnoreCase(Constant.PRODUCT_TYPE.TRAFFIC
				.name(), productType)) {
			val = this.trafficPolicy;
		}
		return StringUtils.equalsIgnoreCase(val, "true");
	}
	
	public void setPolicy(String productType,String selected){
		if (StringUtils.equalsIgnoreCase(Constant.PRODUCT_TYPE.HOTEL.name(),
				productType)) {
			this.hotelPolicy=selected;
		} else if (StringUtils.equalsIgnoreCase(
				Constant.PRODUCT_TYPE.ROUTE.name(), productType)) {
			this.routePolicy=selected;
		} else if (StringUtils.equalsIgnoreCase(Constant.PRODUCT_TYPE.TICKET
				.name(), productType)) {
			this.ticketPolicy=selected;
		} else if (StringUtils.equalsIgnoreCase(Constant.PRODUCT_TYPE.TRAFFIC
				.name(), productType)) {
			this.trafficPolicy=selected;
		}
	}
	
	public String getJourneyTimeStr(){
		StringBuffer sb=new StringBuffer();
		if(minTime.isEmptyTime()&&maxTime.isEmptyTime()){
			sb.append("未定");
		}else if(minTime.equals(maxTime)){
			sb.append(maxTime.toZhString());
		}else if(minTime.isEmptyTime()||maxTime.isEmptyTime()){
			if(!minTime.isEmptyTime()){
				sb.append("最少");
				sb.append(minTime.toZhString());
			}else if(!maxTime.isEmptyTime()){				
				sb.append("最多");
				sb.append(maxTime.toZhString());
			}
		}else{
			sb.append(minTime.toZhString());
			sb.append("至");
			sb.append(maxTime.toZhString());
		}
		
		return sb.toString();
	}
}