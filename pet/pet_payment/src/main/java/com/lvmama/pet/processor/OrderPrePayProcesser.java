package com.lvmama.pet.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.po.pay.PaymentToBankInfo;
import com.lvmama.comm.pet.service.pay.BankPaymentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author zhangjie
 *
 */
public class OrderPrePayProcesser implements MessageProcesser {
	protected final Log LOG =LogFactory.getLog(this.getClass());

	/**
	 * 
	 */
	private BankPaymentService unionpayPreService;

	private PayPaymentService payPaymentService;

	/**
	 * 银联预授权扣款消息处理（补偿扣款机制）
	 * @param message
	 */
	public void process(Message message) {
		if (message.isOrderPrepayMsg()) {

			Long ordId = message.getObjectId();
			String bizType = message.getAddition();

			LOG.info("Order prePay init:"+ordId.toString());
			
			List<PayPayment> list = payPaymentService.selectNewPrePayData(ordId, bizType);
			for (PayPayment payment : list) {
				if (payment.isPrePayment()) {
					PayPrePayment prePayment = payment.getPayPrePayment();
					if (prePayment.isPrePaySuccess()) {
						LOG.info("prePayment, paymentId: "+prePayment.getPaymentId());
						if (payment.isUnionPayPre()&&StringUtil.isNotEmptyString(payment.getGatewayTradeNo())) {
							LOG.info("update payment status:"+payment.getPaymentId());
							//预授权扣款申请，需要先将已经预授权支付成功的支付记录设置成SUCCESS
							payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
							payPaymentService.updatePayment(payment);
						}
					}
				}
			}
			
			PaymentToBankInfo info=new PaymentToBankInfo();
			info.setObjectId(message.getObjectId());
			info.setBizType(message.getAddition());
			unionpayPreService.pay(info);
		}
	}
	
	
	public void setUnionpayPreService(BankPaymentService unionpayPreService) {
		this.unionpayPreService = unionpayPreService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
}
