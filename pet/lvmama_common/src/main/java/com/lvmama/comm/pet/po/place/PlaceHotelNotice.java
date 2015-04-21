package com.lvmama.comm.pet.po.place;

import java.io.Serializable;
import java.util.Date;

public class PlaceHotelNotice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long noticeId;
	private Long placeId;
	private String noticeContent;
	private Date noticeStartTime;
	private Date noticeEndTime;
	private Date noticeCreateTime;
	private String noticeType;	//公告类型(对内:internal   全部:all    一句话推荐:recommend 旅游公告:product)
	//结束时间在当前之前
	private String validNotice;
	public Long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public Date getNoticeStartTime() {
		return noticeStartTime;
	}
	public void setNoticeStartTime(Date noticeStartTime) {
		this.noticeStartTime = noticeStartTime;
	}
	public Date getNoticeEndTime() {
		return noticeEndTime;
	}
	public void setNoticeEndTime(Date noticeEndTime) {
		this.noticeEndTime = noticeEndTime;
	}
	public Date getNoticeCreateTime() {
		return noticeCreateTime;
	}
	public void setNoticeCreateTime(Date noticeCreateTime) {
		this.noticeCreateTime = noticeCreateTime;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getValidNotice() {
		return validNotice;
	}
	public void setValidNotice(String validNotice) {
		this.validNotice = validNotice;
	}
}
