package com.lvmama.front.web.stored;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.front.web.BaseAction;

/**
 * 前台储值卡支付.
 * @author liwenzhan
 *
 */
@Results({
	@Result(name="toPayCompleteView",location="/view/view.do?orderId=${orderId}&cardNo=${cardNo}", type = "redirect"),
	@Result(name = "storedCard_usage", location = "/WEB-INF/pages/stored/storedView_new.ftl", type = "freemarker"),
	@Result(name = "storedCard_usage_new", location = "/WEB-INF/pages/stored/storedCard_usage_new.ftl", type = "freemarker")
})
public class StoredPayAction extends BaseAction {
	/**
	 * 日志输入器
	 */
	private static final Log LOG = LogFactory.getLog(StoredPayAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -5020818261165125591L;

	/**
	 * 储值卡支付SERVICE.
	 */
	private StoredCardService storedCardService ;
	/**
	 * 订单SERVICE.
	 */
	private OrderService orderServiceProxy;
	/**
	 * 消息.
	 */
	private transient TopicMessageProducer orderMessageProducer;
	/**
	 * 订单ID.
	 */
	private Long orderId;
	/**
	 * 储值卡卡号.
	 */
	private String cardNo;
	/**
	 * 返回信息.
	 */
	private String message="";
	private String verifycode="";
	/**
	 * 储值卡支付.
	 * @return
	 * @throws IOException
	 */
	@Action("/stored/storedPay")
	public String storedPay() throws IOException {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(order.isFullyPayed()){
				message = "该订单已经支付完成!";
			}else{
				/*if (card.isPay()) {
					moneyAccountService.doPayStoredCard(orderId, this.getUserId(), cardNo);
					order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
					if(order.isPartPay()){
						orderMessageProducer.sendMsg(MessageFactory.newOrderPartpayPaymentMessage(order.getOrderId()));
					}else if (order.isPaymentSucc()) {
						orderMessageProducer.sendMsg(MessageFactory.newOrderPaymentMessage(order.getOrderId()));
					}
					storedCardService.storedPay(orderId, this.getUserId(),cardNo,true);
				} else {
					message = "储值卡状态为正常,已激活,已出库,未过期才可以支付,请核对改卡状态!";
				}*/
			}
		return "toPayCompleteView";
	}
	/**
	 * 储值卡查询.
	 * @return
	 * @throws IOException
	 */
	@Action("/stored/goStoredSearch")
	public String goStoredSearch() throws IOException {
		
		return "storedCard_usage_new";
	}
	
	public StoredCardService getStoredCardService() {
		return storedCardService;
	}
	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}
	
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
}
