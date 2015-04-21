package com.lvmama.back.message;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

/**
 * 出团通知书状态为“已上传未通知”“已修改未通知”即可发系统工单给客服
 * 
 * @author zhangwengang
 * 
 */
public class OrderGroupWordStatusProcesser implements MessageProcesser {
	private OrderService orderServiceProxy;
	private WorkOrderSenderBiz workOrderProxy;

	@Override
	public void process(Message message) {
		if (message.isOrderGroupWordStatusMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message
					.getObjectId());
			// 只获取订单状态为审核通过的记录
			if (order.isApproveResourceAmple()) {
				// 出团通知书工单
				if (Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name()
						.equalsIgnoreCase(order.getOrderType())
						|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name()
								.equalsIgnoreCase(order.getOrderType())
						|| Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name()
								.equalsIgnoreCase(order.getOrderType())
						|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()
								.equalsIgnoreCase(order.getOrderType())) {
					workOrderProxy.sendWorkOrder(order,
							Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CTTZS
									.getWorkOrderTypeCode(),
							"/super_back/ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId="
									+ order.getOrderId(), Boolean.TRUE,
							Boolean.FALSE, null, null, null, null,null,false);
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
