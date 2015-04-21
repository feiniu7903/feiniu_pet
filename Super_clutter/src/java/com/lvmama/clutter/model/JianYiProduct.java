package com.lvmama.clutter.model;

import java.io.Serializable;

public class JianYiProduct implements Serializable{
	
	private static final long serialVersionUID = 8363932438304688951L;
	/**
	 * 产品类别名称
	 */
	private String shortName;
	/**
	 * 产品数量和价格
	 */
	private String priceAndNum;
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getPriceAndNum() {
		return priceAndNum;
	}
	public void setPriceAndNum(String priceAndNum) {
		this.priceAndNum = priceAndNum;
	}
	

}
