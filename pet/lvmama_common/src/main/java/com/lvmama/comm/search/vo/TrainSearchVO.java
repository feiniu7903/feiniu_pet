/**
 * 
 */
package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;

/**
 * @author yangbin
 *
 */
public class TrainSearchVO extends SearchVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2521801001720500499L;
	private String arrival;
	private String departure;
	private String date;
	private String lineName;
	private Date dateDate;
	private String fromCityPinyin;
	private String toCityPinyin;
	private Long productId;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Date getDateDate() {
		return dateDate;
	}
	public void setDateDate(Date dateDate) {
		this.dateDate = dateDate;
		this.date=DateUtil.formatDate(dateDate, "yyyy-MM-dd");
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	
	public boolean isKeyEmpty(){
		return StringUtils.isEmpty(departure)||StringUtils.isEmpty(arrival);
	}

	public boolean setStationKey(String key){
		if(!key.contains("-")){
			return false;
		}
		
		String[] keys = key.split("-");
		departure = keys[0];
		arrival=keys[1];
		return true;
	}
	public String getFromCityPinyin() {
		return fromCityPinyin;
	}
	public void setFromCityPinyin(String fromCityPinyin) {
		this.fromCityPinyin = fromCityPinyin;
	}
	public String getToCityPinyin() {
		return toCityPinyin;
	}
	public void setToCityPinyin(String toCityPinyin) {
		this.toCityPinyin = toCityPinyin;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
