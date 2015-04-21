package com.lvmama.pet.payment.callback.data;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nuxeo.common.xmap.XMap;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.AlipayUtil;
import com.lvmama.pet.vo.AlipayAPPNotifyData;

/**
 * 支付宝快捷支付回调数据.
 * @author ZHANG Nan
 */
public class AlipayAPPCallbackData implements CallbackData {
	
	protected Logger LOG = Logger.getLogger(this.getClass());
	/**
	 * 商户号
	 */
	private String partner = PaymentConstant.getInstance().getProperty("ALIPAY_APP_PARTNER");
	/**
	 * 商户收款支付宝用户号
	 */
	private String seller=PaymentConstant.getInstance().getProperty("ALIPAY_APP_SELLER");
	/**
	 * 商品名称
	 */
	private String subject = "驴妈妈旅游网订单付款";
	
	private String body="驴妈妈旅游网产品";
	/**
	 * 服务器异步通知页面路径
	 */
	private String notifyUrl=PaymentConstant.getInstance().getProperty("ALIPAY_APP_NOTIFY_URL");
	
	/**
	 * 手机支付宝APP公钥
	 */
	private String alipayAPPRasPublic=PaymentConstant.getInstance().getProperty("ALIPAY_APP_RAS_PUBLIC");
	/**
	 * paymentTradeNo 
	 */
	private String outTradeNo;
	/**
	 * gatewayTradeNo
	 */
	private String tradeNo;
	/**
	 * 回调签名
	 */
	private String sign;
	/**
	 * 支付金额
	 */
	private String totalFee;
	
	/**
	 * 回调状态
	 */
	private String tradeStatus;
	/**
	 * 回调数据
	 */
	private String notifyData;
	
	public AlipayAPPCallbackData(Map<String, String> map,String callbackMethod){
		try {
			this.notifyData=map.get("notify_data");
			LOG.info("notifyData="+notifyData);
			XMap xmap=new XMap();
			xmap.register(AlipayAPPNotifyData.class);
			AlipayAPPNotifyData alipayAPPNotifyData = (AlipayAPPNotifyData) xmap.load(new ByteArrayInputStream(notifyData.getBytes("utf-8")));
			this.tradeStatus=alipayAPPNotifyData.getTradeStatus();
			this.totalFee=alipayAPPNotifyData.getTotalFee();
			this.outTradeNo=alipayAPPNotifyData.getOutTradeNo();
			this.tradeNo=alipayAPPNotifyData.getTradeNo();
			this.sign = map.get("sign");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getPaymentTradeNo() {
		return outTradeNo;
	}

	@Override
	public String getGatewayTradeNo() {
		return tradeNo;
	}

	@Override
	public String getRefundSerial() {
		return tradeNo;
	}

	@Override
	public boolean checkSignature() {
		return AlipayUtil.signRSADoCheck("notify_data="+notifyData, sign, alipayAPPRasPublic);
	}
	
	@Override
	public boolean isSuccess() {
		LOG.info("tradeStatus="+tradeStatus);
		if(checkSignature() && ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus))){
			return true;
		}
		return false;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCallbackInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getPaymentAmount() {
		if(StringUtils.isNotBlank(totalFee)){
			return (long)(Float.parseFloat(totalFee)*100);	
		}
		return 0;
	}

	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.ALIPAY_APP.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}
	
	
	
	
	
	
	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getAlipayAPPRasPublic() {
		return alipayAPPRasPublic;
	}

	public void setAlipayAPPRasPublic(String alipayAPPRasPublic) {
		this.alipayAPPRasPublic = alipayAPPRasPublic;
	}

	public String getNotifyData() {
		return notifyData;
	}

	public void setNotifyData(String notifyData) {
		this.notifyData = notifyData;
	}
}
