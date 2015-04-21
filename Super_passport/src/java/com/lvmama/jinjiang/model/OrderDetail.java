package com.lvmama.jinjiang.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 查询订单详细
 * @author chenkeke
 *
 */
public class OrderDetail {
	private String orderNo;
	private String thirdOrderNo;
	private String groupCode;
	private Contact contact;
	private Integer totalPersonsNum;
	private List<Guest> guests;
	private Integer adultNum;
	private Integer childNum;
	private List<Receivable> receivables;
	private BigDecimal totalAmount;
	private BigDecimal actualAmount;
	private BigDecimal lossAmount;
	private String orderStatus;
	private String payStatus;
	private String remark;
	private Date createTime;
	private Date updateTime;
	private String refundRemark;//退款备注
	public String getRefundRemark() {
		return refundRemark;
	}
	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getThirdOrderNo() {
		return thirdOrderNo;
	}
	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Integer getTotalPersonsNum() {
		return totalPersonsNum;
	}
	public void setTotalPersonsNum(Integer totalPersonsNum) {
		this.totalPersonsNum = totalPersonsNum;
	}
	public List<Guest> getGuests() {
		return guests;
	}
	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}
	public Integer getAdultNum() {
		return adultNum;
	}
	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}
	public Integer getChildNum() {
		return childNum;
	}
	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}
	public List<Receivable> getReceivables() {
		return receivables;
	}
	public void setReceivables(List<Receivable> receivables) {
		this.receivables = receivables;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getLossAmount() {
		return lossAmount;
	}
	public void setLossAmount(BigDecimal lossAmount) {
		this.lossAmount = lossAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
}
