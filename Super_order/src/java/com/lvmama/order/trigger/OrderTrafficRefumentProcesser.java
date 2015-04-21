/**
 * 
 */
package com.lvmama.order.trigger;

import java.util.List;

import org.apache.commons.logging.Log;

import com.ibatis.common.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficRefund;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.OrderUitl;
import com.lvmama.comm.vo.Constant;

/**
 * 负责处理火车票退款
 * @author yangbin
 *
 */
public class OrderTrafficRefumentProcesser implements MessageProcesser{
	
	private OrderTrafficService orderTrafficService;
	private OrderService orderServiceImpl;
	private TopicMessageProducer orderMessageProducer;
	private final static Log logger=org.apache.commons.logging.LogFactory.getLog(OrderTrafficRefumentProcesser.class);

	/* (non-Javadoc)
	 * @see com.lvmama.comm.jms.MessageProcesser#process(com.lvmama.comm.jms.Message)
	 */
	@Override
	public void process(Message message) {
		if(hasTrainOrderMsg(message)){
			OrdOrderTraffic traffic =orderTrafficService.getTrafficByOrderItemMetaId(message.getObjectId());
			if(traffic==null){
				return;
			}
			if(message.isTrainIssueMsg()){
				if (traffic.hasNeedRefument() && traffic.hasIssueStatus()) {
					OrdOrder order = orderServiceImpl.queryOrdOrderByOrderItemMetaId(traffic.getOrderItemMetaId());
					List<OrdOrderItemMeta> itemList = orderServiceImpl.queryOrdOrderItemMetaByOrderId(order.getOrderId());
					OrdOrderItemMeta itemMeta = OrderUitl.getMeta(itemList, traffic.getOrderItemMetaId());
					long amount = orderTrafficService.getTrafficAmount(traffic.getOrderTrafficId());
					long refundAmount = itemMeta.getTotalSettlementPrice() - amount;
					Long refundId=orderServiceImpl.autoCreateOrdRefundmentBySupplier(order,traffic.getOrderItemMetaId(), refundAmount, "SYSTEM", "火车票车位差价退款");
					if(refundId>0L){
						orderTrafficService.updateRefumentStatus(traffic.getOrderTrafficId(),Constant.ORDER_TRAFFIC_REFUMENT.REFUMENTED);
					}
				}
			}
			else if(message.isTrainCancelRefundMsg()){//退款处理
				logger.info("接收到火车票退款请求,开始处理中...");
				OrdOrderTrafficRefund refund = orderTrafficService.getTrafficRefund(traffic.getOrderTrafficId(),message.getAddition());
				if(refund==null){
					logger.error("train refund don't found, orderItemMetaId:"+traffic.getOrderItemMetaId()+",billNo:"+message.getAddition());
					return;
				}
				
				OrdOrder order = orderServiceImpl.queryOrdOrderByOrderItemMetaId(traffic.getOrderItemMetaId());
				//List<OrdOrderItemMeta> itemList = orderServiceImpl.queryOrdOrderItemMetaByOrderId(order.getOrderId());
				orderServiceImpl.autoCreateOrdRefundmentBySupplier(order,traffic.getOrderItemMetaId(), Math.abs(refund.getAmount()), "SYSTEM", "火车票取消退款，退款供应商流水号"+refund.getBillNo());
				
				logger.info("退款成功，现在发送退款成功请求到供应商.RefundId:" + refund.getOrderTrafficRefundId());
				//退款成功后，发送退款成功通知到供应商处
				orderMessageProducer.sendMsg(MessageFactory.newTrainRefundSuccessMessage(refund.getOrderTrafficRefundId()));
			}
		}
	}
	
	private boolean hasTrainOrderMsg(final Message message){
		return message.isTrainIssueMsg() || message.isTrainCancelRefundMsg();
	}
	public void setOrderTrafficService(OrderTrafficService orderTrafficService) {
		this.orderTrafficService = orderTrafficService;
	}
	public void setOrderServiceImpl(OrderService orderServiceImpl) {
		this.orderServiceImpl = orderServiceImpl;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
}
