package com.lvmama.comm.search.vo;

import java.io.Serializable;

import com.lvmama.comm.search.annotation.FilterParam;

public class TicketSearchVO extends SearchVO implements Serializable{
	public TicketSearchVO() {
		super();
	}
	
	public TicketSearchVO(String fromDest,String keyword) {
		super(fromDest, keyword);
	}
	public TicketSearchVO(String fromDest, String keyword, Integer page, Integer pageSize) {
		super(fromDest, keyword,page, pageSize);
	}
	
	private static final long serialVersionUID = 4573720942318237164L;
	
	/**筛选条件 - 包含地区*/
	@FilterParam("A")
	private String city;
	
	/**筛选条件 - 主题*/
	@FilterParam("C")
	private String subject;
	
	@FilterParam("T")
	private String tag;
	
	/**筛选条件 - 景区活动*/
	@FilterParam("R")
	private String placeActivity;
	
	/**筛选条件 - 是否有景区活动*/
	@FilterParam(value="U",transcode=false)
	private String placeActivityHave;
	
	/**促销活动*/
	@FilterParam(value="V",transcode=false)
	protected String promotion;
	/**
	 * 产品名称
	 */
	private String productName;
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPlaceActivity() {
		return placeActivity;
	}

	public void setPlaceActivity(String placeActivity) {
		this.placeActivity = placeActivity;
	}

	public String getPlaceActivityHave() {
		return placeActivityHave;
	}

	public void setPlaceActivityHave(String placeActivityHave) {
		this.placeActivityHave = placeActivityHave;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

}
