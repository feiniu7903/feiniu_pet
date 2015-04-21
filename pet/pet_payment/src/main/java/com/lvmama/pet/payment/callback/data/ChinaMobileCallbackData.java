package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.ChinaMobileUtil;

/**
 * 支付宝回调信息
 * @author Alex Wang
 *
 */
public class ChinaMobileCallbackData implements CallbackData {
	
	private Logger log = Logger.getLogger(this.getClass());
	//商户编号
	private String merchantId;
	//手机支付平台返回的交易流水号
	private String payNo;
	//返回码 提示各类相关信息
	private String returnCode;
	//返回码描述信息 返回码信息提示
	private String message;
	//签名方式 只能是MD5或者RSA
	private String signType;
	//接口类型
	private String type;
	//版本号
	private String version;
	//支付金额 实际支付的金额，以分为单位
	private String amount;
	//金额明细
	/**
	支付金额明细.内容如下:
	CNY_AMT=xx#
	CMY_AMT=xx#
	RED_AMT=xx# VCH_AMT=xx# POT_CHG_AMT=xx
	其中,xx表示金额数字,分为单位,#表示分割符。
	CNY_AMT表示现金金额
	CMY_AMT表示充值卡金额
	RED_AMT表示电子券金额
	VCH_AMT表示代金券金额
	POT_CHG_AMT表示积分金额
	 */
	private String amtItem;
	//支付银行
	private String bankAbbr;
	//支付手机号  手机前三位+后四位,网关支付时为空
	private String mobile;
	//商户订单号
	private String chinaMobileOrderId;
	//支付时间 用户完成支付的时间 YYYYMMDDHHmmSS
	private String payDate;
	//会计日期 手机支付系统会计日期 年年年年月月日日
	private String accountDate;
	//保留字段1 交易返回时原样返回给商家网站，给商户备用
	private String reserved1;
	//保留字段2 交易返回时原样返回给商家网站，给商户备用
	private String reserved2;
	/**
	 * 支付结果
	 * 支付结果状态
		成功：SUCCESS
		失败：FAILED
	 */
	private String status;
	//订单提交日期 商户发起请求的日期 年年年年月月日日
	private String orderDate;
	//费用 单笔订单收取商户的交易手续费，单位为元
	private String fee;
	//签名数据
	private String hmac;
	
	//商户密钥
	private String merchantSignatureKey=PaymentConstant.getInstance().getProperty("CHINA_MOBILE_SIGN_KEY");

	public ChinaMobileCallbackData(Map<String, String> map, String callbackMethod) {
		this.merchantId=map.get("merchantId");
		this.payNo=map.get("payNo");
		this.returnCode=map.get("returnCode");
		this.message=map.get("message");
		this.signType=map.get("signType");
		this.type=map.get("type");
		this.version=map.get("version");
		this.amount=map.get("amount");
		this.amtItem=map.get("amtItem");
		this.bankAbbr=map.get("bankAbbr");
		this.mobile=map.get("mobile");
		this.chinaMobileOrderId=map.get("orderId");
		this.payDate=map.get("payDate");
		this.accountDate=map.get("accountDate");
		this.reserved1=map.get("reserved1");
		this.reserved2=map.get("reserved2");
		this.status=map.get("status");
		this.orderDate=map.get("orderDate");
		this.fee=map.get("fee");
		this.hmac=map.get("hmac");
		log.info("ChinaMobileCallbackData:"+map.toString());
	}
	
	@Override
	public long getPaymentAmount() {
		return Long.parseLong(getAmount());
	}

	@Override
	public boolean checkSignature() {
		String signData = merchantId + payNo + returnCode + message + signType + type + version + amount + amtItem + bankAbbr + mobile + chinaMobileOrderId
				+ payDate + accountDate + reserved1 + reserved2 + status + orderDate + fee;
		log.info("chinamobile payment callback signData="+signData);
		//数据签名
		String signature=new ChinaMobileUtil().MD5Sign(signData, merchantSignatureKey);
		return StringUtils.isNotBlank(signature) && signature.equals(getHmac());
	}
	@Override
	public String getCallbackInfo() {
		return getMessage();
	}

	@Override
	public String getPaymentTradeNo() {
		return getChinaMobileOrderId();
	}
	@Override
	public String getGatewayTradeNo() {
		return getPayNo();
	}
	@Override
	public String getRefundSerial() {
		return getPayNo();
	}

	@Override
	public String getMessage() {
		return "returnCode="+returnCode+",message="+message;
	}
	@Override
	public boolean isSuccess() {
		if (StringUtils.isNotBlank(status) && status.equalsIgnoreCase("SUCCESS") && checkSignature()) {
			return true;
		}else{
			return false;
		}
	}
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.CHINA_MOBILE_PAY.name();
	}

	@Override
	public Date getCallBackTime() {
		if(StringUtils.isNotBlank(getPayDate())){
			return DateUtil.toDate(getPayDate(), "yyyyMMddHHmmss"); 
		}
		return new Date();
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmtItem() {
		return amtItem;
	}

	public void setAmtItem(String amtItem) {
		this.amtItem = amtItem;
	}

	public String getBankAbbr() {
		return bankAbbr;
	}

	public void setBankAbbr(String bankAbbr) {
		this.bankAbbr = bankAbbr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getChinaMobileOrderId() {
		return chinaMobileOrderId;
	}

	public void setChinaMobileOrderId(String chinaMobileOrderId) {
		this.chinaMobileOrderId = chinaMobileOrderId;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
