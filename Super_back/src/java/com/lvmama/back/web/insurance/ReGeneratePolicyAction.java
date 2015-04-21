package com.lvmama.back.web.insurance;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.spring.SpringBeanProxy;

public class ReGeneratePolicyAction extends BaseAction {
	private static final long serialVersionUID = -6395394282207241560L;
	
	private String orderId;
	private MessageProcesser advancedPolicyProcesser = (MessageProcesser) SpringBeanProxy.getBean("advancedPolicyProcesser");
	
	public void submit() {
		ZkMessage.showQuestion("您确定需要重新生成订单号为" + orderId + "的保单吗?", new ZkMsgCallBack() {
			public void execute() {
				Message message = MessageFactory.newOrderPaymentMessage(Long.parseLong(orderId));
				message.setAddition(getOperatorName());
				advancedPolicyProcesser.process(message);
				ZkMessage.showInfo("订单号为" + orderId + "的保单已经重新生成!");
				refreshParent("search");
				closeWindow();
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}
	
	//setter and getter
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
