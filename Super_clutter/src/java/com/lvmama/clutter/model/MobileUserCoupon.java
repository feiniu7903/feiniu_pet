package com.lvmama.clutter.model;

import java.io.Serializable;

/**
 * 移动端专用 - 用户优惠劵信息 
 * @author qinzubo
 *
 */
public class MobileUserCoupon implements Serializable{
	private String name; //  名称
	private String expiredDate;// 到期时间 
	private String code;// 代码
	private String price;// 价格
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	

}
