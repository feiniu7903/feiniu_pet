package com.lvmama.pet.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pay.PaymentToBankInfo;
import com.lvmama.comm.pet.service.pay.BankPaymentService;

/**
 * 
 * @author liwenzhan
 *
 */
public class OrderApproveProcesser implements MessageProcesser {
	protected final Log LOG =LogFactory.getLog(this.getClass());

	/**
	 * 
	 */
	private BankPaymentService unionpayPreService;

	/**
	 * 业务订单已审核处理.
	 * @param message
	 */
	public void process(Message message) {
		if (message.isOrderApproveMsg() || message.isOrderApproveBeforePrepayMsg()) {
			PaymentToBankInfo info=new PaymentToBankInfo();
			info.setObjectId(message.getObjectId());
			info.setBizType(message.getAddition());
			unionpayPreService.pay(info);
		}
	}
	
	
	public void setUnionpayPreService(BankPaymentService unionpayPreService) {
		this.unionpayPreService = unionpayPreService;
	}
}
