package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.vo.Constant;

public class OnWorkSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator{

	public OnWorkSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
	}
	
	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		return data;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.ON_WORK.name());
	}

}
