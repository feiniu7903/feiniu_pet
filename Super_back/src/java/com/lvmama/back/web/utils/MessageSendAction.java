package com.lvmama.back.web.utils;

import org.zkoss.zul.api.Listbox;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.vo.Constant;

public class MessageSendAction extends BaseAction {

	private Listbox typeListbox;
	
	private Long objectId;
	private String type;
	
	TopicMessageProducer orderMessageProducer;
	TopicMessageProducer passportMessageProducer;
	TopicMessageProducer productMessageProducer;
	TopicMessageProducer policyMessageProducer;
	
	@Override
	protected void doAfter() throws Exception {
		for (Constant.EVENT_TYPE eventType : Constant.EVENT_TYPE.values()) {
			typeListbox.appendItemApi(eventType.name(), eventType.name());
		}
		//super.doAfter();
	}

	public void send() {
		if (Constant.EVENT_TYPE.ORDER_CREATE.name().equals(type)) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderCreateMessage(objectId));
		}
		if (Constant.EVENT_TYPE.ORDER_PAYMENT.name().equals(type)) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderPaymentMessage(objectId));
		}
		if (Constant.EVENT_TYPE.ORDER_APPROVE.name().equals(type)) {
			orderMessageProducer.sendMsg(MessageFactory.newOrderApproveMessage(objectId));
		}
		if (Constant.EVENT_TYPE.PASSCODE_APPLY_SUCCESS.name().equals(type)) {
			passportMessageProducer.sendMsg(MessageFactory.newPasscodeApplySuccessMessage(objectId,null));
		}
		if (Constant.EVENT_TYPE.PRODUCT_CHANGE.name().equals(type)) {
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(objectId));
		}
	}

	public void changeType(String type) {
		this.type= type;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

}
