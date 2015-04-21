package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlaceHotelOtherRecommend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long recommendId;  //其它介绍名称
	private Long placeId; //酒店ID
	private String recommendName; //名称
	private String recommentContent; //内容
	private String recommentType; //类型  (特色:special   玩法: playing)
	private String recommentCreatetime; //时间
	private String recommentContentNoHtml;
	private String seqNum;
	public Long getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(Long recommendId) {
		this.recommendId = recommendId;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	public String getRecommendName() {
		return recommendName;
	}
	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}
	public String getRecommentContent() {
		return recommentContent;
	}
	public void setRecommentContent(String recommentContent) {
		this.recommentContent = recommentContent;
	}
	public String getRecommentType() {
		return recommentType;
	}
	public void setRecommentType(String recommentType) {
		this.recommentType = recommentType;
	}
	public String getRecommentCreatetime() {
		return recommentCreatetime;
	}
	public void setRecommentCreatetime(String recommentCreatetime) {
		this.recommentCreatetime = recommentCreatetime;
	}
	public String getRecommentContentNoHtml() {
		return recommentContentNoHtml;
	}
	public void setRecommentContentNoHtml(String recommentContentNoHtml) {
		this.recommentContentNoHtml = recommentContentNoHtml;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
}
