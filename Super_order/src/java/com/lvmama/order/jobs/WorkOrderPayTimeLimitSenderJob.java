package com.lvmama.order.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;
import com.lvmama.order.service.OrderUpdateService;

public class WorkOrderPayTimeLimitSenderJob implements Runnable {

	private static Log log = LogFactory
			.getLog(WorkOrderPayTimeLimitSenderJob.class);

	private WorkOrderSenderBiz workOrderSenderProxy;
	
	private OrderUpdateService orderUpdateService;
	
	private OrderService orderServiceProxy;
	/**
	 * 支付等待超时发系统工单给客服
	 * */
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto WorkOrderPayTimeLimitSenderJob running.....");

			Map<String, Object> params=new HashMap<String, Object>();
			params.put("time1", 60);
			params.put("time2", 70);
			String[] orderType = { Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(),
					Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name(),
					Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(),
					Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name() };
			params.put("orderType", orderType);
			List<OrdOrder> ordersList = orderUpdateService.getNeedSendWorkOrderOrderList(params);
			if(!ordersList.isEmpty()){
				// 支付等待超时提醒
				for (OrdOrder ordOrder : ordersList) {
					searchOrderMsg(ordOrder);

					workOrderSenderProxy.sendWorkOrder(ordOrder,
							Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZFDDCSTX
									.getWorkOrderTypeCode(),
							"/super_back/ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId="
									+ ordOrder.getOrderId(), Boolean.FALSE,
							Boolean.FALSE, null, null, null, null, null, false);
				}
			}
			
			Map<String,Object> params1=new HashMap<String, Object>();
			params1.put("time1", 30);
			params1.put("time2", 40);
			//订单类型
			String[] orderType1 = { 
					//Constant.ORDER_TYPE.TICKET.name(),
					Constant.ORDER_TYPE.HOTEL.name(),
					Constant.ORDER_TYPE.GROUP.name(),
					Constant.ORDER_TYPE.FREENESS.name(),
					Constant.ORDER_TYPE.SELFHELP_BUS.name()};
			params1.put("orderType", orderType1);
			//订单渠道
			String[] channel={Constant.CHANNEL.FRONTEND.name(),
					Constant.CHANNEL.SERVICESTATION.name(),
					Constant.CHANNEL.TOUCH.name(),
					Constant.CHANNEL.CLIENT.name(),
					Constant.CHANNEL.BACKEND.name()};
			params1.put("channel", channel);
			//查询需要发送催支付提醒工单的订单列表
			List<OrdOrder> list = orderUpdateService.getNeedSendWorkOrderOrderList(params1);
			if (!list.isEmpty()) {
				for (OrdOrder order : list) {
					// 查询游客信息
					searchOrderMsg(order);
					workOrderSenderProxy.sendWorkOrder(order,
							Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CZFTXGD
									.getWorkOrderTypeCode(), null,
							Boolean.FALSE, Boolean.FALSE, null, null, null,
							null, null, false);
				}
			}
		}
	}

	//获取订单的相关信息
	private void searchOrderMsg(OrdOrder order) {
		OrdPerson per = new OrdPerson();
		per.setObjectId(order.getOrderId());
		List<OrdPerson> traveller = orderServiceProxy.getOrdPersons(per);
		order.setTravellerList(traveller);

		List<OrdOrderItemMeta> itemMeta = orderServiceProxy.queryOrdOrderItemMetaByOrderId(order.getOrderId());
		order.setAllOrdOrderItemMetas(itemMeta);

		List<OrdOrderItemProd> itemProd = orderUpdateService.selectOrderItemProdByOrderId(order.getOrderId());
		order.setOrdOrderItemProds(itemProd);
	}

	public WorkOrderSenderBiz getWorkOrderSenderProxy() {
		return workOrderSenderProxy;
	}

	public void setWorkOrderSenderProxy(WorkOrderSenderBiz workOrderSenderProxy) {
		this.workOrderSenderProxy = workOrderSenderProxy;
	}

	public void setOrderUpdateService(OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}