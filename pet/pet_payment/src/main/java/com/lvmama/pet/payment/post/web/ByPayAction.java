package com.lvmama.pet.payment.post.web;

import java.net.URLEncoder;
import java.util.Calendar;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PaymentToBankInfo;
import com.lvmama.comm.pet.service.pay.BankPaymentService;
import com.lvmama.comm.utils.InfoBase64Coding;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 百付电话支付.
 * 
 * @author liwenzhan
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/forms/byPay.ftl", type = "freemarker"),
	@Result(name = "codeFail", location = "/WEB-INF/pages/forms/byPayError.ftl", type = "freemarker")	
})
public class ByPayAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4725829137431045899L;

	/**
	 * 
	 */

	private BankPaymentService byPayPaymentService;
		
	private String payCode;

	private String timestmp;

	private String md5sign;
	
	private String objectId;
	
	private String paytotal;
	
	private String csno;
	
    private String mobilenumber;
	
	private String objectType;
	/**
	 * 业务类型.
	 */
	private String bizType;;
	
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType;
	
	private String codeInfo;
    
	/**
	 * 百付电话支付form表单提交.
	 * 
	 * @return /WEB-INF/pages/forms/byPay.ftl
	 */
	@Action("/pay/byPay")
	public String execute() {
		Calendar c = Calendar.getInstance();
		timestmp = String.valueOf(c.getTimeInMillis());
		try {
			long paytotalFen=PriceUtil.convertToFen(Float.valueOf(paytotal));
			String sendMD5 = objectId + paytotalFen + timestmp;
			md5sign = URLEncoder.encode(InfoBase64Coding.encrypt(sendMD5),"UTF-8");
			log.info("bypay : objectId="+objectId+",amount="+paytotal+",csno="+csno+",mobilenumber="+mobilenumber.trim());
			PaymentToBankInfo info = new PaymentToBankInfo();
			info.setPayAmount(paytotalFen);
			info.setCsno(csno);
			info.setMobilenumber(mobilenumber.trim());
			String merchantOrderId = SerialUtil.generate24ByteSerialAttaObjectId(Long.parseLong(objectId));
			info.setPaySerial(merchantOrderId);
			info.setObjectId(Long.parseLong(objectId));
			info.setBizType(bizType);
			info.setObjectType(objectType);
			info.setPaymentType(paymentType);
			
			BankReturnInfo returnInfo = byPayPaymentService.pay(info);
			codeInfo=returnInfo.getCodeInfo();
			if (Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(returnInfo.getSuccessFlag())) {
				payCode = returnInfo.getPayCode();
				return SUCCESS;
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		return "codeFail";
	}
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getTimestmp() {
		return timestmp;
	}

	public void setTimestmp(String timestmp) {
		this.timestmp = timestmp;
	}

	public String getMd5sign() {
		return md5sign;
	}

	public void setMd5sign(String md5sign) {
		this.md5sign = md5sign;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getPaytotal() {
		return paytotal;
	}

	public void setPaytotal(String paytotal) {
		this.paytotal = paytotal;
	}

	public String getCsno() {
		return csno;
	}

	public void setCsno(String csno) {
		this.csno = csno;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public void setByPayPaymentService(BankPaymentService byPayPaymentService) {
		this.byPayPaymentService = byPayPaymentService;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getBizType() {
		return bizType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCodeInfo() {
		return codeInfo;
	}

	public void setCodeInfo(String codeInfo) {
		this.codeInfo = codeInfo;
	}
}
