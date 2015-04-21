package com.lvmama.comm.pet.po.visa;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.Constant;

public class VisaApproval implements Serializable {
	/**
	 *  序列值
	 */
	private static final long serialVersionUID = 9147760333271950047L;
	private Long visaApprovalId;
	/**
	 * 团号
	 */
	private String travelGroupCode;
	
	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 游玩时间.
	 */
	private Date visitTime;
	/**
	 * 游玩人标识
	 */
	private Long personId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 签证类型
	 */
	private String visaType;
	/**
	 * 出签城市
	 */
	private String city;
	/**
	 * 人群
	 */
	private String occupation;
	/**
	 * 签证状态
	 */
	private String visaStatus;
	/**
	 * 是否可返回材料
	 */
	private String returnMaterial;
	/**
	 * 是否可返回保证金
	 */
	private String returnBail;
	/**
	 * 保证金类型
	 */
	private String depositType;
	/**
	 * 保证金开户行
	 */
	private String bank;
	/**
	 * 保证金
	 */
	private Long amount;
	/**
	 * 签证产品名字
	 */
	private String productName;
	/**
	 * 提前预定天数
	 */
	private Integer aheadDay;
	
	/**
	 * 审核明细列表
	 */
	private List<VisaApprovalDetails> visaApprovalDetailsList;
	
	/**
	 * 材料最晚提交时间
	 * @return
	 */
	public Date getLastDays() {
		if (null == aheadDay || 0 == aheadDay) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(visitTime);
			cal.add(Calendar.DAY_OF_YEAR, 0 - aheadDay);
			return cal.getTime();
		}
	}	
	
	public String getCnOccupation() {
		if (null != occupation) {
			return Constant.VISA_OCCUPATION.VISA_FOR_EMPLOYEE.getCnName(occupation);
		} else {
			return "";
		}		
	}
	
	public String getCnVisaStatus() {
		if (null != visaStatus) {
			return Constant.VISA_STATUS.getCnName(visaStatus);
		} else {
			return "";
		}
	}
	
	public Long getVisaApprovalId() {
		return visaApprovalId;
	}
	public void setVisaApprovalId(Long visaApprovalId) {
		this.visaApprovalId = visaApprovalId;
	}
	public String getTravelGroupCode() {
		return travelGroupCode;
	}
	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getVisaStatus() {
		return visaStatus;
	}
	public void setVisaStatus(String visaStatus) {
		this.visaStatus = visaStatus;
	}
	public String getReturnMaterial() {
		return returnMaterial;
	}
	public void setReturnMaterial(String returnMaterial) {
		this.returnMaterial = returnMaterial;
	}
	public String getReturnBail() {
		return returnBail;
	}
	public void setReturnBail(String returnBail) {
		this.returnBail = returnBail;
	}
	public String getDepositType() {
		return depositType;
	}
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getAheadDay() {
		return aheadDay;
	}

	public void setAheadDay(Integer aheadDay) {
		this.aheadDay = aheadDay;
	}

	public List<VisaApprovalDetails> getVisaApprovalDetailsList() {
		return visaApprovalDetailsList;
	}

	public void setVisaApprovalDetailsList(
			List<VisaApprovalDetails> visaApprovalDetailsList) {
		this.visaApprovalDetailsList = visaApprovalDetailsList;
	}
	
}
