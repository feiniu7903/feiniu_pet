package com.lvmama.comm.pet.po.place;

import java.io.Serializable;
import java.util.Date;

public class SpecialTopic  implements Serializable {
	private Long id;
	private String idcode;
	private String title;
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	private String remark;
	private String bannerImage;
	private String smallImage;
	private Integer reused;// 来年是否重用
	private Date startDate;
	private Date endDate;
	private Date created;
	private Integer jumped;
	private String jumpurl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBannerImage() {
		return bannerImage;
	}
	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public Integer getReused() {
		return reused;
	}
	public void setReused(Integer reused) {
		this.reused = reused;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Integer getJumped() {
		return jumped;
	}
	public void setJumped(Integer jumped) {
		this.jumped = jumped;
	}
	public String getJumpurl() {
		return jumpurl;
	}
	public void setJumpurl(String jumpurl) {
		this.jumpurl = jumpurl;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
