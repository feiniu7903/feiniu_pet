package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.util.List;

public class CcProd implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5804755924960712365L;
	private String description;
	private String tip;//游玩须知
	private List<String> placeNameList;
	private String feeInclude;
	private String smsContent;
	private String notice;
	private String managerRecommend;
	private String journeyRefer;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public List<String> getPlaceNameList() {
		return placeNameList;
	}
	public void setPlaceNameList(List<String> placeNameList) {
		this.placeNameList = placeNameList;
	}
	public String getFeeInclude() {
		return feeInclude;
	}
	public void setFeeInclude(String feeInclude) {
		this.feeInclude = feeInclude;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getManagerRecommend() {
		return managerRecommend;
	}
	public void setManagerRecommend(String managerRecommend) {
		this.managerRecommend = managerRecommend;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getJourneyRefer() {
		return journeyRefer;
	}
	public void setJourneyRefer(String journeyRefer) {
		this.journeyRefer = journeyRefer;
	}

}
