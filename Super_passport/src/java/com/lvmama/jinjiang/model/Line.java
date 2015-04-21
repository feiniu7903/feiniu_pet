package com.lvmama.jinjiang.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 线路信息
 * @author chenkeke
 *
 */
public class Line {
	private String lineCode;
	private String name;
	private String travelAgency;
	private Date beginDate;
	private Date endDate;
	private String metaKey;
	private String lineStatus;
	private String businessCategory;
	private String lineRemark;
	private String sellRemark;
	private String outPeriod;
	private Date updateTime;
	private String travelBrand;
	private String lineTraffic;
	private List<Subject> subjects;
	private String lineType;
	private List<Group> groups;
	private Map<String, String> extension;
	private String departPlace;
	private String destination;
	private Integer days;
	
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getDepartPlace() {
		return departPlace;
	}
	public void setDepartPlace(String departPlace) {
		this.departPlace = departPlace;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTravelAgency() {
		return travelAgency;
	}
	public void setTravelAgency(String travelAgency) {
		this.travelAgency = travelAgency;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getMetaKey() {
		return metaKey;
	}
	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}
	public String getLineStatus() {
		return lineStatus;
	}
	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}
	public String getBusinessCategory() {
		return businessCategory;
	}
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}
	public String getLineRemark() {
		return lineRemark;
	}
	public void setLineRemark(String lineRemark) {
		this.lineRemark = lineRemark;
	}
	public String getSellRemark() {
		return sellRemark;
	}
	public void setSellRemark(String sellRemark) {
		this.sellRemark = sellRemark;
	}
	public String getOutPeriod() {
		return outPeriod;
	}
	public void setOutPeriod(String outPeriod) {
		this.outPeriod = outPeriod;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getTravelBrand() {
		return travelBrand;
	}
	public void setTravelBrand(String travelBrand) {
		this.travelBrand = travelBrand;
	}
	public String getLineTraffic() {
		return lineTraffic;
	}
	public void setLineTraffic(String lineTraffic) {
		this.lineTraffic = lineTraffic;
	}
	public List<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public Map<String, String> getExtension() {
		return extension;
	}
	public void setExtension(Map<String, String> extension) {
		this.extension = extension;
	}
}
