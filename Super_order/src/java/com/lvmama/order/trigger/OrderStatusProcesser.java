package com.lvmama.order.trigger;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.com.dao.ComSeqNoDAO;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;

public class OrderStatusProcesser implements MessageProcesser {
	
	private OrderDAO orderDAO;
	private ComSeqNoDAO comSeqNoDAO;
	
	public void process(Message message) {
		if (message.isOrderMessage()) {
			Map<String, String> orderParam = new HashMap<String, String>();
			OrdOrder order = orderDAO.selectByPrimaryKey(message.getObjectId());
			orderParam.put("orderId", order.getOrderId().toString());
			if (message.isOrderCreateMsg()) {
				if (order.isPayToLvmama()) {
					if (order.isNeedResourceConfirm()) {
						if (Constant.ORDER_RESOURCE_STATUS.UNCONFIRMED.name().equalsIgnoreCase(order.getResourceConfirmStatus())) {
							orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.UNVERIFIED.name());
						}else{
							orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.UNPAY.name());
						}
					}else{
						orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.UNPAY.name());
					}
				} else {
					if (order.isNeedResourceConfirm() && order.isApprovePass()) {
						orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.UNPAY.name());
					}else{
						orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.UNVERIFIED.name());
					}
				}
			}
			if (message.isOrderApproveMsg()) {
				if (order.isApprovePass() && order.isNormal()) {
					if(order.isUnpay()){
						orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.UNPAY.name());
					}
					orderParam.put("orderNo", comSeqNoDAO.getSeqNo("ORD_ORDER"));
				}
			}
			if (message.isOrderPaymentMsg()) {
				if (order.isFullyPayed() && order.isNormal()) {
					orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.PAYED.name());
				}
				if (!order.isNeedResourceConfirm()) {
					orderParam.put("orderNo", comSeqNoDAO.getSeqNo("ORD_ORDER"));
				}
			}
			if (message.isOrderCancelMsg()) {
				orderParam.put("orderStatus", Constant.ORDER_VIEW_STATUS.CANCEL.name());
				orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.CANCEL.name());
			}
			if (message.isOrderFinishMsg()) {
				orderParam.put("orderStatus", Constant.ORDER_VIEW_STATUS.FINISHED.name());
				orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.FINISHED.name());
			}
			if (message.isOrderRetoreMsg()) {
				if (order.isPaymentSucc()) {
					orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.PAYED.name());
				}else{
					orderParam.put("orderViewStatus", Constant.ORDER_VIEW_STATUS.UNPAY.name());
				}
			}
			orderDAO.updateByParamMap(orderParam);
		}
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setComSeqNoDAO(ComSeqNoDAO comSeqNoDAO) {
		this.comSeqNoDAO = comSeqNoDAO;
	}

}
