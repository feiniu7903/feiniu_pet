package com.lvmama.order.trigger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.logic.SmsSendLogic;

public class OrderSmsSendProcesser implements MessageProcesser {

	private SmsSendLogic smsSendLogic;
	private OrderDAO orderDAO;

	public void process(Message message) {
		if (message.isOrderMessage()) {
			OrdOrder order = orderDAO.selectByPrimaryKey(message.getObjectId());
			if (message.isOrderCreateMsg()) {
				if (order.isPayToLvmama() && order.isNeedResourceConfirm() && !order.isApproveResourceAmple()) {
					smsSendLogic.sendCreatePayToLvmamaOrderSucc(message);
				}else if (!order.isPayToLvmama() && !order.isPassportOrder() && order.isApproveResourceAmple() && order.isApprovePass()) {
					smsSendLogic.sendCreatePayToSupplierOrderSucc(message);
				}
			}else if(message.isOrderCancelMsg()) {
				if (order.isCanceled()) {
					smsSendLogic.sendCancelOrder(message);
				}
			}else if(message.isOrderApproveMsg()) {//已经支付的订单不发送该短信
				if (order.isPayToLvmama() && order.isNeedResourceConfirm() && order.isApprovePass() && !order.isPaymentSucc()) {
					smsSendLogic.sendApprovePassPayToLvmama(message);
				}else if(order.isPayToLvmama() && (!order.isPassportOrder() || order.hasSelfPack()) && order.isNeedResourceConfirm() && order.isApprovePass() && order.isPaymentSucc()){
					smsSendLogic.sendPayToLvmamaNormalCert(message);
				}
				if (!order.isPayToLvmama() && order.isApprovePass()) {
					//不是二维码定时发送就立即发送普通二维码短信
					if(!(smsSendLogic.isTimingLogic(order.getOrderId()) && !smsSendLogic.isNowSendPasscode(order.getOrderId()) && !smsSendLogic.isOnlyGuGongOrFangteSupplier(order.getOrderId()))){
						smsSendLogic.sendPayToSupNormalCert(message);
					}
				}
			}else if(message.isOrderPaymentMsg()) {
				//不定期订单单独处理
				if(!order.IsAperiodic()) {
					if (order.isPaymentSucc() && (!order.isPassportOrder() || order.hasSelfPack())&& order.isApprovePass()) {
						smsSendLogic.sendPayToLvmamaNormalCert(message);
					}else if(order.isPaymentSucc() && !order.isApprovePass()){
						smsSendLogic.sendOrderPrePay(message);
					}
				}
			}
		}
	}

	public void setSmsSendLogic(SmsSendLogic smsSendLogic) {
		this.smsSendLogic = smsSendLogic;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

}
