package com.lvmama.order.trigger;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.logic.SmsSendLogic;
import com.lvmama.order.sms.CommentPointSmsCreator;
import com.lvmama.order.sms.MultiSmsCreator;
import com.lvmama.sms.dao.ComSmsDAO;

/**
 * @author shihui,zhangjie
 * 
 *         不定期订单发送短信
 *         取消订单短信走正常通道
 * */
public class OrderAperiodicSmsProcesser implements MessageProcesser {

	private static final Log log = LogFactory
			.getLog(OrderAperiodicSmsProcesser.class);
	private OrderService orderServiceProxy;
	private ComSmsTemplateService comSmsTemplateService;
	private OrderPersonDAO orderPersonDAO;
	private ComSmsDAO comSmsDAO;
	private SmsSendLogic smsSendLogic;

	public void process(Message message) {
		// 订单支付成功发送短信
		if (message.isOrderPaymentMsg()) {
			log.info("创建期票订单支付成功短信,orderId:"+message.getObjectId());
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(message
					.getObjectId());
			//申码的不定期订单不走此通道(申码成功后发送)
			if(ordOrder != null && ordOrder.IsAperiodic() && ordOrder.isPaymentSucc() && !ordOrder.isPassportOrder() && ordOrder.isApprovePass()) {
				smsSendLogic.sendAperiodicPaySuccCert(message);
			}
			// 密码券激活发送短信
		} else if (message.isActivatedOrdMsg()) {
			log.info("创建期票使用通知短信,orderId:"+message.getObjectId());
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(message
					.getObjectId());
			if(ordOrder != null && ordOrder.IsAperiodic() && ordOrder.isPaymentSucc() && ordOrder.isApprovePass()) {
				smsSendLogic.sendAperiodicActivateSms(message);
				//激活后才发送点评短信
				insertCommentSms(message.getObjectId());
			}
		} else if (message.isCancelActivatedOrdMsg()) {
			log.info("供应商取消预订短信,orderId:"+message.getObjectId());
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(message
					.getObjectId());
			if(ordOrder != null && ordOrder.IsAperiodic() && ordOrder.isPaymentSucc() && ordOrder.isApprovePass()) {
				smsSendLogic.sendAperiodicCancelActivateSms(message);
			}
		}
	}
	
	private void insertCommentSms(Long orderId) {
		String mobile = getContactMobile(orderId);
		MultiSmsCreator creator = new CommentPointSmsCreator(orderId, mobile);
		List<ComSms> list = creator.createSmsList();
		for (ComSms comSms : list) {
			comSmsDAO.insert(comSms);
		}
	}

	public ComSmsTemplateService getComSmsTemplateService() {
		return comSmsTemplateService;
	}

	public void setComSmsTemplateService(
			ComSmsTemplateService comSmsTemplateService) {
		this.comSmsTemplateService = comSmsTemplateService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	private String getContactMobile(Long orderId) {
		return this.orderPersonDAO.getOrdPersonMobile(orderId,
				Constant.ORD_PERSON_TYPE.CONTACT.name());
	}

	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	public void setComSmsDAO(ComSmsDAO comSmsDAO) {
		this.comSmsDAO = comSmsDAO;
	}

	public void setSmsSendLogic(SmsSendLogic smsSendLogic) {
		this.smsSendLogic = smsSendLogic;
	}
}
