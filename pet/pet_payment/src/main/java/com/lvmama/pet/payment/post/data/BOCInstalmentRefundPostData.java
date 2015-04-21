package com.lvmama.pet.payment.post.data;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocnet.common.security.PKCS7Tool;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 中行分期支付退款请求数据对象.
 * @author sunruyi
 */
public class BOCInstalmentRefundPostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(BOCInstalmentRefundPostData.class);
	/**
	 * 原始数据对象.
	 */
	private BOCInstalmentRefundOrig orig;
	/**
	 * 签名后的字符串.
	 */
	private String sign;
	/**
	 * 构造函数.
	 * @param order 订单.
	 * @param ordPayment 支付记录.
	 * @param ordRefundment 退款单.
	 */
	public BOCInstalmentRefundPostData(RefundmentToBankInfo info){
		orig = new BOCInstalmentRefundOrig(info);
		sign = orig.signature();
	}
	/**
	 * 初始化退款的XML字符串.
	 * @return XML字符串.
	 */
	public String initRefundXML(){
		String xml = "";
		XStream stream = new XStream(new DomDriver());
		stream.alias("orderRefund", BOCInstalmentRefundPostData.class);
		stream.alias("orig",BOCInstalmentRefundOrig.class);
		//忽略掉注解的属性
		stream.autodetectAnnotations(true);
		xml = stream.toXML(this);
		xml = xml.replaceAll("&#xd;", "");
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
		LOG.info(xml);
		return xml;
	}
	public BOCInstalmentRefundOrig getOrig() {
		return orig;
	}
	public void setOrig(BOCInstalmentRefundOrig orig) {
		this.orig = orig;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
 class BOCInstalmentRefundOrig{
	/**
	 * LOG.
	 */
	@XStreamOmitField
	private static final Log LOG = LogFactory.getLog(BOCInstalmentRefundOrig.class);
	/**
	 * 证书库路径.
	 */
	@XStreamOmitField
	private final String keyStorePath = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_KEY_STORE_PATH");
	/**
	 * 证书库口令.
	 */
	@XStreamOmitField
	private final String keyStorePassword = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_KEY_STORE_PASSWORD");
	/**
	 * 签名私钥口令，一般与证书库口令相同.
	 */
	@XStreamOmitField
	private final String keyPassword = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_KEY_PASSWORD");
	/**
	 * 流水号.
	 */
	private String serialno;
	/**
	 * 商户号.
	 */
	private String masterid = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_MASTER_ID");
	/**
	 * 交易订单号.
	 */
	private String orderid;
	/**
	 * 订单日期.
	 */
	private String date;
	/**
	 * 订单发生时间.
	 */
	private String timestamp;
	/**
	 * 货币类型.
	 */
	private String currency = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_CURRENCY");
	/**
	 * 退款金额.
	 */
	private String amount;
	/**
	 * 原支付订单号.
	 */
	private String oldOrderId;
	/**
	 * 原订单日期.
	 */
	private String oldDate;
	/**
	 * 原订单发生时间.
	 */
	private String oldTimestamp;
	/**
	 * 终端号.
	 */
	private String terminalID = PaymentConstant.getInstance().getProperty("BOC_INSTALMENT_TERMINAL_ID");
	/**
	 * 授权码.
	 */
	private String authCode;
	/**
	 * 备用字段.
	 */
	private String remark;
	/**
	 * 构造函数.
	 * @param order 订单.
	 * @param payment 支付记录.
	 */
	
	public BOCInstalmentRefundOrig(RefundmentToBankInfo info){
		oldOrderId = info.getPaymentTradeNo();//支付时的订单ID
		authCode = info.getRefundSerial();//交易授权码
		oldDate = this.formatDate(info.getCallbackTime());
		oldTimestamp = this.formatTimestamp(info.getCallbackTime());
		orderid = this.initOrderId(oldOrderId);
		serialno = this.initSerialNo();
		date = this.formatDate(info.getCreateTime());
		timestamp = this.formatTimestamp(info.getCreateTime());
		amount = String.valueOf(info.getRefundAmount());
		remark = "";
	}
	/**
	 * 初始化orderId.
	 * @param oldOrderId 支付时的订单Id
	 * @return
	 */
	private String initOrderId(String oldOrderId){
		Long oldOrderIdLong = Long.valueOf(oldOrderId);
		Long orderId = oldOrderIdLong + 1L; 
		return String.valueOf(orderId);
	}
	/**
	 * 初始化serialno必须为18位.
	 * @return
	 */
	private String initSerialNo(){
		return DateUtil.getFormatDate(new Date(), "yyyyMMddkkmmssSSS") + RandomFactory.generate(1);
	}
	public String signature() {
		String signature = null;
		try {
			byte[] data = this.formatOrig().getBytes();
			PKCS7Tool tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword, keyPassword);
			signature = tool.sign(data);
			signature = signature.replaceAll("\n", "");
		} catch (GeneralSecurityException e) {
			LOG.info("BOC INSTALMENT REFUND SIGNATURE ERROR GeneralSecurityException:" + e);
		} catch (IOException e) {
			LOG.info("BOC INSTALMENT REFUND SIGNATURE ERROR IOException:" + e);
		} catch (Exception e) {
			LOG.info("BOC INSTALMENT REFUND SIGNATURE ERROR Exception:" + e);
		}
		return signature;
	}
	/**
	 * 格式化商户订单数据组成的原始数据字符串.
	 * @return 商户订单数据组成的原始数据字符串.
	 */
	private String formatOrig(){
		StringBuilder origStrBuild = new StringBuilder();
		origStrBuild.append(serialno).append("|")
				.append(masterid).append("|")
				.append(orderid).append("|")
				.append(date).append("|")
				.append(timestamp).append("|")
				.append(currency).append("|")
				.append(amount).append("|")
				.append(oldOrderId).append("|")
				.append(oldDate).append("|")
				.append(oldTimestamp).append("|")
				.append(terminalID).append("|")
				.append(authCode).append("|")
				.append(remark);
		LOG.info(origStrBuild.toString());
		return origStrBuild.toString();
	}
	/**
	 * 格式化订单日期.
	 * @param creatTime
	 * @return 订单日期.
	 */
	private String formatDate(Date creatTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(creatTime);
	}
	/**
	 * 格式化订单发生时间.
	 * @param creatTime
	 * @return 订单发生时间.
	 */
	private String formatTimestamp(Date creatTime){
		SimpleDateFormat sdf = new SimpleDateFormat("kkmmss");
		return sdf.format(creatTime);
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOldOrderId() {
		return oldOrderId;
	}

	public void setOldOrderId(String oldOrderId) {
		this.oldOrderId = oldOrderId;
	}

	public String getOldDate() {
		return oldDate;
	}

	public void setOldDate(String oldDate) {
		this.oldDate = oldDate;
	}

	public String getOldTimestamp() {
		return oldTimestamp;
	}

	public void setOldTimestamp(String oldTimestamp) {
		this.oldTimestamp = oldTimestamp;
	}

	public String getTerminalID() {
		return terminalID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
 }