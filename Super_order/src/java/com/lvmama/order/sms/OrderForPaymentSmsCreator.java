/**
 * 
 */
package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.vo.Constant;

/**
 * 摧款短信
 * @author yangbin
 *
 */
public class OrderForPaymentSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator{
	
	
	private String code;
	

	/**
	 * @param objectId
	 * @param mobile
	 * @param code 回复的短信码为Y<code>XXX</code>对应是XXX的值
	 */
	public OrderForPaymentSmsCreator(Long objectId, String mobile,String code) {
		super(objectId, mobile);
		this.code = code;
	}
	
	
	

	@Override
	Map<String, Object> getContentData() {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("orderId", super.objectId);
		map.put("messageCode", code);
		return map;
	}




	@Override
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(super.objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.ORDER_FOR_PAYMENT.name());
	}

}
