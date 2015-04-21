package com.lvmama.jinjiang.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 签证信息
 * @author chenkeke
 *
 */
public class Visa {
	private String code; // 签证代码
	private String destination; // 目的地
	private String sendPlace; // 送签地
	private String guestType; // 游客类型
	private String materialName; // 材料名称
	private Integer needNum; // 需要份数
	private String materialRemark; // 材料说明
	private String visaType; // 签证类型
	private BigDecimal bondAmount; // 担保金
	private Date updateTime; // 更新时间
	private String remark;	// 备注
	private List<MaterialAttach> attachs; // 材料附件

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSendPlace() {
		return sendPlace;
	}

	public void setSendPlace(String sendPlace) {
		this.sendPlace = sendPlace;
	}

	public String getGuestType() {
		return guestType;
	}

	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getNeedNum() {
		return needNum;
	}

	public void setNeedNum(Integer needNum) {
		this.needNum = needNum;
	}

	public String getMaterialRemark() {
		return materialRemark;
	}

	public void setMaterialRemark(String materialRemark) {
		this.materialRemark = materialRemark;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public BigDecimal getBondAmount() {
		return bondAmount;
	}

	public void setBondAmount(BigDecimal bondAmount) {
		this.bondAmount = bondAmount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<MaterialAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<MaterialAttach> attachs) {
		this.attachs = attachs;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
