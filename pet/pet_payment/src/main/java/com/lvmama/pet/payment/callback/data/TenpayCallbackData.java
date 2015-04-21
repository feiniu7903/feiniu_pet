package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

/**
 * 财付通回调信息
 * @author zhangjie
 *
 */
public class TenpayCallbackData implements CallbackData {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private Map<String, String> paraMap;

	private String key;

	private String sign;
	private String trade_state;		//交易状态  0—成功   其他保留
	private String total_fee;
	private String transaction_id;
	private String out_trade_no;
	
	
	public TenpayCallbackData(Map<String, String> map) {
		PaymentConstant pc = PaymentConstant.getInstance();
		this.key = pc.getProperty("TENPAY_KEY"); //私钥值。
		
		this.paraMap = map;
		log.info("Callback Data from tenpay :" + paraMap.toString());
		this.sign = this.paraMap.get("sign");
		this.trade_state = this.paraMap.get("trade_state");
		this.total_fee = this.paraMap.get("total_fee");
		this.transaction_id = this.paraMap.get("transaction_id");
		this.out_trade_no = this.paraMap.get("out_trade_no");
		
	}
	
	@Override
	public long getPaymentAmount() {
		return (long)(Float.parseFloat(total_fee));
	}

	@Override
	public boolean checkSignature() {
        return COMMUtil.getSignature(paraMap,key).equals(sign);
	}

	@Override
	public String getCallbackInfo() {
		return paraMap.get("pay_info");
	}

	@Override
	public String getPaymentTradeNo() {
		return out_trade_no;
	}
	@Override
	public String getGatewayTradeNo() {
		return transaction_id;
	}
	@Override
	public String getRefundSerial() {
		return transaction_id;
	}

	@Override
	public String getMessage() {
		return null;
	}



	@Override
	public boolean isSuccess() {
		if ("0".equals(trade_state) && checkSignature()) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.TENPAY.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}

}
