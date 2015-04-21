package com.lvmama.tnt.back.processer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.tnt.order.service.TntOrderService;
/**
 * lvmama 订单更新消息监控
 * @author gaoxin
 *
 */
public class OrderProcesser implements MessageProcesser{
	private final Log log=LogFactory.getLog(OrderProcesser.class);
	
//	@Autowired
//	private OrderService orderServiceProxy;
	
	@Autowired
	public TntOrderService tntOrderService;
	
	@Override
	public void process(Message message) {
		log.info("TntOrderProcesser message: " + message);
		if(message.isOrderMessage() && !message.isPaymentSuccessCallMessage() && !message.isOrderCreateMsg() || message.isOrderPerformMsg() || message.isOrderPartpayPayment()){
			Long orderId=message.getObjectId();
			tntOrderService.synOrder(orderId);
		}
		if(message.isTntOrderRefundSuccessMsg()){
			Long refundmentId=message.getObjectId();
			log.info("TntOrderProcesser TNT_ORDER_REFUND message: " + refundmentId);
			tntOrderService.synOrderRefund(refundmentId);
		}
	}
}
