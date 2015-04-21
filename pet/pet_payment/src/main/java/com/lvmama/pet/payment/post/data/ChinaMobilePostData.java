package com.lvmama.pet.payment.post.data;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.ChinaMobileUtil;

public class ChinaMobilePostData implements PostData {
	
	private static final Log LOG = LogFactory.getLog(ChinaMobilePostData.class);
	
	//字符集 00=GBK,01=GB2312,02=UTF-8
	private String characterSet="02";
	//同步返回URL
	private String callbackUrl=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_CALLBACK_URL");
	//异步返回URL
	private String notifyUrl=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_NOTIFY_URL");
	//用户IP地址
	private String ipAddress="127.0.0.1";
	//商户ID
	private String merchantId=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_MERCHANT_ID");
	//商户密钥
	private String merchantSignatureKey=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_SIGN_KEY");
	//商户请求号
	private String requestId;
	//签名方式 只能是MD5或RSA
	private String signType="MD5";
	//接口类型
	private String type="DirectPayConfirm";
	//版本号
	private String version="2.0.0";
	//签名数据
	private String hmac;
	//订单金额 	以分为单位，如1元表示为100
	private String amount="0";
	//银行代码
	private String bankAbbr="";
	//00- CNY—现金支付方式
	private String currency="00";
	//订单提交日期 商户发起请求的时间  年年年年月月日日
	private String orderDate;
	//商户订单号  商户系统订单号(对应支付的paymentTradeNo)
	private String chinaMobileOrderId="";
	//商户会计日期 商户发起请求的会计日期 年年年年月月日日
	private String merAcDate;
	//有效期数量   数字，与订单有效期单位同时构成订单有效期
	private String period="40";
	//有效期单位 只能取以下枚举值 00=分,01=小时,02=日,03=月
	private String periodUnit="00";
	//商户展示名称
	private String merchantAbbr="上海景域文化传播有限公司";
	//商品名称  所购买商品的名称
	private String productName="驴妈妈旅游网订单付款";
	//营销工具使用控制 00使用全部营销工具(默讣) 10不支持使用电子券,20不支持代金券,30不支持积分,40不支持所有营销工具
	private String couponsFlag="00";
	//商品描述
	private String productDesc="";
	//商品编号
	private String productId="";
	//商品数量
	private String productNum="";
	//保留字段1
	private String reserved1="";
	//保留字段2
	private String reserved2="";
	//用户标识
	private String userToken="";
	//商品展示地址
	private String showUrl="";
	
	public ChinaMobilePostData(PayPayment payment,String bankid) {
		this.requestId=payment.getPaymentTradeNo();
		this.chinaMobileOrderId=payment.getPaymentTradeNo();
		this.orderDate=DateUtil.formatDate(new Date(), "yyyyMMdd");
		this.merAcDate=DateUtil.formatDate(new Date(), "yyyyMMdd");
		this.amount=payment.getAmount()+"";
		this.bankAbbr=bankid;
		this.hmac=signature();
		LOG.info("ChinaMobilePostData init finished. payment="+StringUtil.printParam(payment));
	}
	
	@Override
	public String signature() {
		String signData = characterSet + callbackUrl + notifyUrl + ipAddress + merchantId + requestId + signType + type + version + amount + bankAbbr + currency
				+ orderDate + chinaMobileOrderId + merAcDate + period + periodUnit + merchantAbbr + productDesc + productId + productName + productNum + reserved1
				+ reserved2 + userToken + showUrl + couponsFlag;
		//数据签名
		return new ChinaMobileUtil().MD5Sign(signData, merchantSignatureKey);
	}

	/**
	 * 获取支付对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		return getChinaMobileOrderId();
	}
	
	public String getCharacterSet() {
		return characterSet;
	}

	public void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantSignatureKey() {
		return merchantSignatureKey;
	}

	public void setMerchantSignatureKey(String merchantSignatureKey) {
		this.merchantSignatureKey = merchantSignatureKey;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBankAbbr() {
		return bankAbbr;
	}

	public void setBankAbbr(String bankAbbr) {
		this.bankAbbr = bankAbbr;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getMerAcDate() {
		return merAcDate;
	}

	public void setMerAcDate(String merAcDate) {
		this.merAcDate = merAcDate;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}

	public String getMerchantAbbr() {
		return merchantAbbr;
	}

	public void setMerchantAbbr(String merchantAbbr) {
		this.merchantAbbr = merchantAbbr;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCouponsFlag() {
		return couponsFlag;
	}

	public void setCouponsFlag(String couponsFlag) {
		this.couponsFlag = couponsFlag;
	}	

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public String getChinaMobileOrderId() {
		return chinaMobileOrderId;
	}

	public void setChinaMobileOrderId(String chinaMobileOrderId) {
		this.chinaMobileOrderId = chinaMobileOrderId;
	}
}
