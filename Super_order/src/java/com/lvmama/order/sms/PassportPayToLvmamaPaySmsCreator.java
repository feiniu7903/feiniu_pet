package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 二维码订单，二维码在线支付支付成功短信
 * @author chenkeke
 *
 */
public class PassportPayToLvmamaPaySmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator{
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private OrdOrder order;
	private String visitDate;
	/**
	 * 二维码订单，二维码在线支付支付成功短信
	 * @param orderId
	 * @param mobile
	 */
	public PassportPayToLvmamaPaySmsCreator(Long orderId, String mobile){
		this.objectId = orderId;
		this.mobile = mobile;
		this.order = orderServiceProxy.queryOrdOrderByOrderId(this.objectId);
	}
	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		String format="yyyy-MM-dd";
		visitDate = DateUtil.getFormatDate(order.getVisitTime(), format);
		data.put("orderId", this.objectId);
		data.put("time", order.getLastCancelStr());
		data.put("visitDate", visitDate);
		return data;
	}
	@Override
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.PASSPORT_PAY_TO_LVMAMA_PAY.name());
	}

}
