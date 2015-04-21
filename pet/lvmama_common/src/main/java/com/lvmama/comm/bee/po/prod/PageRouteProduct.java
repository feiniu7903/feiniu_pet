package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.lvmama.comm.utils.PriceUtil;

public class PageRouteProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7680286072580273678L;
	private String productName;
	private String groupName;
	private String days;
	private String bizcode;
	private float memberPrice;
	private float makerPrice = 300.3344f;
	private String routeFrom;
	private String routeTo;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getBizcode() {
		return bizcode;
	}
	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
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
	public float getMemberPrice() {
		DecimalFormat df=new DecimalFormat("#.##"); 
		return new Float(df.format(this.memberPrice));
	}
	public void setMemberPrice(long memberPrice) {
		this.memberPrice = PriceUtil.convertToYuan(memberPrice);
	}
	public float getMakerPrice() {
		DecimalFormat df=new DecimalFormat("#.##"); 
		return new Float(df.format(this.makerPrice));
	}
	public void setMakerPrice(long makerPrice) {
		this.makerPrice = PriceUtil.convertToYuan(makerPrice);;
	}
	public float getDiscount() {
		float dis = this.getMemberPrice()/this.getMakerPrice();
		DecimalFormat df=new DecimalFormat("#.#"); 
		return new Float(df.format(dis*10));
	}

}
