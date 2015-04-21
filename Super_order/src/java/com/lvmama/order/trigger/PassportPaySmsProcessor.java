package com.lvmama.order.trigger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.order.logic.SmsSendLogic;
/**
 * 不立即发送二维码短信时，发送在线支付或景区支付提醒短信
 * @author chenkeke
 *
 */
public class PassportPaySmsProcessor implements MessageProcesser{
	private OrderService orderServiceProxy;
	private SmsSendLogic smsSendLogic;
	@Override
	public void process(Message message) {
		//支付给驴妈妈，并已经支付成功
		if (message.isOrderPaymentMsg() || message.isOrderApproveMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());			
			if(order.isPaymentSucc() && order.isPassportOrder() && order.isPayToLvmama() && order.isApprovePass() && !order.isCanceled()){
				if(smsSendLogic.isTimingLogic(order.getOrderId()) && !smsSendLogic.isNowSendPasscode(order.getOrderId()) && !smsSendLogic.isOnlyGuGongOrFangteSupplier(order.getOrderId())){
					//立即发送二维码订单，二维码在线支付支付成功短信
					smsSendLogic.sendPassportPayToLvmamaPaySuccess(message);
					//定时 游玩前一天发送二维码订单，二维码游玩提醒
					smsSendLogic.sendPassportVisitRemindByTiming(message,true);
				}else{
					//立即发送二维码订单，二维码游玩提醒
					smsSendLogic.sendPassportVisitRemindByTiming(message,false);
				}
			}
		}
		//支付给供应商，并已经下单成功
		if (message.isOrderApproveMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if(order.isApprovePass() && order.isPassportOrder() && order.isPayToSupplier() && !order.isCanceled()){
				if(smsSendLogic.isTimingLogic(order.getOrderId())  && !smsSendLogic.isNowSendPasscode(order.getOrderId()) && !smsSendLogic.isOnlyGuGongOrFangteSupplier(order.getOrderId())){
					//立即发送二维码订单，景区支付并下单成功短信
					smsSendLogic.sendPassportPayToSupplierOrderSuccess(message);
					//定时 游玩前一天发送二维码订单，二维码游玩提醒
					smsSendLogic.sendPassportVisitRemindByTiming(message,true);
				}
			}
		}
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setSmsSendLogic(SmsSendLogic smsSendLogic) {
		this.smsSendLogic = smsSendLogic;
	}
}
