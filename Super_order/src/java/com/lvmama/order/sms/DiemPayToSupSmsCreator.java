package com.lvmama.order.sms;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.vo.Constant;

public class DiemPayToSupSmsCreator extends AbstractOrderSmsCreator {

	public DiemPayToSupSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
	}
	
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.DIEM_PAYTO_SUP.name());
	}

}
