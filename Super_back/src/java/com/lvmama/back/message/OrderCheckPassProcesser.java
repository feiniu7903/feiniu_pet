package com.lvmama.back.message;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

/**
 * 资源审核通过时发工单到客服
 * 
 * @author zhangwengang
 * 
 */
public class OrderCheckPassProcesser implements MessageProcesser {
	private OrderService orderServiceProxy;
	private WorkOrderSenderBiz workOrderProxy;

	@Override
	public void process(Message message) {
		if (message.isOrderResourceMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message
					.getObjectId());
			// 只获取订单状态为审核通过的记录
			if (order.isApproveResourceAmple()) {

				// 只获取长线和出境的记录
				if ((Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name()
						.equalsIgnoreCase(order.getOrderType())
						|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name()
								.equalsIgnoreCase(order.getOrderType())
						|| Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name()
								.equalsIgnoreCase(order.getOrderType()) || Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN
						.name().equalsIgnoreCase(order.getOrderType()))) {
					workOrderProxy.sendWorkOrder(order,
							Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZYSHTG
									.getWorkOrderTypeCode(), null,
							Boolean.FALSE, Boolean.FALSE, null, null, null,
							null,null,false);
				}
			}
		}

	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
	}
}
