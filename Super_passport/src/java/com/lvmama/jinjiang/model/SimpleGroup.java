package com.lvmama.jinjiang.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 实时获取团信息
 * @author chenkeke
 *
 */
public class SimpleGroup {
	private String groupCode;
	private Date departDate;
	private Date returnDate;
	private String groupStatus;
	private Integer advanceCloseDSO = 1; //提前预定天数
	/*private Integer planNum;
	private Integer reserveNum;
	private Integer paidInNum;
	private Integer reservationNum;
	private Integer adultNum;
	private Integer childNum;*/
	private Integer limitNum;
	private Integer requireNum;
	private List<GroupPrice> prices;
	private BigDecimal subAmount;
	private Date updateTime;
	private Map<String, String> extension;
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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
	public Integer getAdvanceCloseDSO() {
		return advanceCloseDSO;
	}
	public void setAdvanceCloseDSO(Integer advanceCloseDSO) {
		this.advanceCloseDSO = advanceCloseDSO;
	}
	public Integer getRequireNum() {
		return requireNum;
	}
	public void setRequireNum(Integer requireNum) {
		this.requireNum = requireNum;
	}
	public List<GroupPrice> getPrices() {
		return prices;
	}
	public void setPrices(List<GroupPrice> prices) {
		this.prices = prices;
	}
	public BigDecimal getSubAmount() {
		return subAmount;
	}
	public void setSubAmount(BigDecimal subAmount) {
		this.subAmount = subAmount;
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
