package com.lvmama.order.jobs;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.OrderUpdateService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class AutoCancelOrderJob implements Runnable {

	private static final Log log = LogFactory.getLog(AutoCancelOrderJob.class);
	private OrderUpdateService orderUpdateService;
	private OrderService orderServiceProxy;
	private TopicMessageProducer orderMessageProducer;
	private PublicWorkOrderService publicWorkOrderService;
	
	@SuppressWarnings("unchecked")
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto Cancel order launched.");
			List<OrdOrder> list = this.orderUpdateService
					.getToAutoCancelOrder();
			for (OrdOrder ordOrder : list) {
				orderUpdateService.cancelOrder(ordOrder.getOrderId(),
						"系统自动取消订单", "SYSTEM", null);
				log.info("order_id:" + ordOrder.getOrderId() + "  "
						+ ordOrder.hasNeedPrePay() + "   " + ordOrder.isUnpay());
				
				if(ordOrder.getHasSupplierChannelOrder()){
					orderMessageProducer.sendMsg(MessageFactory.newOrderCancelMessage(ordOrder.getOrderId()));
				}
				if (ordOrder.hasNeedPrePay() && !ordOrder.isUnpay()) {
					orderServiceProxy.autoCreateOrderFullRefund(ordOrder,
							"SYSTEM", "必须预授权订单，自动废单");
				}
				
				// 判断是否重复发工单
				boolean isExistFlag = publicWorkOrderService.isExists(
						ordOrder.getOrderId(), null, Constant.WORK_ORDER_TYPE_AND_SENDGROUP.DDQXTXJD
						.getWorkOrderTypeCode(), null);
				if (!isExistFlag) {
					orderMessageProducer.sendMsg(MessageFactory
							.newOrderCancelMessage(ordOrder.getOrderId()));
				}
			}
		}
	}

	public void setOrderUpdateService(OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}

	public void setOrderMessageProducer(
			TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setPublicWorkOrderService(
			PublicWorkOrderService publicWorkOrderService) {
		this.publicWorkOrderService = publicWorkOrderService;
	}

}
