package com.lvmama.pet.payment.post.data;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.UnionUtil;

public class UnionpayPostData implements PostData {
	
	private String version; // 消息版本号 M
	private String charset; // 字符编码M 全大写
	// 签名
	private String signature;
	private String signMethod;//加密方式
	private String transType; // 交易类型M
	private String merAbbr; // 商户名称M
	private String merId; // 商户代码M
	private String merCode=""; // 商户类型 C 当收单机构代码出现时，则该域必须出现
	private String backEndUrl; // 通知URL M
	private String frontEndUrl; // 返回URL M
	private String acqCode=""; // 收单机构代码,当商户直接与银联互联网系统相连时，该域可不出现当商户通过其他系统间接与银联互联网系统相连时，该域必须出现
	private String orderTime; // 交易开始日期时间M
	private String orderNumber; // 商户订单号 M 最大为32个
	private String commodityName; // 商品名称 O 商户可自行选择是否提交订单中包含的商品信息
	private String commodityUrl=""; //商品URL O 
	private String commodityUnitPrice=""; //商品单价 O 
	private String commodityQuantity="";//商品数量 O 
	private String transferFee="";//运输费用 O 
	private String commodityDiscount="";//优惠信息 O 	
	private String orderAmount; // 交易金额 M
	private String orderCurrency; // 交易币种 M 156代表人民币
	private String customerName=""; // 持卡人姓名 O 建议商户提交。出现在支付信息采集页面中
	private String defaultPayType=""; // 默认支付方式 O
	private String defaultBankNumber=""; // 默认银行编码 O
	private String transTimeout; // 交易超时时间 O // 建议商户提交，以防止钓鱼网站的问题
	private String customerIp; // 持卡人IP M
	private String origQid=""; // 原始交易流水号 C 当交易类型是撤销或者退货时，该域必须出现。其他交易该域不可出现
	private String merReserved = "{isPreAuth=true}"; // 商户保留域
	private String key;// 商城密匙，需要和银联商户网站上配置的一样
	
	/**
	 * 
	 */
	private UnionpayPostData() {
		PaymentConstant pc = PaymentConstant.getInstance();
		version = pc.getProperty("CHINAPAY_PRE_VERSION");
		charset = pc.getProperty("CHINAPAY_PRE_CHARSET");
		transType = pc.getProperty("UNIONPAY_TRANSTYPE_PAY");
		merId = pc.getProperty("CHINAPAY_PRE_MERID");
		orderCurrency = pc.getProperty("CHINAPAY_PRE_CURRENCY");
		key = pc.getProperty("CHINAPAY_PRE_KEY");
		backEndUrl = pc.getProperty("UNIONPAY_BACKENDURL");
		frontEndUrl = pc.getProperty("UNIONPAY_FRONTENDURL");
		signMethod =   pc.getProperty("CHINAPAY_PRE_SIGNTYPE");
		merAbbr =  "上海驴妈妈旅游网";
		transTimeout = "300000000";
	}
	/**
	 * 包装支付的数据.
	 * 
	 * @param ordOrder
	 * @param ordPayment
	 */
	public UnionpayPostData(PayPayment payPayment,String userIp,String defaultPayType) {
		this();
		this.defaultPayType=defaultPayType;
		this.setOrderTime(DateUtil.formatDate(payPayment.getCreateTime(), "yyyyMMddHHmmss"));
		this.setOrderNumber(payPayment.getPaymentTradeNo());
		this.setCommodityName("上海驴妈妈旅游网订购的产品");
		this.setOrderAmount(payPayment.getAmount().toString());
        this.setCustomerIp(userIp);
	}
	public String getMerSignMsg() {
		String htmlString = "acqCode="+acqCode
		+"&backEndUrl="+backEndUrl
		+"&charset="+charset
		+"&commodityDiscount="+commodityDiscount
		+"&commodityName="+commodityName
		+"&commodityQuantity="+commodityQuantity
		+"&commodityUnitPrice="+commodityUnitPrice
		+"&commodityUrl="+commodityUrl
		+"&customerIp="+customerIp
		+"&customerName="+customerName
		+"&defaultBankNumber="+defaultBankNumber
		+"&defaultPayType="+defaultPayType
		+"&frontEndUrl="+frontEndUrl
		+"&merAbbr="+merAbbr
		+"&merCode="+merCode
		+"&merId="+merId
		+"&merReserved="+merReserved
		+"&orderAmount="+orderAmount
		+"&orderCurrency="+orderCurrency
		+"&orderNumber="+orderNumber
		+"&orderTime="+orderTime
		+"&origQid="+origQid
		+"&transTimeout="+transTimeout
		+"&transType="+transType
		+"&transferFee="+transferFee
		+"&version="+version+"&"+UnionUtil.md5(key, signMethod, charset);
		return UnionUtil.md5(htmlString, signMethod, charset);
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}

	public String getBackEndUrl() {
		return backEndUrl;
	}

	public void setBackEndUrl(String backEndUrl) {
		this.backEndUrl = backEndUrl;
	}

	public String getFrontEndUrl() {
		return frontEndUrl;
	}

	public void setFrontEndUrl(String frontEndUrl) {
		this.frontEndUrl = frontEndUrl;
	}

	public String getAcqCode() {
		return acqCode;
	}

	public void setAcqCode(String acqCode) {
		this.acqCode = acqCode;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityUrl() {
		return commodityUrl;
	}

	public void setCommodityUrl(String commodityUrl) {
		this.commodityUrl = commodityUrl;
	}

	public String getCommodityUnitPrice() {
		return commodityUnitPrice;
	}

	public void setCommodityUnitPrice(String commodityUnitPrice) {
		this.commodityUnitPrice = commodityUnitPrice;
	}

	public String getCommodityQuantity() {
		return commodityQuantity;
	}

	public void setCommodityQuantity(String commodityQuantity) {
		this.commodityQuantity = commodityQuantity;
	}

	public String getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(String transferFee) {
		this.transferFee = transferFee;
	}

	public String getCommodityDiscount() {
		return commodityDiscount;
	}

	public void setCommodityDiscount(String commodityDiscount) {
		this.commodityDiscount = commodityDiscount;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDefaultPayType() {
		return defaultPayType;
	}

	public void setDefaultPayType(String defaultPayType) {
		this.defaultPayType = defaultPayType;
	}

	public String getDefaultBankNumber() {
		return defaultBankNumber;
	}

	public void setDefaultBankNumber(String defaultBankNumber) {
		this.defaultBankNumber = defaultBankNumber;
	}

	public String getTransTimeout() {
		return transTimeout;
	}

	public void setTransTimeout(String transTimeout) {
		this.transTimeout = transTimeout;
	}

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public String getOrigQid() {
		return origQid;
	}

	public void setOrigQid(String origQid) {
		this.origQid = origQid;
	}

	public String getMerReserved() {
		return merReserved;
	}

	public void setMerReserved(String merReserved) {
		this.merReserved = merReserved;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String signature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPaymentTradeNo() {
		return this.orderNumber;
	}
}
