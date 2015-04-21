package com.lvmama.back.message;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;

/**
 * 期票订单废单,废除密码券
 * 
 * @author shihui
 * 
 */
public class OrderItemMetaAperiodicProcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(OrderItemMetaAperiodicProcesser.class);
	private OrderService orderServiceProxy;
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService;
	
	public void process(Message message) {
		log.info(message);
		//将不定期密码券变更为未激活 add by shihui
		if(message.isOrderPaymentMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if(order == null) {
				log.info("订单：" + order.getOrderId() + "不存在");
				return;
			}
			if(order.IsAperiodic() && order.isPaymentSucc() && order.isPayToLvmama() && order.isApprovePass() && !order.isCanceled()) {
				for (OrdOrderItemMeta ordOrderItemMeta : order.getAllOrdOrderItemMetas()) {
					if(!ordOrderItemMeta.isOtherProductType()) {
						OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicService.selectOrderAperiodicByOrderItemMetaId(ordOrderItemMeta.getOrderItemMetaId());
						if(aperiodic != null) {
							aperiodic.setActivationStatus(Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name());
							orderItemMetaAperiodicService.updateStatusByPrimaryKey(aperiodic);
							log.info("update aperiodic's status to UNACTIVATED, aperiodicId:" + aperiodic.getAperiodicId());
						}
					}
				}
			}
		} else if (message.isOrderCancelMsg()) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if(order == null) {
				log.info("订单：" + order.getOrderId() + "不存在");
				return;
			}
			//不定期废单,废除密码券
			if (order.IsAperiodic() && order.isCanceled()) {
				log.info("Order cancel, orderId: "+order.getOrderId());
				//不定期订单废掉对应的密码券
				List<OrdOrderItemMeta> ordOrderItemMetaList = order.getAllOrdOrderItemMetas();
				for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList) {
					if(!ordOrderItemMeta.isOtherProductType()) {
						OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicService.selectOrderAperiodicByOrderItemMetaId(ordOrderItemMeta.getOrderItemMetaId());
						if(aperiodic != null) {
							aperiodic.setActivationStatus(Constant.APERIODIC_ACTIVATION_STATUS.INVALID.name());
							orderItemMetaAperiodicService.updateStatusByPrimaryKey(aperiodic);
						}
					}
				}
			}
		}
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderItemMetaAperiodicService(
			OrderItemMetaAperiodicService orderItemMetaAperiodicService) {
		this.orderItemMetaAperiodicService = orderItemMetaAperiodicService;
	}
}
