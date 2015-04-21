package com.lvmama.front.web.buy;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.front.web.BaseAction;
@Results({
	@Result(name = "invalid.token", location = "/WEB-INF/pages/buy/wrong.ftl", type = "freemarker"),
	@Result(name="toPayCompleteView",location="/view/view.do?orderId=${orderId}", type = "redirect"),
	@Result(name="paysuccess",location="/WEB-INF/pages/usr/money/order_pay_detail.ftl",type="freemarker")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"), @InterceptorRef("token") })
public class AccountPayAction extends BaseAction{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private CashAccountService cashAccountService ;
	
	
	protected Long orderId;
//	@Action("/view/accountpay")
//	public String  accountPay() throws IOException{
//		//全额支付
//		cashAccountService.totalPay(orderId, this.getUserId());
//		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
//		if(order.isPaymentSucc()) {
//			orderMessageProducer.sendMsg(MessageFactory.newOrderPaymentMessage(order.getOrderId()));
//		}else if(order.isPartPay()){
//			orderMessageProducer.sendMsg(MessageFactory.newOrderPartpayPaymentMessage(order.getOrderId()));
//		}
//		//return "paysuccess";
//		return "toPayCompleteView";
//
//
//
//	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	/**
	 * orderMessageProducer.
	 */
	private transient TopicMessageProducer orderMessageProducer;

			/**
	 * setOrderMessageProducer.
	 *
	 * @param orderMessageProducer
	 *            orderMessageProducer
	 */
	public void setOrderMessageProducer(
			final TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	private OrderService orderServiceProxy;

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
	
}
