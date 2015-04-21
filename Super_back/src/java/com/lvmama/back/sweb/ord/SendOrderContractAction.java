package com.lvmama.back.sweb.ord;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.vo.Constant;
@ParentPackage("json-default")
public class SendOrderContractAction extends BaseAction {

	private static final long serialVersionUID = 8231378999478632004L;
	private Long orderId;
	private String paymentStatus;
	private Long productId;
	private String result;
	
	/**
	 * orderMessageProducer.
	 */
	private transient TopicMessageProducer orderMessageProducer;
	@Action(value = "/ajax/sendContractEmail", results = @Result(type = "json", name = "sendContractEmail", params = {
			"orderId",
			"productId" }))
	public String sendContractEmail() {
		if (null == orderId){
			result="订单ID为空";
		}else if(!Constant.PAYMENT_STATUS.PAYED.name().equalsIgnoreCase(paymentStatus)){
			result="订单没有支付！";
		}else{
			try{
				orderMessageProducer.sendMsg(MessageFactory.newOrderSendEContract(orderId));
				result="发送成功！";
			}catch(Exception e){
				result="发送失败："+e;
			}
		}
		return "sendContractEmail";
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

}
