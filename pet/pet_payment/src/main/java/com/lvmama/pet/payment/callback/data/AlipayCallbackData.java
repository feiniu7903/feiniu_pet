package com.lvmama.pet.payment.callback.data;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alipay.util.CheckURL;
import com.alipay.util.SignatureHelper;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 支付宝回调信息
 * @author Alex Wang
 *
 */
public class AlipayCallbackData implements CallbackData {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private Map<String, String> paraMap;
	private String callbackMethod;

	private String partner;
	private String key;
	
	private String total_fee;
	private String subject;
	private String sign;
	private String notify_id;
	private String trade_no;
	private String out_trade_no;
	private String trade_status;

	public AlipayCallbackData(Map<String, String> map, String callbackMethod) {
		PaymentConstant pc = PaymentConstant.getInstance();
		//判断当前交易是否为充值
		if(isRecharge(map.get("out_trade_no"))){
			this.partner = pc.getProperty("ALIPAY_PARTNER_RECHARGE");
			this.key = pc.getProperty("ALIPAY_KEY_RECHARGE"); //私钥值。
		}
		else{
			this.partner = pc.getProperty("ALIPAY_PARTNER");
			this.key = pc.getProperty("ALIPAY_KEY"); //私钥值。	
		}
		
		this.paraMap = map;
		this.callbackMethod = callbackMethod;
		
		this.total_fee = this.paraMap.get("total_fee");
		this.subject = this.paraMap.get("subject");
		this.sign = this.paraMap.get("sign");
		this.notify_id = this.paraMap.get("notify_id");
		this.trade_no = this.paraMap.get("trade_no");
		this.out_trade_no = this.paraMap.get("out_trade_no");
		this.trade_status = this.paraMap.get("trade_status");
	}
	
	@Override
	public long getPaymentAmount() {
		return (long)(Float.parseFloat(total_fee)*100);
	}
	/**
	 * 判断当前交易是否为充值
	 * @author ZHANG Nan
	 * @param out_trade_no
	 * @return
	 */
	private Boolean isRecharge(String out_trade_no){
		if(StringUtils.isNotBlank(out_trade_no) && Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.R.name().equals(out_trade_no.substring(0,1))){
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
		String mysign = SignatureHelper.sign(paraMap, key);
		if (mysign.equals(sign)){
			result = true;
		}
		log.info("check from bank :" + result);
        return result;
	}

	private boolean checkGateway() {
		String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
				+ "&partner=" + partner
				+ "&notify_id=" + notify_id;
		//获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
		String responseTxt = CheckURL.check(alipayNotifyURL);
		log.info("check from gateway, response: " + responseTxt);
		if(responseTxt.equals("true")){
			return true;
		}
		return false;
	}
	
	@Override
	public String getCallbackInfo() {
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
	public String getMessage() {
		return null;
	}



	@Override
	public boolean isSuccess() {
		if (trade_status!=null && trade_status.equalsIgnoreCase("TRADE_SUCCESS") && checkSignature() && checkGateway()) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.ALIPAY.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}

}
