package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.pet.po.place.Place;

/**
 * 目的地 和 标签 关联表
 * 
 * @author chenkeyu
 * */
public class ProdPlaceTag implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Long id;

	/** 目的地 */
	private Place place;

	/** 标签 */
	private ProdTag tag = new ProdTag();

	/** 有效 开始时间 */
	private Date beginTime;

	/** 有效 结束时间 */
	private Date endTime;
	
	/** 目的地类型 */
	private String placeType;
	
	private Boolean isChecked;
	
	private String creator;//创建者
	
	public ProdPlaceTag (){
		
	}
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public ProdTag getTag() {
		return tag;
	}

	public void setTag(ProdTag tag) {
		this.tag = tag;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStrBeginTime() {
		return beginTime == null ? "" : sdf.format(beginTime);
	}

	public String getStrEndTime() {
		return endTime == null ? "" : sdf.format(endTime);
	}

	public ProdPlaceTag(Long placeTagId) {
		this.id = placeTagId;
	}
	
	
	public String getPlaceType() {
		if ("1".equals(place.getStage())) {
			this.placeType = "目的地";
		} else if ("2".equals(place.getStage())) {
			this.placeType = "景区";
		} else if ("3".equals(place.getStage())) {
			this.placeType = "酒店";
		}
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public static void main(String args[]){
		ProdPlaceTag tag = new ProdPlaceTag();
		tag.setBeginTime(null);
		System.out.println(tag.getStrBeginTime());
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}