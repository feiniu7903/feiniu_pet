package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;


import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

public class OrderForGugongSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator{
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private String cardNum;
	private OrdOrder order;
	private String ylOrderId;
	public OrderForGugongSmsCreator(Long orderId,String ylOrderId,String mobile) {
		this.objectId = orderId;
		this.mobile=mobile;
		this.ylOrderId=ylOrderId;
		order = orderServiceProxy.queryOrdOrderByOrderId(objectId);
	}
	
	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		cardNum=order.getContact().getCertNo();
		data.put("cardNumber", cardNum);
		data.put("ylOrderId",ylOrderId);
		return data;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.ORDER_FOR_GUGONG.name());
	}

}
