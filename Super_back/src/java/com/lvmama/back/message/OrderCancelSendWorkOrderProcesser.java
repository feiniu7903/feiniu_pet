package com.lvmama.back.message;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

/**
 * 订单取消，将改订单下所有的工单完成，并发送“订单取消提醒”工单给客服和计调
 * 
 * @author zhushuying
 * 
 */
public class OrderCancelSendWorkOrderProcesser implements MessageProcesser {
	private OrderService orderServiceProxy;
	private WorkOrderFinishedBiz workOrderFinishedProxy;
	private WorkOrderSenderBiz workOrderProxy;

	@Override
	public void process(Message message) {
		if (message.isOrderCancelMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message
					.getObjectId());
			if (order != null) {
				boolean sendFlag=false;//是否发“订单取消提醒(计调)”工单
				// 出境和长线线路产品
				if (Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(order.getOrderType())
						|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equalsIgnoreCase(order.getOrderType())
						|| Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(order.getOrderType()) 
						|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(order.getOrderType())) {
					// 在取消订单时先结束该订单下所以的工单
					workOrderFinishedProxy.finishWorkOrder(order.getOrderId());
					if(order.isNeedResourceConfirm()){
						// 发“订单取消提醒”给客服
						workOrderProxy.sendWorkOrder(order,
								Constant.WORK_ORDER_TYPE_AND_SENDGROUP.DDQXTX
										.getWorkOrderTypeCode(), null,
								Boolean.FALSE, Boolean.FALSE, null, null, null,
								null, null, false);
						//出境线路产品
						if(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(order.getOrderType()) 
								|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(order.getOrderType())){
							//资源状态为已审核/待跟进， 资源审核不通过
							if(Constant.ORDER_APPROVE_STATUS.VERIFIED.name().equals(order.getApproveStatus())
									||Constant.ORDER_APPROVE_STATUS.BEFOLLOWUP.name().equals(order.getApproveStatus()) ||
									Constant.ORDER_APPROVE_STATUS.RESOURCEFAIL.name().equals(order.getApproveStatus())){
								sendFlag=true;
							}
						}
					}
					//订单状态：取消,支付状态：已支付/部分支付，无需资源审核/需资源审核的
					if(Constant.ORDER_STATUS.CANCEL.name().equals(order.getOrderStatus())
							&&(Constant.PAYMENT_STATUS.PAYED.name().equals(order.getPaymentStatus())
									|| Constant.PAYMENT_STATUS.PARTPAY.name().equals(order.getPaymentStatus()))
							&&(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(order.getOrderType()) 
									|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(order.getOrderType()))){
						sendFlag=true;
					}
				}
				//发送工单
				if(sendFlag){
					workOrderProxy.sendWorkOrder(order,
						Constant.WORK_ORDER_TYPE_AND_SENDGROUP.DDQXTXJD
								.getWorkOrderTypeCode(),null,
						Boolean.TRUE,Boolean.TRUE,null,null,
						null,null,null ,false);
				}
			}
		}

	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}

	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
	}
	
}
