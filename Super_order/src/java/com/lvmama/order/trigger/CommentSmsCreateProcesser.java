package com.lvmama.order.trigger;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.sms.CommentPointSmsCreator;
import com.lvmama.order.sms.MultiSmsCreator;
import com.lvmama.sms.dao.ComSmsDAO;

public class CommentSmsCreateProcesser implements MessageProcesser  {
	
	private static final Log log = LogFactory.getLog(CommentSmsCreateProcesser.class);
	private ComSmsDAO comSmsDAO;
	private OrderDAO orderDAO;
	private OrderPersonDAO orderPersonDAO;
	
	public void process(Message message) {
		if(message.isOrderPaymentMsg()) {
			OrdOrder order = orderDAO.selectByPrimaryKey(message.getObjectId());
			if (!order.IsAperiodic() && order.isPaymentSucc() && !order.isOther()) {
 				insertCommentSms(order);
			}
		}
		//创建订单时 如果是支付给供应商的也发第一次短信提醒
		OrdOrder orderCreate = orderDAO.selectByPrimaryKey(message.getObjectId());
		if(message.isOrderCreateMsg() && "TOSUPPLIER".equals(orderCreate.getPaymentTarget()) && "NORMAL".equals(orderCreate.getOrderStatus())){
           insertCommentSms(orderCreate);
		}
	}

	private void insertCommentSms(OrdOrder order) {
		String mobile = getContactMobile(order.getOrderId());
		MultiSmsCreator creator = new CommentPointSmsCreator(order.getOrderId(), mobile);
		List<ComSms> list = creator.createSmsList();
		for (ComSms comSms : list) {
			comSmsDAO.insert(comSms);
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

}
