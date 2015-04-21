package com.lvmama.pet.payment.callback.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bocnet.common.security.PKCS7Tool;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 中国银行网上支付回调数据.
 * @author sunruyi
 * @see org.apache.commons.logging.Log
 * @see org.apache.commons.logging.LogFactory
 * @see com.bocnet.common.security.PKCS7Tool
 * @see com.lvmama.common.utils.PriceUtil
 * @see com.lvmama.common.vo.Constant
 * @see com.lvmama.pet.vo.PaymentConstant
 */
public class BOCCallbackData implements CallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(BOCCallbackData.class);
	
	
	private Map<String, String> paraMap;
	private String callbackMethod;
	
	
	/**
	 * 根证书路径.
	 */
	private final String rootCertificatePath = PaymentConstant.getInstance().getProperty("BOC_ROOT_CERTIFICATE_PATH");
	/**
	 * 商户号.
	 */
	private String merchantNo;
	/**
	 * 订单号.
	 */
	private String orderNo;
	/**
	 * 银行订单流水号.
	 */
	private String orderSeq;
	/**
	 * 银行卡类别.
	 */
	private String cardTyp;
	/**
	 * 支付时间.
	 */
	private String payTime;
	/**
	 * 订单状态.
	 */
	private String orderStatus;
	/**
	 * 支付金额.
	 */
	private String payAmount;
	/**
	 * 银行交易流水号.
	 */
	private String bankTranSeq;
	/**
	 * 网关签名数据.
	 */
	private String signData;
	/**
	 * 客户支付IP地址.
	 */
	private String orderIp;
	/**
	 * 客户浏览器Refer信息.
	 */
	private String orderRefer;
	/**
	 * 返回操作类型.
	 */
	private String returnActFlag;

	/**
	 * 构造函数.
	 * @param map 中行回调时的参数信息.
	 */
	public BOCCallbackData(final Map<String, String> map, String callbackMethod) {
		
		this.paraMap = map;
		this.callbackMethod = callbackMethod;
		
		
		this.merchantNo = map.get("merchantNo");
		this.orderNo = map.get("orderNo");
		this.orderSeq = map.get("orderSeq");
		this.cardTyp = map.get("cardTyp");
		this.payTime = map.get("payTime");
		this.orderStatus = map.get("orderStatus");
		this.payAmount = map.get("payAmount");
		this.orderIp = map.get("orderIp");
		this.orderRefer = map.get("orderRefer");
		this.bankTranSeq = map.get("bankTranSeq");
		this.returnActFlag = map.get("returnActFlag");
		this.signData = map.get("signData");
	}
	
	public Map<String, String> getParaMap() {
		return paraMap;
	}

	public void setParaMap(Map<String, String> paraMap) {
		this.paraMap = paraMap;
	}

	public String getCallbackMethod() {
		return callbackMethod;
	}


	public void setCallbackMethod(String callbackMethod) {
		this.callbackMethod = callbackMethod;
	}

	/**
	 * 校验签名.
	 */
	@Override
	public boolean checkSignature() {
		boolean flag = false;
		try {
			PKCS7Tool tool= PKCS7Tool.getVerifier(rootCertificatePath);
			StringBuilder signBuilder = new StringBuilder();
			signBuilder.append(merchantNo).append("|").append(orderNo)
					.append("|").append(orderSeq).append("|").append(cardTyp)
					.append("|").append(payTime).append("|")
					.append(orderStatus).append("|").append(payAmount);
			byte[] data = signBuilder.toString().getBytes();
			tool.verify(signData, data, null);
			LOG.info(signBuilder.toString());
			flag = true;
		} catch (GeneralSecurityException e) {
			LOG.error("BOC CALL BACK ERROR GeneralSecurityException: " + e);
		} catch (IOException e) {
			LOG.error("BOC CALL BACK ERROR IOException: " + e);
		}
		return flag;
	}
	/**
	 * 是否支付成功.
	 */
	@Override
	public boolean isSuccess() {
		return (orderStatus != null && orderStatus.equals("1") && this.checkSignature());
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getPaymentTradeNo() {
		return orderNo;
	}
	/**
	 * 获取银行产生的订单流水号.
	 */
	@Override
	public String getGatewayTradeNo() {
		return orderSeq;
	}
	@Override
	public String getRefundSerial() {
		return orderSeq;
	}
	@Override
	public String getCallbackInfo() {
		return null;
	}
	/**
	 * 获取支付金额.
	 */
	@Override
	public long getPaymentAmount() {
		return PriceUtil.convertToFen(new BigDecimal(payAmount));
	}
	/**
	 * 获取支付网关.
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.BOC.name();
	}

	@Override
	public Date getCallBackTime() {
		return new Date();
	}
}
