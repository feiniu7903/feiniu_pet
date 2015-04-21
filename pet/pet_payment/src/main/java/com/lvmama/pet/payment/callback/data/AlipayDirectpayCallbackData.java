package com.lvmama.pet.payment.callback.data;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.util.CheckURL;
import com.alipay.util.SignatureHelper;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 支付宝快捷支付回调数据.
 * @author sunruyi
 */
public class AlipayDirectpayCallbackData implements CallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(AlipayDirectpayCallbackData.class);
	private String ALIPAY_DIRECTPAY_PARTNER = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_PARTNER");
	private String ALIPAY_DIRECTPAY_KEY = PaymentConstant.getInstance().getProperty("ALIPAY_DIRECTPAY_KEY");
	private String subject = "";
	private String callbackMethod;
	private String total_fee;
	private String notify_id;
	private String trade_no;
	private String out_trade_no;
	private String trade_status;
	private String sign;
	private Map<String, String> paraMap;
	
	public AlipayDirectpayCallbackData(Map<String, String> map,String callbackMethod){
		this.callbackMethod = callbackMethod;
		this.paraMap = map;
		LOG.info("AlipayDirectpayCallbackData.paraMap="+paraMap.toString());
		
		this.total_fee = this.paraMap.get("total_fee");
		this.subject = this.paraMap.get("subject");
		this.sign = this.paraMap.get("sign");
		this.notify_id = this.paraMap.get("notify_id");
		this.trade_no = this.paraMap.get("trade_no");
		this.out_trade_no = this.paraMap.get("out_trade_no");
		this.trade_status = this.paraMap.get("trade_status");
	}
	
	private boolean checkGateway() {
		String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
				+ "&partner=" + ALIPAY_DIRECTPAY_PARTNER
				+ "&notify_id=" + notify_id;
		//获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
		String responseTxt = CheckURL.check(alipayNotifyURL);
		if(responseTxt.equals("true")){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkSignature() {
		boolean result = false;
		if(null!=subject&&"get".equalsIgnoreCase(callbackMethod)) {        //同步设置subject字符集
		    try {
		    	subject = new String(subject.getBytes("ISO-8859-1"), "UTF-8");	//构造utf_subject
		    	paraMap.put("subject", subject);
			} catch (UnsupportedEncodingException e) {}
		}
		String mysign = SignatureHelper.sign(paraMap, ALIPAY_DIRECTPAY_KEY);
		if (mysign.equals(sign)){
			result = true;
		}
        return result;
	}

	@Override
	public boolean isSuccess() {
		boolean isCheckGateway = checkGateway();
		boolean isCheckSignature = checkSignature();
		boolean isNull = trade_status!=null;
		boolean iskey= trade_status.equalsIgnoreCase("TRADE_FINISHED") || trade_status.equalsIgnoreCase("TRADE_SUCCESS");
		boolean isOk =  isNull && iskey && isCheckSignature && isCheckGateway;
		return isOk;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getPaymentTradeNo() {
		return out_trade_no;
	}
	@Override
	public String getGatewayTradeNo() {
		return trade_no;
	}
	@Override
	public String getRefundSerial() {
		return trade_no;
	}
	@Override
	public String getCallbackInfo() {
		return null;
	}

	@Override
	public long getPaymentAmount() {
		return (long)(Float.parseFloat(total_fee)*100);
	}

	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.ALIPAY_DIRECTPAY.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}

}
