package com.lvmama.message;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.jinjiang.service.JinjiangOrderService;

public class JinJiangOrderProcessor implements MessageProcesser {

	private JinjiangOrderService jinjiangOrderService;
	
	@Override
	public void process(Message message) {
		if(message.isOrderCreateMsg()){
			jinjiangOrderService.submitOrder(message.getObjectId());
		}
		if( message.isSupplierChannelReSubmit()){
			jinjiangOrderService.reSubmitOrder(message.getObjectId());
		}
		if(message.isOrderCancelMsg()|| message.isSupplierChannelReCancel()){
			jinjiangOrderService.cancelOrder(message.getObjectId());
		}
		if(message.isOrderPaymentMsg()|| message.isSupplierChannelRePayed()){
			jinjiangOrderService.payOrder(message.getObjectId());
		}
	}

	public JinjiangOrderService getJinjiangOrderService() {
		return jinjiangOrderService;
	}

	public void setJinjiangOrderService(JinjiangOrderService jinjiangOrderService) {
		this.jinjiangOrderService = jinjiangOrderService;
	}

	
}
