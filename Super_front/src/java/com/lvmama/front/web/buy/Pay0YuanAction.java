package com.lvmama.front.web.buy;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.front.web.BaseAction;
@Results( {
		@Result(name = "payMentComplent", location = "/order/toSuccess.do?orderId=${orderId}", type = "redirect")
	})
public class Pay0YuanAction extends BaseAction {
	private Logger LOG = Logger.getLogger(this.getClass());
	private TopicMessageProducer orderMessageProducer;
	private Long orderId;
	/**
	 * 
	 */
	@Action("/pay/pay0")
	public String execute(){
		if(orderId!=null && orderId>0){
			orderMessageProducer.sendMsg(MessageFactory.newOrderPay0YuanMessage(orderId));	
		}
		return "payMentComplent";
	} 
	
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrderId() {
		return orderId;
	}
}
