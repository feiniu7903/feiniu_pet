package com.lvmama.pet.refundment.data;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocnet.common.security.PKCS7Tool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.PaymentConstant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 中行分期退款返回结果类.
 * @author sunruyi
 * @see com.bocnet.common.security.PKCS7Tool
 * @see com.lvmama.pet.vo.PaymentConstant
 * @see com.thoughtworks.xstream.XStream
 * @see com.thoughtworks.xstream.io.xml.DomDriver
 */
public class BOCInstalmentRefundCallbackData implements RefundCallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(BOCInstalmentRefundCallbackData.class);
	/**
	 * 根证书路径.
	 */
	private static final String rootCertificatePath = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_ROOT_CERTIFICATE_PATH");
	
	private static final String succStatus = "0";
	private static final String otherSuccStatus = "2";
	
	/**
	 * 数据组成的原始数据字符串.
	 */
	private String sign;
	/**
	 * 原始数据字符串的签名.
	 */
	private BOCInstalmentRefundOrig orig;
	
	/**
	 * 构造函数.
	 * @param xmlStr
	 * @return
	 */
	public static BOCInstalmentRefundCallbackData initBOCInstalmentRefundResult(String xmlStr){
		XStream stream = new XStream(new DomDriver());
		stream.alias("result", BOCInstalmentRefundCallbackData.class);
		stream.alias("orig",BOCInstalmentRefundOrig.class);
		return (BOCInstalmentRefundCallbackData)stream.fromXML(xmlStr);
	}
	/**
	 * 测试方法.
	 * @param args
	 */
	public static void main(String[] args) {
	    String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result>    <orig>        <status>0</status>        <errdesc>成功</errdesc>        <serialno>201204129776330002</serialno>        <masterid>104110083980001</masterid>        <orderid>9776330002</orderid>    </orig>    <sign></sign></result>";
		BOCInstalmentRefundCallbackData result = BOCInstalmentRefundCallbackData.initBOCInstalmentRefundResult(xmlStr);
		LOG.info(result.checkSignature());
		
		
	}
	@Override
	public boolean checkSignature() {
		boolean flag = false;
		try {
			if(UtilityTool.isValid(sign)){
				PKCS7Tool tool= PKCS7Tool.getVerifier(rootCertificatePath);
				StringBuilder signBuilder = new StringBuilder();
				signBuilder.append(orig.getStatus()).append("|")
				//.append(orig.getErrdesc()).append("|")
				.append(orig.getSerialno()).append("|")
				.append(orig.getMasterid()).append("|")
				.append(orig.getOrderid());
				LOG.info(signBuilder.toString());
				byte[] data = signBuilder.toString().getBytes("ISO-8859-1");
				tool.verify(sign, data, null);
				flag = true;
			}
		} catch (GeneralSecurityException e) {
			LOG.error("BOC INSTALMENT REFUND CALL BACK ERROR GeneralSecurityException: " + e);
		} catch (IOException e) {
			LOG.error("BOC INSTALMENT REFUND CALL BACK ERROR IOException: " + e);
		}
		return flag;
	}
	@Override
	public boolean isSuccess() {
		/*
		 * 1为退款成功
		 * 2为已经完成退款
		 */
		return (orig.getStatus() != null && (orig.getStatus().equals(succStatus) || orig.getStatus().equals(otherSuccStatus)) && this.checkSignature());
	}
	@Override
	public String getCallbackInfo() {
		return orig.getErrdesc();
	}
	
	/**
	 * 返回码.
	 * @return
	 */
	public String getStatusCode(){
		return orig.getStatus();
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public BOCInstalmentRefundOrig getOrig() {
		return orig;
	}
	public void setOrig(BOCInstalmentRefundOrig orig) {
		this.orig = orig;
	}
	@Override
	public String getSerial() {
		// TODO Auto-generated method stub
		return null;
	}
}

class BOCInstalmentRefundOrig{
	/**
	 * 支付状态.
	 */
	private String status;
	/**
	 * 错误原因.
	 */
	private String errdesc;
	/**
	 * 商户流水号.
	 */
	private String serialno;
	/**
	 * 商户号.
	 */
	private String masterid;
	/**
	 * 交易订单号.
	 */
	private String orderid;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrdesc() {
		return errdesc;
	}

	public void setErrdesc(String errdesc) {
		this.errdesc = errdesc;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getMasterid() {
		return masterid;
	}

	public void setMasterid(String masterid) {
		this.masterid = masterid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
}