package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant.VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE;
/**
 * 旅游须知
 * @author yanzhirong
 *
 */
public class TravelTips implements Serializable{

	private static final long serialVersionUID = -1494839541033526669L;
	
	/** 主键ID*/
	private Long travelTipsId;
	
	/** 洲域*/
	private String continent;
	
	/** 国家*/
	private String country;
	
	/** 须知名称*/
	private String tipsName;
	
	/** 须知内容*/
	private String content;

	public Long getTravelTipsId() {
		return travelTipsId;
	}

	public void setTravelTipsId(Long travelTipsId) {
		this.travelTipsId = travelTipsId;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}
	
	public String getContinentCnName(){
		return VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE.getCnName(this.continent);
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
		
}
