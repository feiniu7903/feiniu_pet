package com.lvmama.pet.payment.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PaymentQueryService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class PaymentQueryServiceFactory{
	private Log LOG = LogFactory.getLog(this.getClass());
	
	private static final Map<String,PaymentQueryService> serviceMap = new HashMap<String,PaymentQueryService>();

	/**
	 * 支付宝
	 */
	private PaymentQueryService alipayPaymentQueryService;
	
	/**
	 * 银联
	 */
	private PaymentQueryService unionpayPaymentQueryService;

	/**
	 * 银联预授权
	 */
	private PaymentQueryService unionpayPrePaymentQueryService;
	/**
	 * 宁波银行
	 */
	private PaymentQueryService ningboBankPaymentQueryService;
	
	/**
	 * 财付通查询接口
	 */
	private PaymentQueryService tenpayQueryService;
	/**
	 * 财付通手机支付（包括app和wap）查询接口
	 */
	private PaymentQueryService tenpayPhoneQueryService;
	/**
	 * 百度支付查询
	 */
	private PaymentQueryService baiduQueryService;
	/**
	 * 手机银联支付
	 */
	private PaymentQueryService upompQueryService;
	/**
	 * @param info
	 * @return
	 */
	public PaymentQueryReturnInfo query(final PayPayment info){
		LOG.info("query invoked: "+StringUtil.printParam(info));		
		PaymentQueryService queryService = getQueryService(info.getPaymentGateway());
		if(queryService==null){
			PaymentQueryReturnInfo queryReturnInfo=new PaymentQueryReturnInfo();
			queryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.name());
			queryReturnInfo.setCodeInfo("未获取到有效的交易查询网关,不允许进行交易查询操作!");
			return queryReturnInfo;
		}
		LOG.info("got query service: " + queryService.getClass().getName());
		return  queryService.paymentStateQuery(info);
	}
	
	/**
	 * 根据支付网关，获取相应的查询服务.
	 * @param paymentGateway 支付网关.
	 * @return
	 */
	private PaymentQueryService getQueryService(String paymentGateway){
		if(serviceMap.isEmpty()){
			serviceMap.put(Constant.PAYMENT_GATEWAY.ALIPAY.name(), alipayPaymentQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ALIPAY_DIRECTPAY.name(), alipayPaymentQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name(), alipayPaymentQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.ALIPAY_APP.name(), alipayPaymentQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.UNIONPAY.name(), unionpayPaymentQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name(), unionpayPrePaymentQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.NING_BO_BANK.name(), ningboBankPaymentQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.TENPAY.name(), tenpayQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.TENPAY_APP.name(), tenpayPhoneQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.TENPAY_WAP.name(), tenpayPhoneQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BAIDUPAY_APP.name(), baiduQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BAIDUPAY_APP_ACTIVITIES.name(), baiduQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BAIDUPAY_WAP.name(), baiduQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.BAIDUPAY_WAP_ACTIVITIES.name(), baiduQueryService);
			serviceMap.put(Constant.PAYMENT_GATEWAY.UPOMP.name(), upompQueryService);
		}
		PaymentQueryService refundService = serviceMap.get(paymentGateway);
		return refundService;
	}
	
	public PaymentQueryService getUnionpayPaymentQueryService() {
		return unionpayPaymentQueryService;
	}
	public void setUnionpayPaymentQueryService(
			PaymentQueryService unionpayPaymentQueryService) {
		this.unionpayPaymentQueryService = unionpayPaymentQueryService;
	}
	public PaymentQueryService getAlipayPaymentQueryService() {
		return alipayPaymentQueryService;
	}
	public void setAlipayPaymentQueryService(
			PaymentQueryService alipayPaymentQueryService) {
		this.alipayPaymentQueryService = alipayPaymentQueryService;
	}

	public PaymentQueryService getUnionpayPrePaymentQueryService() {
		return unionpayPrePaymentQueryService;
	}

	public void setUnionpayPrePaymentQueryService(
			PaymentQueryService unionpayPrePaymentQueryService) {
		this.unionpayPrePaymentQueryService = unionpayPrePaymentQueryService;
	}

	public PaymentQueryService getNingboBankPaymentQueryService() {
		return ningboBankPaymentQueryService;
	}

	public void setNingboBankPaymentQueryService(
			PaymentQueryService ningboBankPaymentQueryService) {
		this.ningboBankPaymentQueryService = ningboBankPaymentQueryService;
	}
	
	public PaymentQueryService getTenpayQueryService() {
		return tenpayQueryService;
	}

	public void setTenpayQueryService(PaymentQueryService tenpayQueryService) {
		this.tenpayQueryService = tenpayQueryService;
	}

	public PaymentQueryService getBaiduQueryService() {
		return baiduQueryService;
	}

	public void setBaiduQueryService(PaymentQueryService baiduQueryService) {
		this.baiduQueryService = baiduQueryService;
	}

	public PaymentQueryService getTenpayPhoneQueryService() {
		return tenpayPhoneQueryService;
	}

	public void setTenpayPhoneQueryService(
			PaymentQueryService tenpayPhoneQueryService) {
		this.tenpayPhoneQueryService = tenpayPhoneQueryService;
	}

	public PaymentQueryService getUpompQueryService() {
		return upompQueryService;
	}

	public void setUpompQueryService(PaymentQueryService upompQueryService) {
		this.upompQueryService = upompQueryService;
	}

}