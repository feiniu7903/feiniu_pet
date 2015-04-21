package com.lvmama.order.trigger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

/**
 * 支付完成，发送工单给客服和计调人员
 * 
 * @author zhushuying
 *
 */
public class PayedSenderWorkOrderProcessor implements MessageProcesser{
	private OrderService orderServiceProxy;
	private WorkOrderSenderBiz workOrderSenderProxy;

	public void process(Message message) {
		if (message.isOrderPaymentMsg()){
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message
					.getObjectId());
			if (order != null) {
				//只针对出境的线路类型发送工单
				if (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name()
						.equalsIgnoreCase(order.getOrderType())
						|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()
								.equalsIgnoreCase(order.getOrderType())) {
					// 支付后提醒（客服）
					workOrderSenderProxy.sendWorkOrder(order,
							Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZFHTX
									.getWorkOrderTypeCode(), "", Boolean.FALSE,
							Boolean.FALSE, null, null, null, null, null, false);

					// 已支付提醒（计调）
					workOrderSenderProxy.sendWorkOrder(order,
							Constant.WORK_ORDER_TYPE_AND_SENDGROUP.YZFTX
									.getWorkOrderTypeCode(), "", Boolean.FALSE,
							Boolean.TRUE, null, null, null, null, null, false);
				}
			}
		}
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setWorkOrderSenderProxy(WorkOrderSenderBiz workOrderSenderProxy) {
		this.workOrderSenderProxy = workOrderSenderProxy;
	}
}
