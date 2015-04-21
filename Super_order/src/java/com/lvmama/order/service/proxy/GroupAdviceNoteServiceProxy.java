/**
 * 
 */
package com.lvmama.order.service.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.service.GroupAdviceNoteService;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.bee.service.ord.IGroupAdviceNoteService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.po.pub.ComMessageReceivers;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.utils.Configuration;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 * 
 */
public class GroupAdviceNoteServiceProxy implements IGroupAdviceNoteService {

	private OrderService orderServiceProxy;
	private GroupAdviceNoteService groupAdviceNoteService;
	private TopicMessageProducer orderMessageProducer;
	private ComMessageService comMessageService;
	private ComLogService comLogService;
	private SmsService smsService;
	private static final Log logger = org.apache.commons.logging.LogFactory.getLog(GroupAdviceNoteServiceProxy.class);

	// 发送出团通知书邮件、短信、提醒
	/* (non-Javadoc)
	 * @see com.lvmama.order.service.proxy.IGroupAdviceNoteService#sendGroupAdviceNote(java.lang.Long, java.lang.String)
	 */
	@Override
	public ResultHandleT<Boolean> sendGroupAdviceNote(Long orderId,String operatorName) {
		ResultHandleT<Boolean> handleT = new ResultHandleT<Boolean>();
		handleT.setReturnContent(false);
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		OrdOrderRoute orderRoute = groupAdviceNoteService
				.getOrderRouteByOrderId(orderId);
		String status = orderRoute.getGroupWordStatus();
		String noteTemplate = "";
		String remindTemplate = "";
		if (Constant.GROUP_ADVICE_STATE.NEEDSEND.name().equals(status)
				|| Constant.GROUP_ADVICE_STATE.UPLOADED_NOT_SENT.name().equals(
						status)) { // 首次
			noteTemplate = "groupAdviceNote.messageTemplateForNew";
			remindTemplate = "groupAdviceNote.remindTemplateForNew";
		} else if (Constant.GROUP_ADVICE_STATE.SENT_NOTICE.name()
				.equals(status)
				|| Constant.GROUP_ADVICE_STATE.MODIFY_NOTICE.name().equals(
						status)
				|| Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name().equals(
						status)
				|| Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name().equals(
						status)) { // 重发
			noteTemplate = "groupAdviceNote.messageTemplateForUpdate";
			remindTemplate = "groupAdviceNote.remindTemplateForUpdate";
		} else {
			logger.error("出团通知书状态异常,无法发送,订单号:[" + orderId + "].");
			handleT.setMsg("出团通知书状态异常,无法发送,订单号:[" + orderId + "].");
			return handleT;
		}

		// 发送邮件
		orderMessageProducer
				.sendMsg(MessageFactory.newGroupAdviceNoteMailMessage(orderId,
						operatorName));

		// 发送短信
		ComSms sms = new ComSms();
		sms.setMobile(order.getContact().getMobile());
		String tpl = getPropertyValue("groupAdviceNote.properties", noteTemplate);
		Map<String, String> values = new HashMap<String, String>();
		values.put("orderId", String.valueOf(order.getOrderId()));
		values.put("mail", order.getContact().getEmail());
		sms.setContent(StringUtil.buildStringByTemplate(tpl, "{", "}", values));
		sms.setObjectId(orderId);
		sms.setObjectType("ORD_ORDER");
		smsService.sendSms(sms);

		// 发送系统提醒
		Map<String, String> map = new HashMap<String, String>();
		map.put("skipResults", "0");
		map.put("maxResults", "1");
		map.put("messageCode", "GROUP_ADVICE_NOTE");
		ComMessageReceivers receivers = comMessageService
				.queryComMessageReceiverByParam(map).get(0);
		String[] userList = receivers.getMessageReceivers().split(",");
		for (String user : userList) {
			ComMessage comMessage = new ComMessage();
			comMessage.setReceiver(user);
			comMessage.setSender(operatorName);
			tpl = getPropertyValue("groupAdviceNote.properties", remindTemplate);
			values = new HashMap<String, String>();
			values.put("orderId", String.valueOf(order.getOrderId()));
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("orderId", orderId);
			values.put("processer", order.getTakenOperator());
			comMessage.setContent(StringUtil.buildStringByTemplate(tpl, "{",
					"}", values));
			comMessage.setStatus("CREATE");
			comMessage.setCreateTime(new Date());
			comMessageService.insertComMessage(comMessage);
		}

		// 更新出团通知书状态
		String newStatus = null;
		if (Constant.GROUP_ADVICE_STATE.NEEDSEND.name().equals(status)
				|| Constant.GROUP_ADVICE_STATE.UPLOADED_NOT_SENT.name().equals(
						status)) {
			newStatus = Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name();
		} else if (Constant.GROUP_ADVICE_STATE.SENT_NOTICE.name()
				.equals(status)
				|| Constant.GROUP_ADVICE_STATE.MODIFY_NOTICE.name().equals(
						status)) {
			newStatus = Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name();
		} else if (Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name().equals(
				status)) {
			newStatus = Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name();
		} else if (Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name().equals(
				status)) {
			newStatus = Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name();
		}
		if (newStatus != null) {
			groupAdviceNoteService.updateOrderGroupWordStatus(
					order.getOrderId(), newStatus);
			comLogService.insert(
					"ORD_ORDER",
					null,
					order.getOrderId(),
					operatorName,
					"GROUP_ADVICE_NOTE",
					"重发出团通知书",
					"发送前状态：" + Constant.GROUP_ADVICE_STATE.getCnName(status)
							+ ",发送成功后状态："
							+ Constant.GROUP_ADVICE_STATE.getCnName(newStatus),
					null);
			if (Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name().equals(
					newStatus)
					|| Constant.GROUP_ADVICE_STATE.SENT_NOTICE.name().equals(
							newStatus)) {
				handleT.setReturnContent(true);//通知后续操作人处理
			}
			// 出团通知书状态为“已发送未通知”“已修改未通知”即可发系统工单给客服
			if (Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name().equals(
					newStatus)
					|| Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name()
							.equals(newStatus)) {
				orderMessageProducer.sendMsg(MessageFactory
						.newOrderGroupWordStatus(orderId));
			}
		}
		return handleT;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setGroupAdviceNoteService(
			GroupAdviceNoteService groupAdviceNoteService) {
		this.groupAdviceNoteService = groupAdviceNoteService;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	public String getPropertyValue(String propertyName,String key){
		return Configuration.getConfiguration().getPropertyValue(propertyName, key);
	}
}
