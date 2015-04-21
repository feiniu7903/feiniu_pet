package com.lvmama.comm.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.PaymentConstant;

public class PaymentUrl {
	
	protected Log LOG = LogFactory.getLog(this.getClass());
	
	private Long objectId;
	private String objectType;
	private String bizType;
	private Long amount;
	private String paymentType;
	private String signature;
	private String userId;
	
	/**
	 * 可使用奖金(分)
	 */
	private Long bonus;
	
	/**
	 * 
	 * @param objectId 业务对象ID(ID)
	 * @param objectType 业务对象类型(ORD_ORDER,FINC_CACH_ACCOUNT)
	 * @param bizType 业务类型 (SUPER_ORDER,BEE_ORDER,ANT_ORDER,CASHACCOUNT_RECHARGE,TRANSHOTEL_ORDER)
	 * @param amount 支付金额 (分为单位)
	 * @param paymentType 支付类型(PAY/PRE_PAY)
	 */
	public PaymentUrl(Long objectId, String objectType, String bizType, Long amount, String paymentType) {
		this.objectId = objectId;
		this.objectType = objectType;
		this.bizType = bizType;
		this.amount = amount;
		this.paymentType = paymentType;
	}
	
	/**
	 * 
	 * @param objectId 业务对象ID(ID)
	 * @param objectType 业务对象类型(ORD_ORDER,FINC_CACH_ACCOUNT)
	 * @param bizType 业务类型 (SUPER_ORDER,BEE_ORDER,ANT_ORDER,CASHACCOUNT_RECHARGE,TRANSHOTEL_ORDER)
	 * @param amount 支付金额 (分为单位) bonus(可用现金账户金额)
	 * @param paymentType 支付类型(PAY/PRE_PAY)
	 */
	public PaymentUrl(Long objectId, String objectType, String bizType, Long amount, String paymentType, Long bonus,String userId) {
		this.objectId = objectId;
		this.objectType = objectType;
		this.bizType = bizType;
		this.amount = amount;
		this.paymentType = paymentType;
		this.bonus = bonus;
		this.userId=userId;
	}
	/**
	 * 
	 * @param gatewayUrlPrefix URL的前缀如，http://pay.lvmama.com/payment/pay/alipay.do
	 * @return
	 */
	public String getPaymentUrl(String gatewayUrlPrefix) {
		String dataStr = String.valueOf(objectId)+objectType+String.valueOf(amount)+paymentType+bizType+PaymentConstant.SIG_PRIVATE_KEY;
		LOG.info("source: " + dataStr);
		signature = MD5.md5(dataStr);
		LOG.info("md5: " + signature);
		String params = "objectId=" + objectId + "&objectType=" + objectType + "&amount=" + amount + "&paymentType=" + paymentType + "&bizType=" + bizType + "&signature=" + signature;
		return gatewayUrlPrefix  + "?" + params;
	}
	/**
	 * 
	 * @param gatewayUrlPrefix URL的前缀如，http://pay.lvmama.com/payment/pay/alipay.do
	 * @return
	 */
	public String getPaymentUrlWithBonus(String gatewayUrlPrefix) {
		String dataStr = String.valueOf(objectId)+objectType+String.valueOf(amount)+paymentType+bizType+bonus+userId+PaymentConstant.SIG_PRIVATE_KEY;
		LOG.info("source: " + dataStr);
		signature = MD5.md5(dataStr);
		LOG.info("md5: " + signature);
		String params = "objectId=" + objectId + "&objectType=" + objectType + "&amount=" + amount + "&paymentType=" + paymentType + "&bizType=" + bizType + "&signature=" + signature;
		return gatewayUrlPrefix  + "?" + params;
	}
	
}
