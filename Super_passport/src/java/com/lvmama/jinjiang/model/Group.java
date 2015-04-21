package com.lvmama.jinjiang.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 团信息
 * @author chenkeke
 *
 */
public class Group {
	private String groupCode; // 团代码
	private Integer journeyDays; // 行程天数
	private String groupCategory; // 团类型
	private Date departDate; // 出团日期
	private Date returnDate; // 返程日期
	private String groupStatus; // 团状态
	private String seo; // 团SEO标签
	private Integer advanceCloseDSO = 1; // 提前截止天数 默认1天
	/*private Integer planNum; // 计划人数
	private Integer reserveNum; // 预留人数
	private Integer paidInNum; // 实收人数
	private Integer reservationNum; // 预订人数
	private Integer adultNum; // 成人数
	private Integer childNum; // 儿童数*/
	private Integer	limitNum;//剩余人数
	private Boolean isRecommendation; // 是否推荐
	private String saleRemark; // 费用说明
	private String district; // 区域
	private String destination; // 目的地
	private Integer guidesNum; // 派陪人数
	private String orderConfirmType; // 订单确认方式
	private String serviceStds; // 服务标准
	private String tips; // 小贴士
	private String priceInclude; // 费用包含
	private String priceExclude; // 费用不包含
	private String remark; // 备注
	private Integer requireNum; // 成团人数
	private String departPlace; // 出发地
	private LineFeature lineFeature; // 线路特色
	private List<GroupPrice> prices; // 价格列表
	private List<Journey> journeys; // 行程列表
	private String responsibleName; // 团控人
	private String responsibleTel; // 团控电话
	private String responsibleMobile; // 团控手机
	private String name; // 团名称
	private String explainMeetingPlace; // 说明会地地址
	private Date explainMeetingBeginTime; // 说明会开始时间
	private Date explainMeetingEndTime; // 说明会结束时间
	private List<MediaAttach> attachs;// 附件列表
	private BigDecimal subAmount; // 订金金额
	private String visaCode; // 签证代码
	private Date visaUpdateTime; // 签证更新时间
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private Map<String, String> extension; // 备用字段

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getJourneyDays() {
		return journeyDays;
	}

	public void setJourneyDays(Integer journeyDays) {
		this.journeyDays = journeyDays;
	}

	public String getGroupCategory() {
		return groupCategory;
	}

	public void setGroupCategory(String groupCategory) {
		this.groupCategory = groupCategory;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public String getSeo() {
		return seo;
	}

	public void setSeo(String seo) {
		this.seo = seo;
	}

	public Integer getAdvanceCloseDSO() {
		return advanceCloseDSO;
	}

	public void setAdvanceCloseDSO(Integer advanceCloseDSO) {
		this.advanceCloseDSO = advanceCloseDSO;
	}

	public Boolean getIsRecommendation() {
		return isRecommendation;
	}

	public void setIsRecommendation(Boolean isRecommendation) {
		this.isRecommendation = isRecommendation;
	}

	public String getSaleRemark() {
		return saleRemark;
	}

	public void setSaleRemark(String saleRemark) {
		this.saleRemark = saleRemark;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getGuidesNum() {
		return guidesNum;
	}

	public void setGuidesNum(Integer guidesNum) {
		this.guidesNum = guidesNum;
	}

	public String getOrderConfirmType() {
		return orderConfirmType;
	}

	public void setOrderConfirmType(String orderConfirmType) {
		this.orderConfirmType = orderConfirmType;
	}

	public String getServiceStds() {
		return serviceStds;
	}

	public void setServiceStds(String serviceStds) {
		this.serviceStds = serviceStds;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getPriceInclude() {
		return priceInclude;
	}

	public void setPriceInclude(String priceInclude) {
		this.priceInclude = priceInclude;
	}

	public String getPriceExclude() {
		return priceExclude;
	}

	public void setPriceExclude(String priceExclude) {
		this.priceExclude = priceExclude;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRequireNum() {
		return requireNum;
	}

	public void setRequireNum(Integer requireNum) {
		this.requireNum = requireNum;
	}

	public String getDepartPlace() {
		return departPlace;
	}

	public void setDepartPlace(String departPlace) {
		this.departPlace = departPlace;
	}


	public LineFeature getLineFeature() {
		return lineFeature;
	}

	public void setLineFeature(LineFeature lineFeature) {
		this.lineFeature = lineFeature;
	}

	public List<GroupPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<GroupPrice> prices) {
		this.prices = prices;
	}

	public List<Journey> getJourneys() {
		return journeys;
	}

	public void setJourneys(List<Journey> journeys) {
		this.journeys = journeys;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getResponsibleTel() {
		return responsibleTel;
	}

	public void setResponsibleTel(String responsibleTel) {
		this.responsibleTel = responsibleTel;
	}

	public String getResponsibleMobile() {
		return responsibleMobile;
	}

	public void setResponsibleMobile(String responsibleMobile) {
		this.responsibleMobile = responsibleMobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExplainMeetingPlace() {
		return explainMeetingPlace;
	}

	public void setExplainMeetingPlace(String explainMeetingPlace) {
		this.explainMeetingPlace = explainMeetingPlace;
	}

	public Date getExplainMeetingBeginTime() {
		return explainMeetingBeginTime;
	}

	public void setExplainMeetingBeginTime(Date explainMeetingBeginTime) {
		this.explainMeetingBeginTime = explainMeetingBeginTime;
	}

	public Date getExplainMeetingEndTime() {
		return explainMeetingEndTime;
	}

	public void setExplainMeetingEndTime(Date explainMeetingEndTime) {
		this.explainMeetingEndTime = explainMeetingEndTime;
	}

	public List<MediaAttach> getAttachs() {
		return attachs;
	}
	
	public void setAttachs(List<MediaAttach> attachs) {
		this.attachs = attachs;
	}

	public BigDecimal getSubAmount() {
		return subAmount;
	}

	public void setSubAmount(BigDecimal subAmount) {
		this.subAmount = subAmount;
	}

	public String getVisaCode() {
		return visaCode;
	}

	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}

	public Date getVisaUpdateTime() {
		return visaUpdateTime;
	}

	public void setVisaUpdateTime(Date visaUpdateTime) {
		this.visaUpdateTime = visaUpdateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Map<String, String> getExtension() {
		return extension;
	}

	public void setExtension(Map<String, String> extension) {
		this.extension = extension;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

}
