package com.lvmama.order.trigger;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OrderTrafficService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.logic.SmsSendLogic;
import com.lvmama.order.sms.BeforePerformSmsCreator;
import com.lvmama.order.sms.MultiSmsCreator;
import com.lvmama.sms.dao.ComSmsDAO;

public class OrderSmsCreateProcesser implements MessageProcesser  {
	
	private ComSmsDAO comSmsDAO;
	private OrderDAO orderDAO;
	private OrderPersonDAO orderPersonDAO;
	
	private OrderTrafficService orderTrafficService;
	private OrderService orderServiceImpl;
	private SmsSendLogic smsSendLogic;
	
	public void process(Message message) {
		if(message.isOrderPaymentMsg()) {
			OrdOrder order = orderDAO.selectByPrimaryKey(message.getObjectId());
			/**
			 * 出境游不需要游玩前短信，需求来自http://192.168.0.10:3000/issues/336
			 * @author Brian
			 * @since 2011/08/18
			 * 
			 * 添加判断，如果支付时间与游玩日期是同一天的情况下不产生该短信
			 * 添加判断，不定期订单不产生该短信
			 */
			if (!order.IsAperiodic() && !DateUtils.isSameDay(order.getVisitTime(), new Date())&&order.isPaymentSucc() && 
					!(Constant.ORDER_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(order.getOrderType()) || 
					Constant.ORDER_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(order.getOrderType()))) {
				String mobile = getContactMobile(message.getObjectId());
				MultiSmsCreator creator = new BeforePerformSmsCreator(message.getObjectId(), mobile);
				List<ComSms> list = creator.createSmsList();
				for (ComSms comSms : list) {
					comSmsDAO.insert(comSms);
				}
			}
		}else if(message.isTrainIssueMsg()){
			OrdOrderTraffic traffic =orderTrafficService.getTrafficByOrderItemMetaId(message.getObjectId());
			if(traffic==null){
				return;
			}
			OrdOrder order = orderServiceImpl.queryOrdOrderByOrderItemMetaId(traffic.getOrderItemMetaId());
//			List<OrdOrderItemMeta> itemList = orderServiceImpl.queryOrdOrderItemMetaByOrderId(order.getOrderId());
//			OrdOrderItemMeta itemMeta = OrderUitl.getMeta(itemList, traffic.getOrderItemMetaId());
			smsSendLogic.sendTrainIssueSms(order,traffic.getOrderItemMetaId());
		}
	}
	 
	private String getContactMobile(Long orderId) {
		return this.orderPersonDAO.getOrdPersonMobile(orderId, Constant.ORD_PERSON_TYPE.CONTACT.name());
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	public void setComSmsDAO(ComSmsDAO comSmsDAO) {
		this.comSmsDAO = comSmsDAO;
	}

	public void setOrderTrafficService(OrderTrafficService orderTrafficService) {
		this.orderTrafficService = orderTrafficService;
	}

	public void setOrderServiceImpl(OrderService orderServiceImpl) {
		this.orderServiceImpl = orderServiceImpl;
	}

	public void setSmsSendLogic(SmsSendLogic smsSendLogic) {
		this.smsSendLogic = smsSendLogic;
	}

	
}
