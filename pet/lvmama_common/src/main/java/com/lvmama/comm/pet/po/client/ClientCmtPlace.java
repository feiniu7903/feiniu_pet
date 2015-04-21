package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;

public class ClientCmtPlace implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5749024738449710615L;
	String content;
	String avgScore;
	String createdTimeStr;
	String cashRefund;
	String point;
	String userName;
	String auditStatu;
	String chAudit;
	String placeName;
	String placeId;
	String cmtType;
	String orderId;
	String productName;
	/**
	 * 点评维度
	 */
	private List<CmtLatitudeVO> cmtLatitudes;
	
	public List<CmtLatitudeVO> getCmtLatitudes() {
		return cmtLatitudes;
	}
	public void setCmtLatitudes(List<CmtLatitudeVO> cmtLatitudes) {
		this.cmtLatitudes = cmtLatitudes;
	}
	public String getChAudit() {
		return chAudit;
	}
	public void setChAudit(String chAudit) {
		this.chAudit = chAudit;
	}
	boolean isBest;	
	boolean isExperience;
	
	public String getCashRefundYuan() {
		if (StringUtils.isEmpty(this.cashRefund)) {
			return "";
		} else {
			if ("0".equals(this.cashRefund)){
				return"";
			}
			return (Long.valueOf(this.cashRefund) / 100)+"";
		}
	}
	
	public String getCreatedTimeStr() {
		return createdTimeStr;
	}
	public void setCreatedTimeStr(String createdTimeStr) {
		this.createdTimeStr = createdTimeStr;
	}
	
	public String getCashRefund() {
		return cashRefund;
	}
	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}
	public boolean isBest() {
		return isBest;
	}
	public void setBest(boolean isBest) {
		this.isBest = isBest;
	}
	public boolean isExperience() {
		return isExperience;
	}
	public void setExperience(boolean isExperience) {
		this.isExperience = isExperience;
	}
	public String getPoint() {
		return StringUtil.coverNullStrValue(point);
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getContent() {
		return StringUtil.coverNullStrValue(content);
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAvgScore() {
		return StringUtil.coverNullStrValue(avgScore);
	}
	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
	}
	public String getUserName() {
		return StringUtil.coverNullStrValue(userName);
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAuditStatu() {
		return StringUtil.coverNullStrValue(auditStatu);
	}
	public void setAuditStatu(String auditStatu) {
		this.auditStatu = auditStatu;
	}
	public String getPlaceName() {
		return StringUtil.coverNullStrValue(placeName);
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getProductName() {
		return StringUtil.coverNullStrValue(productName);
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPlaceId() {
		
		return StringUtil.coverNullStrValue(placeId);
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getCmtType() {
		return StringUtil.coverNullStrValue(cmtType);
	}
	public void setCmtType(String cmtType) {
		this.cmtType = cmtType;
	}
	public String getOrderId() {
		return  StringUtil.coverNullStrValue(orderId);
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
