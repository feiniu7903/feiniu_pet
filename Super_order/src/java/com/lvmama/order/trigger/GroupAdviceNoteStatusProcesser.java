package com.lvmama.order.trigger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.OrderRouteService;
/**
 * 支付成功，修改订单的出团通知书状态为  待发送
 * @author zhaojindong
 *
 */
public class GroupAdviceNoteStatusProcesser implements MessageProcesser{
	private static final Log log = LogFactory.getLog(GroupAdviceNoteStatusProcesser.class);
	private OrderRouteService orderRouteService;
	
	@Override
	public void process(Message message) {
		if (message.isOrderPaymentMsg()) {
			log.info("订单支付成功消息处理，修改出团通知书状态。订单号：" + message.getObjectId());
			OrdOrderRoute route = orderRouteService.queryOrdOrderRouteByOrderId(message.getObjectId());
			if(route == null){
				log.error("无法获取出团通知书状态");
				return;
			}
			route.setGroupWordStatus(Constant.GROUP_ADVICE_STATE.NEEDSEND.name());
			orderRouteService.updateOrderRoute(route);
			log.info("订单支付成功消息处理，修改出团通知书状态完成。");
		}
	}

	public OrderRouteService getOrderRouteService() {
		return orderRouteService;
	}

	public void setOrderRouteService(OrderRouteService orderRouteService) {
		this.orderRouteService = orderRouteService;
	}
	
}
