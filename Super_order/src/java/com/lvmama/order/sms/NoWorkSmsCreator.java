package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.vo.Constant;

/**
 * ON_WORK短信模板内容生成器.<br>
 * 说明：非工作时段短信<br>
 * 与订单有关的短信模板内容生成器需要继承AbstractOrderSmsCreator抽象类
 * 与订单无关的短信模板内容生成器需要继承AbstractSmsCreator抽象类
 * 
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.prd.po.ProdChannelSms
 * @see com.lvmama.vo.Constant
 */
public class NoWorkSmsCreator extends AbstractOrderSmsCreator implements
		SingleSmsCreator {
	/**
	 * 构造函数.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param mobile
	 *            手机号码
	 */
	public NoWorkSmsCreator(final Long orderId, final String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
	}

	/**
	 * 填充短信模板中的变量.<br>
	 * key为变量名称，value为变量值
	 * 
	 * @return Map<String, Object>
	 */
	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		return data;
	}

	/**
	 * 获取模板.
	 * 
	 * @return {@link ProdChannelSms}
	 */
	@Override
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(
				order.getChannel(), Constant.SMS_TEMPLATE.NO_WORK.name());
	}
}
