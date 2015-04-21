package com.lvmama.pet.payment.phonepay;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;

public class ChinaPnrClient implements PhonepayClient{
	
	protected transient final Log log = LogFactory.getLog(getClass());
	/**
	 * 订单消息.
	 */
	private TopicMessageProducer resourceMessageProducer;
	/**
	 * 配置文件对象.
	 */
	private PaymentConstant pc = PaymentConstant.getInstance();
	
	
	private PayPaymentService payPaymentService;
	
    /**
     * 汇付天下支付处理.
     */
	public void sendData(CardAndOrderObject cardAndOrder,String paymentTradeNo) {
			Map<String, String> map = cardAndOrder.createDataMap(paymentTradeNo);
			String response = HttpsUtil.requestPostForm(pc.getProperty("CHINAPNR_URL"), map);
			log.info("chinapnr pay RESPONSE: "  + response);
			ChinaPNRObject chinaPNRObject = new ChinaPNRObject().createInstance(response);
			PayPayment payPayment = new PayPayment();
			
			payPayment.setCallbackTime(new Date());
			payPayment.setAmount(chinaPNRObject.getPayedAmount());
			payPayment.setPaymentTradeNo(paymentTradeNo);
			if(chinaPNRObject.isSuccess()) {
				payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			}else{
				payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
				payPayment.setCallbackInfo(chinaPNRObject.getErrorMsg());
			}
			List<PayPayment> paymentDBList = payPaymentService.callBackPayPayment(payPayment, chinaPNRObject.isSuccess());
			String key = "PAYMENT_CALL_BACK_BASE_ACTION_"+ paymentTradeNo;
			if (!SynchronizedLock.isOnDoingMemCached(key) && paymentDBList.size()>0 && chinaPNRObject.isSuccess()) {
				for (PayPayment paymentDB : paymentDBList) {
					if (paymentDB!=null && !paymentDB.isNotified() && Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equals(paymentDB.getStatus())) {
						resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentDB.getPaymentId()));
					}
				}	
			}
			SynchronizedLock.releaseMemCached(key);
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}
	
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

}
