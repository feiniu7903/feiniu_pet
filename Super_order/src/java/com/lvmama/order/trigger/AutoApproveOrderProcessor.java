package com.lvmama.order.trigger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.vo.Constant.ORDER_APPROVE_STATUS;
import com.lvmama.order.service.OrderUpdateService;

public class AutoApproveOrderProcessor implements MessageProcesser {
	private static final Log log = LogFactory.getLog(AutoApproveOrderProcessor.class);
	private OrderUpdateService orderUpdateService;
	private TopicMessageProducer orderMessageProducer;
	private OrderService orderServiceImpl;

	@Override
	public void process(Message message) {
		if (message.isOrderCreateMsg()) {
			OrdOrder ordOrder = orderUpdateService.getToAutoApproveOrder(message.getObjectId());
			if(log.isDebugEnabled()){
				log.debug("auto approve order id:"+message.getObjectId()+"paymentTagert:"+ordOrder.getPaymentTarget()+",isApprovePass:"+ordOrder.getApproveStatus());
			}
			//支付给供应商，没有审核通过
			if (ordOrder != null && !ordOrder.isPayToLvmama()
					&& !ordOrder.isApprovePass()) {
				orderUpdateService.orderAutoApprovePass(ordOrder);
				orderMessageProducer.sendMsg(MessageFactory.newOrderApproveMessage(ordOrder.getOrderId()));
			}else{ 
				//对支付给驴妈妈相关自动审核处理
				OrdOrder orderDetail = orderServiceImpl.queryOrdOrderByOrderId(message.getObjectId());//取出订单的全部信息
				if(orderDetail.isPayToLvmama()&&!orderDetail.isApprovePass()){
					if(orderDetail.isHotel()&&orderDetail.isNeedResourceConfirm()&&StringUtils.equals(orderDetail.getApproveStatus(),ORDER_APPROVE_STATUS.UNVERIFIED.name())&&orderDetail.getOrdOrderItemProds().size()==1){
						boolean successFlag=orderUpdateService.updateOrdOrderApproveStatus(ordOrder.getOrderId(), ORDER_APPROVE_STATUS.INFOPASS.name(), "SYSTEM");
						if (successFlag) {
							orderMessageProducer.sendMsg(MessageFactory
									.newOrderApproveMessage(orderDetail.getOrderId()));						
						} 
					}
				}
			}
		}
	}

	public void setOrderMessageProducer(
			TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setOrderUpdateService(OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}

	public void setOrderServiceImpl(OrderService orderServiceImpl) {
		this.orderServiceImpl = orderServiceImpl;
	}

	
}
