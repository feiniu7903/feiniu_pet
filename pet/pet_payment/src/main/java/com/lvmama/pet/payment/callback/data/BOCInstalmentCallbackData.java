package com.lvmama.pet.payment.callback.data;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocnet.common.security.PKCS7Tool;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 中行分期回调数据.
 * @author sunruyi
 */
public class BOCInstalmentCallbackData implements CallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(BOCInstalmentCallbackData.class);
	/**
	 * 根证书路径.
	 */
	private final String rootCertificatePath = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_ROOT_CERTIFICATE_PATH");
	/**
	 * 数据组成的原始数据字符串.
	 */
	private String sign;
	/**
	 * 原始数据字符串的签名.
	 */
	private BocInstalmentOrig orig;
	/**
	 * ORD_PAYMENT表中的支付流水号.
	 */
	private String serial;
	/**
	 * 构造函数.
	 * @param map 中行回调时的参数信息.
	 */
	public static BOCInstalmentCallbackData initBOCInstalmentCallbackData(String xmlStr){
		String responseXml = xmlStr.replaceAll("&#xd;", "");
		LOG.info("BOC INSTALMENT CALLBACK XML = " + responseXml);
		XStream stream = new XStream(new DomDriver());
		stream.alias("result", BOCInstalmentCallbackData.class);
		stream.alias("orig",BocInstalmentOrig.class);
		BOCInstalmentCallbackData callbackData = (BOCInstalmentCallbackData)stream.fromXML(responseXml);
		return callbackData;
	}

	/**
	 * 校验签名.
	 */
	@Override
	public boolean checkSignature() {
		boolean flag = false;
		try {
			if(UtilityTool.isValid(sign)){
				LOG.info("BOC INSTALMENT CALLBACK rootCertificatePath=" + rootCertificatePath);
				PKCS7Tool tool= PKCS7Tool.getVerifier(rootCertificatePath);
				StringBuilder signBuilder = new StringBuilder();
				signBuilder.append(orig.getStatus()).append("|")
				//.append(orig.getErrdesc()).append("|")
				.append(orig.getSerialno()).append("|")
				.append(orig.getMasterid()).append("|")
				.append(orig.getOrderid()).append("|")
				.append(orig.getDate()).append("|")
				.append(orig.getAuthCode()).append("|")
				.append(orig.getInstalFirstAmt()).append("|")
				.append(orig.getInstalmentFee()).append("|")
				.append(orig.getEveryPayAmt()).append("|")
				.append(orig.getTranAmount()).append("|")
				.append(orig.getRetrReferenceNum());
				byte[] data = signBuilder.toString().getBytes("ISO-8859-1");
				tool.verify(sign, data, null);
				flag = true;
			}
		} catch (GeneralSecurityException e) {
			LOG.error("BOC INSTALMENT CALL BACK ERROR GeneralSecurityException: " + e);
		} catch (IOException e) {
			LOG.error("BOC INSTALMENT CALL BACK ERROR IOException: " + e);
		}
		return flag;
	}
	/**
	 * 是否支付成功.
	 */
	@Override
	public boolean isSuccess() {
		return (orig.getStatus() != null && orig.getStatus().equals("0") && this.checkSignature());
	}

	@Override
	public String getMessage() {
		return null;
	}
	/**
	 * 获取gateway_tradeNo.
	 * <pre>
	 * 中行分期会在支付时会将我们发起交易时传去的orderId原样返回，authCode授权码会在退款时用到，retrReferenceNum参考号 作为对账标识.
	 * <pre>
	 */
	@Override
	public String getGatewayTradeNo() {
		return orig.getRetrReferenceNum();
	}

	@Override
	public String getCallbackInfo() {
		return orig.getErrdesc();
	}
	
	/**
	 * 获取支付网关.
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.BOC_INSTALMENT.name();
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public BocInstalmentOrig getOrig() {
		return orig;
	}
	public void setOrig(BocInstalmentOrig orig) {
		this.orig = orig;
	}

	/**
	 * 设置支付交易流水号.
	 * @param serial 支付交易流水号.
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}
	@Override
	public String getPaymentTradeNo() {
		return orig.getOrderid();
	}
	@Override
	public String getRefundSerial() {
		return orig.getAuthCode();
	}
	@Override
	public Date getCallBackTime() {
		return new Date();
	}
	@Override
	public long getPaymentAmount() {
		return Long.parseLong(orig.getTranAmount());
	}
}
class BocInstalmentOrig{
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
	/**
	 * 订单日期.
	 */
	private String date;
	/**
	 * 授权码.
	 */
	private String authCode;
	/**
	 * 分期首付款.
	 */
	private String instalFirstAmt;
	/**
	 * 分期手续费.
	 */
	private String instalmentFee;
	/**
	 * 分期月还款额.
	 */
	private String everyPayAmt;
	/**
	 * 交易金额.
	 */
	private String tranAmount;
	/**
	 * 参考号，作为对账依据.
	 */
	private String retrReferenceNum;
	
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getInstalFirstAmt() {
		return instalFirstAmt;
	}
	public void setInstalFirstAmt(String instalFirstAmt) {
		this.instalFirstAmt = instalFirstAmt;
	}
	public String getInstalmentFee() {
		return instalmentFee;
	}
	public void setInstalmentFee(String instalmentFee) {
		this.instalmentFee = instalmentFee;
	}
	public String getEveryPayAmt() {
		return everyPayAmt;
	}
	public void setEveryPayAmt(String everyPayAmt) {
		this.everyPayAmt = everyPayAmt;
	}
	public String getTranAmount() {
		return tranAmount;
	}
	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}
	public String getRetrReferenceNum() {
		return retrReferenceNum;
	}
	public void setRetrReferenceNum(String retrReferenceNum) {
		this.retrReferenceNum = retrReferenceNum;
	}
}