package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class OrderPrePaySmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator {
	private static final Log log = LogFactory.getLog(OrderPrePaySmsCreator.class);
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	
	private PayPaymentService payPaymentService;
	
	private String visitDate;
	private OrdOrder order;
	
	public OrderPrePaySmsCreator(Long orderId,String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
		order = orderServiceProxy.queryOrdOrderByOrderId(objectId);
	}

	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		visitDate = DateUtil.getFormatDate(order.getVisitTime(), "yyyy-MM-dd");
		data.put("visitDate", visitDate);
		data.put("orderId", objectId);
		data.put("sendTime",DateUtil.getDateAfterMinutes(1L));
		return data;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		String smsTemplate=Constant.SMS_TEMPLATE.ORDER_PAY_SUCC_WAIT_APPROVE.name();
		log.info("OrderId:"+objectId+",Order Channel:"+order.getChannel()+","+smsTemplate);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), smsTemplate);
	}
	
	
	
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

}
