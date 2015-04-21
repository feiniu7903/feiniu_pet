package com.lvmama.back.message;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.vo.Constant;

public class OrderPaymentCheckProcessor implements MessageProcesser  {
	
	private ComMessageService comMessageService;
	private OrderService orderServiceProxy;
	
	@Override
	public void process(Message message) {
		if (message.isOrderPaymentMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if (order.isCanceled() && order.isPaymentSucc()) {
				comMessageService.addSystemComMessage(Constant.EVENT_TYPE.PAYMENT_FAILED_ORDER.name(),"订单"+order.getOrderId()+"取消后被支付", Constant.SYSTEM_USER);
			}
		}
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

}
