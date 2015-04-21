package com.lvmama.pet.payment.callback.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.callback.data.CallbackData;



/**
 * 支付回调的处理.
 * 
 * @author liwenzhan
 * 
 */
@Results({ @Result(name = "error", location = "/WEB-INF/pages/pay/error.ftl", type = "freemarker") })
public abstract class CallbackBaseAction extends BaseAction {
	/**
	 * 序列化 ID.
	 */
	private static final long serialVersionUID = 8040166269718939521L;

	protected Logger LOG = Logger.getLogger(this.getClass());
	/**
	 * 
	 */
	protected TopicMessageProducer resourceMessageProducer;
	/**
	 * 支付Payment的SERVICE.
	 */
	private PayPaymentService payPaymentService;
	/**
	 * 
	 */
	protected String errorMessage;
	/**
	 * 
	 */
	protected static String ORDER_SUCCESS = "order_success";
	/**
	 * 
	 */
	protected static String RECHARGE_SUCCESS = "recharge_success";
	/**
	 * 
	 */
	protected static String ASYNC = "asyn";
	/**
	 * 
	 */
	protected String asynchronousResult = "success";
	/**
	 * 
	 */
	protected CallbackData callbackData;
	/**
	 * 
	 */
	private Long objectId;
	
	private boolean cashaccountRecharge=false;

	/**
	 * 支付回调处理payment数据.
	 * 
	 * @param sync 同步
	 * @return
	 */
	String callback(boolean sync) {
		String key = null;
		try {
			LOG.info("SYNC: " + sync + " CALL BACK INVOKED: "+ this.getClass().getName());
			callbackData = getCallbackData();
			if (callbackData.getPaymentTradeNo() == null) {
				return ERROR;
			}
			LOG.info("callbackData.getPaymentTradeNo="+ callbackData.getPaymentTradeNo());

			PayPayment payment = new PayPayment();
			payment.setPaymentTradeNo(callbackData.getPaymentTradeNo());
			payment.setCallbackInfo(callbackData.getCallbackInfo());
			payment.setGatewayTradeNo(callbackData.getGatewayTradeNo());
			payment.setRefundSerial(callbackData.getRefundSerial());
			payment.setCallbackTime(callbackData.getCallBackTime());
			boolean success = callbackData.isSuccess();
			if(callbackData.getPaymentTradeNo().startsWith(Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.R.name())){
				cashaccountRecharge=true;
			}
			List<PayPayment> paymentDBList = payPaymentService.callBackPayPayment(payment, success);
			key = "PAYMENT_CALL_BACK_BASE_ACTION_"+ callbackData.getPaymentTradeNo();
			log.info("success="+success+",paymentDBList.size="+paymentDBList.size()+",key="+key);
			if (!SynchronizedLock.isOnDoingMemCached(key) && paymentDBList.size()>0 && success) {
				for (PayPayment paymentDB : paymentDBList) {
					log.info(StringUtil.printParam(paymentDB));
					if (paymentDB!=null && !paymentDB.isNotified() && Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equals(paymentDB.getStatus())) {
						resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentDB.getPaymentId()));
						cashaccountRecharge = paymentDB.isCashAccountRechargePayment();
					}	
				}
			}
			return sync?ORDER_SUCCESS:ASYNC;
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			SynchronizedLock.releaseMemCached(key);
		}
		return ERROR;
	}

	abstract CallbackData getCallbackData();

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getAsynchronousResult() {
		return asynchronousResult;
	}

	public void setAsynchronousResult(String asynchronousResult) {
		this.asynchronousResult = asynchronousResult;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	
	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}

	public boolean isCashaccountRecharge() {
		return cashaccountRecharge;
	}
}
