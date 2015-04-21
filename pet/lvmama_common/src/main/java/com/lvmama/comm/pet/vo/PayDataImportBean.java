package com.lvmama.comm.pet.vo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class PayDataImportBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9121425108616813110L;
	private static final String replaceStr = " "; // ASICII 63 不是空格
	/**
	 * 时间格式为：yyyyMMddHHmmss
	 */
	private static final String DATE_FORMART = "yyyyMMddHHmmss";
	
	private String gwName;
	
	private Long fileId;
	
	private String gwSn;
	
	private String orderNo;
	/**
	 * 金额：单位分
	 */
	private Float gwAmount;
	
	private String paymentTime;
	
	private String payType;
	
	private String payStatus;
	
	private String orderStatus;

	public PayDataImportBean() {
		super();
	}
	/**
	 * 直接创建出支付对象
	 * @param gwSn 流水号
	 * @param orderNo 订单号
	 * @param gwAmount 支付金额
	 * @param paymentTime 支付时间
	 */
	public PayDataImportBean(String gwSn, String orderNo, Float gwAmount,
			String paymentTime) {
		super();
		this.gwSn = gwSn;
		this.orderNo = orderNo;
		this.gwAmount = gwAmount;
		this.paymentTime = paymentTime;
	}

	@Override
	public String toString() {
		return "gwSn:" + gwSn + " orderNo:" + orderNo + " gwAmount:" + gwAmount + " paymentTime:" + paymentTime + " payType:" + payType + " payStatus:" + payStatus + " orderStatus:" + orderStatus;
	}
	public Date getFormartPaymentTime() {
		if(paymentTime != null && !"".equals(paymentTime)) {
			paymentTime = (paymentTime+"00000000000000").substring(0, 14);// 20120701235959
			return DateUtil.getDateByStr(paymentTime, DATE_FORMART);
		}
		return null;
	}
	public String replace63(String str) {
		return str == null ? null : str.replace(replaceStr, "").replace(",", "");
	}
	public String getGwName() {
		return gwName;
	}
	public void setGwName(String gwName) {
		this.gwName = gwName;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getGwSn() {
		return gwSn;
	}
	public void setGwSn(String gwSn) {
		this.gwSn = gwSn;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Float getGwAmount() {
		return gwAmount;
	}
	public void setGwAmount(Float gwAmount) {
		this.gwAmount = gwAmount;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}
