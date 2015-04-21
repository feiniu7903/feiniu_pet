package com.lvmama.jinjiang.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 * 订单
 * @author chenkeke
 *
 */
public class Order {
	private String thirdOrderNo;
	private String groupCode;
	private Contact contact;
	private Integer totalPersonsNum;
	private List<Guest> guests;
	private Integer adultNum;
	private Integer childNum;
	private List<Receivable> receivables;
	private BigDecimal totalAmount;
	private String remark;
	private Map<String,String> extension;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Map<String, String> getExtension() {
		return extension;
	}
	public void setExtension(Map<String, String> extension) {
		this.extension = extension;
	}
}
