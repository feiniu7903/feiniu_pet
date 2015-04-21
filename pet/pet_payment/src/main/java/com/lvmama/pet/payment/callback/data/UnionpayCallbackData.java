package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.UnionUtil;

public class UnionpayCallbackData implements CallbackData {
	
	private String version;	//消息版本号 	M
	private String charset;//字符编码 M	
	private String signMethod;	//签名方法M	
	private String signature;	//签名信息M	
	private String transType;	//交易类型M	
	private String respCode;	//响应码	M+	
	private String respMsg;	//响应信息 M+	该域是对响应码的文字说明，商户可以显示在页面上方便持卡人分析交易失败的结果
	private String merAbbr;	//商户名称M	
	private String merId;	//商户代码M	
	private String orderNumber;	//商户订单号M	
	private String traceNumber;	//系统跟踪号M+	
	private String traceTime;;	//系统跟踪时间M+	
	private String qid;	//交易流水号M+	该域是银联互联网系统给予每笔交易的唯一标识
	private String orderAmount;	//交易金额M	
	private String orderCurrency;	//交易币种M	
	private String respTime;	//交易完成时间M+	
	private String settleAmount;	//清算金额M+	
	private String settleCurrency;	//清算币种M+	
	private String settleDate;	//清算日期M+	
	private String exchangeRate;	//清算汇率M+	
	private String exchangeDate;	//兑换日期M+	
	private String cupReserved;	//系统保留域O	部分已启用，见6.34
	private String key;// 商城密匙，需要和银联商户网站上配置的一样
	

	/**
	 * signature
	 */
	private String signature() {
		String returnXml = "charset="+charset+
			 "&cupReserved="+cupReserved+
			 "&exchangeDate="+exchangeDate+
			 "&exchangeRate="+exchangeRate+
			"&merAbbr="+merAbbr+
			"&merId="+merId+
			"&orderAmount="+orderAmount+
			"&orderCurrency="+orderCurrency+
			"&orderNumber="+orderNumber+
			"&qid="+qid+
			"&respCode="+respCode+
			"&respMsg="+respMsg+
			"&respTime="+respTime+
			"&settleAmount="+settleAmount+
			"&settleCurrency="+settleCurrency+
			"&settleDate="+settleDate+
			"&traceNumber="+traceNumber+
			"&traceTime="+traceTime+
			"&transType="+transType+
			"&version="+version+"&"+UnionUtil.md5(key, signMethod, charset);
		   return UnionUtil.md5(returnXml, signMethod, charset);
	}
	
	public UnionpayCallbackData(Map<String, String> pureParaPair) {
		this.setCharset(pureParaPair.get("charset"));
		this.setCupReserved(pureParaPair.get("cupReserved"));
		this.setExchangeDate(pureParaPair.get("exchangeDate"));
		this.setExchangeRate(pureParaPair.get("exchangeRate"));
		this.setMerAbbr(pureParaPair.get("merAbbr"));
		this.setMerId(pureParaPair.get("merId"));
		this.setOrderAmount(pureParaPair.get("orderAmount"));
		this.setOrderCurrency(pureParaPair.get("orderCurrency"));
		this.setOrderNumber(pureParaPair.get("orderNumber"));
		this.setQid(pureParaPair.get("qid"));
		this.setRespCode(pureParaPair.get("respCode"));
		this.setRespMsg(pureParaPair.get("respMsg"));
		this.setRespTime(pureParaPair.get("respTime"));
		this.setSettleAmount(pureParaPair.get("settleAmount"));
		this.setSettleCurrency(pureParaPair.get("settleCurrency"));
		this.setSettleDate(pureParaPair.get("settleDate"));
		this.setTraceNumber(pureParaPair.get("traceNumber"));
		this.setTraceTime(pureParaPair.get("traceTime"));
		this.setTransType(pureParaPair.get("transType"));
		this.setVersion(pureParaPair.get("version"));
		this.setSignature(pureParaPair.get("signature"));
		this.setSignMethod(pureParaPair.get("signMethod"));
		key = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_KEY");
		signature();
	}
	
	/**
	 * 返回获取流水号.
	 */
	@Override
	public String getPaymentTradeNo() {
		return orderNumber;
	}

	/**
	 * 获取返回银行退款流水号.
	 */
	@Override
	public String getRefundSerial() {
		return qid;
	}
	
	/**
	 * 判断返回签名是否正确.
	 * 
	 * @return true代表返回签名验证正确   false签名失败 
	 */
	@Override
	public boolean checkSignature() {
		return signature.equalsIgnoreCase(signature());
	}
	/**
	 * 是否支付成功.
	 * 
	 * @return 00代表支付成功， 其他代表支付不成功
	 */
	@Override
	public boolean isSuccess() {
		return respCode.equalsIgnoreCase("00");
	}
	/**
	 * 返回的银行错误信息描述.
	 * 
	 * @return errDis 
	 *     错误信息描述.
	 */
	@Override
	public String getMessage() {
		return respMsg;
	}
	/**
	 * 网关支付号.
	 * 
	 * @return commSerial 
	 *        网关支付号.
	 */
	@Override
	public String getGatewayTradeNo() {
		return qid;
	}
	/**
	 * 返回的银行备注.
	 * 
	 * @return  bankMoNo 
	 *        银行备注.
	 */
	@Override
	public String getCallbackInfo() {
		return respMsg;
	}
	/**
	 * 返回的支付金额.
	 * 
	 * @return  amount
	 *          支付金额，以分为单位.
	 */
	@Override
	public long getPaymentAmount() {
		return Long.valueOf(this.orderAmount);
	}
	/**
	 * 返回支付网关名称.
	 * 
	 * @return 支付网关名称
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name();
	}
	/**
	 * 银行交易时间(目前预授权在用).
	 * @return
	 */
	@Override
	public Date getCallBackTime() {
		if(this.respTime!=null && this.respTime.length()==14){
			return DateUtil.stringToDate(respTime, "yyyyMMddHHmmss");
		}
		return new Date();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getMerAbbr() {
		return merAbbr;
	}

	public void setMerAbbr(String merAbbr) {
		this.merAbbr = merAbbr;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getTraceNumber() {
		return traceNumber;
	}

	public void setTraceNumber(String traceNumber) {
		this.traceNumber = traceNumber;
	}

	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderCurrency() {
		return orderCurrency;
	}

	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}

	public String getRespTime() {
		return respTime;
	}

	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}

	public String getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(String settleAmount) {
		this.settleAmount = settleAmount;
	}

	public String getSettleCurrency() {
		return settleCurrency;
	}

	public void setSettleCurrency(String settleCurrency) {
		this.settleCurrency = settleCurrency;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getExchangeDate() {
		return exchangeDate;
	}

	public void setExchangeDate(String exchangeDate) {
		this.exchangeDate = exchangeDate;
	}

	public String getCupReserved() {
		return cupReserved;
	}

	public void setCupReserved(String cupReserved) {
		this.cupReserved = cupReserved;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}
