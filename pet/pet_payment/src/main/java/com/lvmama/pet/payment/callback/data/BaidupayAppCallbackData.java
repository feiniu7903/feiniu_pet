package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.COMMUtil;

/**
 * 百度app支付回调数据.
 * @author ZHANG Jie
 */
public class BaidupayAppCallbackData implements CallbackData {
	
	protected Logger LOG = Logger.getLogger(this.getClass());
	
	/**
	 * 百付宝商户号
	 */
	private String spNo;

	/**
	 * 商户订单号,不超过20个字符
	 */
	private String orderNo;
	
	/**
	 * 百度钱包交易号,不超过32个字符
	 */
	private String bfbOrderNo;
	
	/**
	 * 百度钱包交易创建时间,格式为YYYYMMDDHHMMSS
	 */
	private String bfbOrderCreateTime;	
	
	/**
	 * 支付时间,格式为YYYYMMDDHHMMSS
	 */
	private String payTime;	
	
	/**
	 * 支付类型
	 */
	private String payType;
	
	/**
	 * 商品单价，以分为单位
	 */
	private String unitAmount;
	
	/**
	 * 商品数量
	 */
	private String unitCount;
	
	/**
	 * 运费，以分为单位
	 */
	private String transportAmount;
	
	/**
	 * 总金额，以分为单位
	 */
	private String totalAmount;
	
	/**
	 * 百度钱包收取商户的手续费，以分为单位
	 */
	private String feeAmount;
	
	/**
	 * 币种，默认人民币
	 */
	private String currency;
	
	/**
	 * 买家在商户网站的用户名,允许包含中文；不超过64字符或32个汉字
	 */
	private String buyerSpUsername;
	
	/**
	 * 支付结果代码
	 */
	private String payResult;
	
	/**
	 * 请求参数的字符编码
	 */
	private String inputCharset;
	
	/**
	 * 接口的版本号
	 */
	private String version="2";
	
	/**
	 * 签名结果
	 */
	private String sign;
	
	/**
	 * 签名方法
	 */
	private String signMethod;
	
	/**
	 * 商户自定义数据
	 */
	private String extra;
	
	/**
	 * 私钥
	 */
	private String key;
	
	private Map<String, String> paraMap;
	
	public BaidupayAppCallbackData(Map<String, String> map){
		LOG.info("Callback Data from baidupayapp :" + map.toString());
		this.paraMap = map;
		this.spNo=this.paraMap.get("sp_no");
		this.orderNo=this.paraMap.get("order_no");
		this.bfbOrderNo=this.paraMap.get("bfb_order_no");
		this.bfbOrderCreateTime=this.paraMap.get("bfb_order_create_time");
		this.payTime=this.paraMap.get("pay_time");
		this.payType=this.paraMap.get("pay_type");
		this.unitAmount=this.paraMap.get("unit_amount");
		this.unitCount=this.paraMap.get("unit_count");
		this.transportAmount=this.paraMap.get("transport_amount");
		this.totalAmount=this.paraMap.get("total_amount");
		this.feeAmount=this.paraMap.get("fee_amount");
		this.currency=this.paraMap.get("currency");
		this.buyerSpUsername=this.paraMap.get("buyer_sp_username");
		this.payResult=this.paraMap.get("pay_result");
		this.inputCharset=this.paraMap.get("input_charset");
		this.signMethod=this.paraMap.get("sign_method");
		this.extra=this.paraMap.get("extra");
		this.sign=this.paraMap.get("sign");
		if(PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_ACTIVITIES_SP_NO").equalsIgnoreCase(this.spNo)){
			this.key=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_ACTIVITIES_KEY");
		}else{
			this.key=PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_KEY");
		}
	}

	@Override
	public String getPaymentTradeNo() {
		return orderNo;
	}

	@Override
	public String getGatewayTradeNo() {
		return bfbOrderNo;
	}

	@Override
	public String getRefundSerial() {
		return bfbOrderNo;
	}

	@Override
	public boolean checkSignature() {
		return COMMUtil.getSignatureTwo(paraMap,key).equalsIgnoreCase(sign);
	}
	
	@Override
	public boolean isSuccess() {
		if ("1".equals(payResult) && checkSignature()) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getCallbackInfo() {
		return "";
	}

	@Override
	public long getPaymentAmount() {
		if(StringUtils.isNotBlank(totalAmount)){
			return (long)(Float.parseFloat(totalAmount)*100);	
		}
		return 0;
	}

	@Override
	public String getPaymentGateway() {
		if(PaymentConstant.getInstance().getProperty("BAIDUAPPPAY_ACTIVITIES_SP_NO").equalsIgnoreCase(this.spNo)){
			return Constant.PAYMENT_GATEWAY.BAIDUPAY_APP_ACTIVITIES.name();
		}else{
			return Constant.PAYMENT_GATEWAY.BAIDUPAY_APP.name();
		}
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}

	public Logger getLOG() {
		return LOG;
	}

	public void setLOG(Logger lOG) {
		LOG = lOG;
	}

	public String getSpNo() {
		return spNo;
	}

	public void setSpNo(String spNo) {
		this.spNo = spNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBfbOrderNo() {
		return bfbOrderNo;
	}

	public void setBfbOrderNo(String bfbOrderNo) {
		this.bfbOrderNo = bfbOrderNo;
	}

	public String getBfbOrderCreateTime() {
		return bfbOrderCreateTime;
	}

	public void setBfbOrderCreateTime(String bfbOrderCreateTime) {
		this.bfbOrderCreateTime = bfbOrderCreateTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(String unitAmount) {
		this.unitAmount = unitAmount;
	}

	public String getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(String unitCount) {
		this.unitCount = unitCount;
	}

	public String getTransportAmount() {
		return transportAmount;
	}

	public void setTransportAmount(String transportAmount) {
		this.transportAmount = transportAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBuyerSpUsername() {
		return buyerSpUsername;
	}

	public void setBuyerSpUsername(String buyerSpUsername) {
		this.buyerSpUsername = buyerSpUsername;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Map<String, String> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
