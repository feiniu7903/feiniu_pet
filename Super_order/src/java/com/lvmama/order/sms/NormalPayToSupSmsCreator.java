package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.prd.dao.ProdProductDAO;

public class NormalPayToSupSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator {
	
	private String content;
	private String visitDate;
	private OrdOrder order;
	private String latestUseTime="";
	
	public NormalPayToSupSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
		order = orderDAO.selectByPrimaryKey(objectId);
	}
	
	@Override
	Map<String, Object> getContentData() {
		OrderItemProdDAO orderItemProdDAO = (OrderItemProdDAO)SpringBeanProxy.getBean("orderItemProdDAO");
		ProdProductDAO prodProductDAO = (ProdProductDAO)SpringBeanProxy.getBean("prodProductDAO");
		String format="yyyy-MM-dd";
		if(order.hasTodayOrder()){
			latestUseTime = "最早换票时间："+DateUtil.getFormatDate(order.getVisitTime(), "HH:mm")+",最晚换票时间:"+DateUtil.getFormatDate(order.getLatestUseTime(),"HH:mm");
		}
		visitDate = DateUtil.getFormatDate(order.getVisitTime(), format);
		
		List<OrdOrderItemProd> items = orderItemProdDAO.selectByOrderId(objectId);
		for (OrdOrderItemProd ordOrderItemProd : items) {
			if (ordOrderItemProd.isNeedSendSms()) {
				ProdProduct product = prodProductDAO.selectByPrimaryKey(ordOrderItemProd.getProductId());
				if (content==null) {
					content = product.getSmsContent();
				}else{
					content = content + "," + product.getSmsContent();
				}
			}
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		data.put("visitDate", visitDate);
		data.put("latestUseTime", latestUseTime);
		data.put("content", content);
		return data;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.NORM_PAYTO_SUP.name());
	}

}
