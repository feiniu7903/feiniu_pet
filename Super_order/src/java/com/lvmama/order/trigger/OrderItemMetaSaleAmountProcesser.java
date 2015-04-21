package com.lvmama.order.trigger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.order.service.OrderItemMetaSaleAmountServie;
import com.lvmama.order.service.OrderRefundmentService;

public class OrderItemMetaSaleAmountProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(OrderItemMetaSaleAmountProcesser.class);
	
	private OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie;
	private OrderRefundmentService orderRefundmentService;
	
	@Override
	public void process(Message message) {
		Long orderId = null;
		if (message.isOrderPaymentMsg()) {
			log.info("支付成功后，分拆收入消息处理：" + message);
			orderId = message.getObjectId();
			orderItemMetaSaleAmountServie.updateOrderItemMetaSaleAmount(orderId);
		}else if(message.isOrderPartpayPayment()){
			log.info("部分支付成功后，分拆收入消息处理：" + message);
			orderId = message.getObjectId();
			orderItemMetaSaleAmountServie.updateOrderItemSaleAmount(orderId);
		}
	}

	public OrderItemMetaSaleAmountServie getOrderItemMetaSaleAmountServie() {
		return orderItemMetaSaleAmountServie;
	}

	public void setOrderItemMetaSaleAmountServie(OrderItemMetaSaleAmountServie orderItemMetaSaleAmountServie) {
		this.orderItemMetaSaleAmountServie = orderItemMetaSaleAmountServie;
	}

	public OrderRefundmentService getOrderRefundmentService() {
		return orderRefundmentService;
	}

	public void setOrderRefundmentService(OrderRefundmentService orderRefundmentService) {
		this.orderRefundmentService = orderRefundmentService;
	}
}
