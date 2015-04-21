package com.lvmama.order.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.OrderUitl;
import com.lvmama.comm.vo.Constant;

public class PaymentRemindSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator {
	private OrdOrder order;
	private OrderService orderServiceProxy =(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	public PaymentRemindSmsCreator(Long orderId, String mobile) {		
		this.mobile = mobile;
		this.objectId = orderId;
		this.order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
	}
	
	private static final String MSG="若您无法在此时间内完成支付，可致电驴妈妈客服帮您延长支付时间，避免您的订单被取消。";

	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		Date theTime = DateUtil.getDateAfterMinutes(order.getWaitPayment());
		data.put("time", DateUtil.getFormatDate(theTime, "yyyy-MM-dd HH:mm"));
		String waitPaymentRemind="";
		if(OrderUitl.hasWaitpaymentChange(order)){
			waitPaymentRemind = MSG;
		}
		data.put("waitPaymentRemind", waitPaymentRemind);
		return data;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.PAYMENT_REMIND.name());
	}	
}
