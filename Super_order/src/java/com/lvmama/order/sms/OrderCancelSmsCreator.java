package com.lvmama.order.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

public class OrderCancelSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator {
	private OrdOrder order;
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService = (OrderItemMetaAperiodicService) SpringBeanProxy.getBean("orderItemMetaAperiodicService");
	public OrderCancelSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
		this.order = orderDAO.selectByPrimaryKey(objectId);
	}
	
	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		//不定期需要取密码券
		if(order.IsAperiodic() && order.isPaySucc()) {
			OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicService.selectFirstOrderAperiodicByOrderId(objectId);
			if(aperiodic != null) {
				data.put("passwordCertificate", aperiodic.getPasswordCertificate());
			}
		}
		return data;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), getSMSTemplate(order));
	}
	
	/**
	 * 根据订单取消时状态来判断短信模板
	 * @param order
	 * @return
	 */
	private String getSMSTemplate(OrdOrder order)
	{	
		Constant.SMS_TEMPLATE template=Constant.SMS_TEMPLATE.ORDER_CANCEL;//默认后台废单或用户废单
		//不定期并且支付成功的短信发自己的模板
		if(order.IsAperiodic() && order.isPaySucc()) {
			template = Constant.SMS_TEMPLATE.UNUSED_PASSWORD_CANCEL_APER;
		} else if(order.getApproveStatus().equals(Constant.ORDER_APPROVE_STATUS.RESOURCEFAIL.name()))//处理订单为资源不通过
		{
			template=Constant.SMS_TEMPLATE.ORDER_RESOURCEFAIL_CANCEL;
		}else if(isTimeout(order))//超时废单
		{
			template=Constant.SMS_TEMPLATE.ORDER_TIMEOUT_CANCEL;
		}
		return template.name();
	}
	
	/**
	 * 判断是否是超时废单
	 * @param order
	 * @return
	 */
	public boolean isTimeout(OrdOrder order)
	{
		boolean flag=false;
		if(order.isPayToLvmama())
		{
			if(StringUtils.equals(order.getApproveStatus(),Constant.ORDER_APPROVE_STATUS.VERIFIED.name())
					&&StringUtils.equals(order.getPaymentStatus(),Constant.PAYMENT_STATUS.UNPAY.name()))//先判断是否是审核通过及未支付
			{				
				if(order.getApproveTime()!=null)
				{
					Date now=new Date();
					if(order.getWaitPayment()!=null&&order.getWaitPayment()!=-1L)//根据审核时间判断是否超时
					{
						Date date=DateUtils.addMinutes(order.getApproveTime(),order.getWaitPayment().intValue());
						flag=date.before(now);
					}
					
					if(!flag&&order.getWaitPayment()==-1L&&order.getVisitTime()!=null)
					{
						//查看游玩时间是否超时
						Date date=DateUtils.addHours(order.getVisitTime(), -6);
						flag=date.before(now);
					}
				}
			}
		}
		
		return flag;
	}
}
