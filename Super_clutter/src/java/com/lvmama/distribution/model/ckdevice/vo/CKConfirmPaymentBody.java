package com.lvmama.distribution.model.ckdevice.vo;

import org.dom4j.DocumentException;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ConstantMsg;

public class CKConfirmPaymentBody implements CKBody {

	String orderId;
	String paymentStatus;
	String phoneNo;
	String checksum;
	
	
	public String getOrderId() {
		return orderId;
	}

	public Long getLongOrderId() {
		return  Long.valueOf(orderId.trim());
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	@Override
	public void init(String requestXml) throws DocumentException {
		orderId = TemplateUtils.getElementValue(requestXml,"//request/body/order/orderId");
		paymentStatus = TemplateUtils.getElementValue(requestXml,"//request/body/order/paymentStatus");
		phoneNo = TemplateUtils.getElementValue(requestXml,"//request/body/order/phoneNo");
		checksum = TemplateUtils.getElementValue(requestXml,"//request/body/order/checksum");
	}

	@Override
	public String checkParams() {
		if (!StringUtil.isNumber(orderId)) {
			return ConstantMsg.CK_MSG.UNDEFINED_PARAM.getCode();
		}
		if (!Constant.PAYMENT_STATUS.PAYED.name().equals(getPaymentStatus())) {
			return "1025";
		}
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}

}
