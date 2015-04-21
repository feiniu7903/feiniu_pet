package com.lvmama.order.sms;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.vo.Constant;
class AfterPerformSmsCreator extends AbstractOrderSmsCreator {
	
	public AfterPerformSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.AFTER_PERFORM.name());
	}
}
