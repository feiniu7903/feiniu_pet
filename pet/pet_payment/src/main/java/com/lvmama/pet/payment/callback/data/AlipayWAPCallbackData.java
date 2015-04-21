package com.lvmama.pet.payment.callback.data;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nuxeo.common.xmap.XMap;

import com.alipay.util.Md5Encrypt;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.vo.AlipayWAPNotifyData;

/**
 * 支付宝快捷支付回调数据.
 * @author ZHANG Nan
 */
public class AlipayWAPCallbackData implements CallbackData {
	private String sign;
	//同步回调状态
	private String result;
	//我方支付流水号(paymentTradeNo)
	private String outTradeNo;
	//支付宝支付流水号(gatewayTradeNo)
	private String tradeNo;
	//支付金额
	private String totalFee;
	//异步回调状态
	private String tradeStatus;
	private String version;
	private String secId; 
	private String service;
	private String notifyData;
	private Boolean sync;
	//私钥
	private String privateKey=PaymentConstant.getInstance().getProperty("ALIPAY_WAP_PRIVATE_KEY");
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	public AlipayWAPCallbackData(Map<String, String> map,String callbackMethod,Boolean sync){
		this.sync=sync;
		if(sync){
			this.result=map.get("result");
			this.outTradeNo=map.get("out_trade_no");
			this.tradeNo=map.get("trade_no");
		}
		else{
			try {
				this.notifyData=map.get("notify_data");
				XMap xmap=new XMap();
				xmap.register(AlipayWAPNotifyData.class);
				AlipayWAPNotifyData alipayWAPNotifyData = (AlipayWAPNotifyData) xmap.load(new ByteArrayInputStream(this.notifyData.getBytes("utf-8")));
				log.info(StringUtil.printParam(alipayWAPNotifyData));
				this.tradeStatus=alipayWAPNotifyData.getTradeStatus();
				this.outTradeNo=alipayWAPNotifyData.getOutTradeNo();
				this.tradeNo=alipayWAPNotifyData.getTradeNo();
				this.secId=map.get("sec_id");
				this.totalFee=alipayWAPNotifyData.getTotalFee();
				this.service=map.get("service");
				this.version=map.get("v");
				this.sign=map.get("sign");
			} catch (Exception e) {
				e.printStackTrace();
			}	
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
		String verifyData="service=" + service + "&v=" + version + "&sec_id=" + secId + "&notify_data="+ notifyData+privateKey;
		String verified=Md5Encrypt.md5(verifyData);
		if(StringUtils.isNotBlank(verified) && verified.equals(sign)){
			return true;
		}
		return false;
	}
	@Override
	public boolean isSuccess() {
		if(sync){
			return "success".equalsIgnoreCase(result);
		}
		else{
			return checkSignature() && ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus));	
		}
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
		return Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getNotifyData() {
		return notifyData;
	}

	public void setNotifyData(String notifyData) {
		this.notifyData = notifyData;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Boolean getSync() {
		return sync;
	}

	public void setSync(Boolean sync) {
		this.sync = sync;
	}
}
