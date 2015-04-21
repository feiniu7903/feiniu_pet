package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
/**
 * 产品的旅行须知关联类
 * @author yanzhirong
 *
 */
public class ViewTravelTips implements Serializable{

	private static final long serialVersionUID = 7854504171122120301L;
	
	/**主键ID*/
	private Long viewTravelTipsId;
	
	/**产品ID*/
	private Long productId;
	
	/**旅行须知ID*/
	private Long travelTipsId;
	
	/** 国家*/
	private String country;
	
	/** 须知名称*/
	private String tipsName;

	public Long getViewTravelTipsId() {
		return viewTravelTipsId;
	}

	public void setViewTravelTipsId(Long viewTravelTipsId) {
		this.viewTravelTipsId = viewTravelTipsId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getTravelTipsId() {
		return travelTipsId;
	}

	public void setTravelTipsId(Long travelTipsId) {
		this.travelTipsId = travelTipsId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTipsName() {
		return tipsName;
	}

	public void setTipsName(String tipsName) {
		this.tipsName = tipsName;
	}
	
	
}
