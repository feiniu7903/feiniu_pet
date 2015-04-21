package com.lvmama.order.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.prd.dao.ProdProductDAO;

public class NormalPaySuccSmsCreator extends AbstractOrderSmsCreator implements MultiSmsCreator {
	private static final Log log = LogFactory.getLog(NormalPaySuccSmsCreator.class);
	private String content;
	private String visitDate;
	private Date sendTime;
	private String latestUseTime;
	private OrdOrder order;
	private OrderService orderServiceProxy =(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
//	private MetaPerformDAO metaPerformDAO = (MetaPerformDAO)SpringBeanProxy.getBean("metaPerformDAO");
	public NormalPaySuccSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
//		order = orderDAO.selectByPrimaryKey(objectId);
		//要拿到订单里相关的子项及子子项的全部信息
		order = orderServiceProxy.queryOrdOrderByOrderId(objectId);
		
	}

	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		//设置最晚取消时间，如果没有最晚取消时间,就不给此取消的提示
		if (!order.hasSelfPack()) {
			data.put("time", order.getLastCancelStr());
		}else{
			data.put("time", "");
		}
		if(order.hasTodayOrder()){
			StringBuffer sb =new StringBuffer();
			sb.append("最早换票时间：");
			sb.append(DateUtil.getFormatDate(order.getVisitTime(), "HH:mm"));
			sb.append(",最晚换票时间:");
			sb.append(latestUseTime);
			latestUseTime = sb.toString();
		}
		data.put("latestUseTime",latestUseTime);
		data.put("orderId", objectId);
		data.put("visitDate", visitDate);
		data.put("content", content);
		if(order.hasSelfPack()){
			data.put("sendTime", sendTime);
		}
		return data;
	}

	public List<ComSms> createSmsList() {
		List<ComSms> smsList = new ArrayList<ComSms>();
		OrderItemProdDAO orderItemProdDAO = (OrderItemProdDAO)SpringBeanProxy.getBean("orderItemProdDAO");
		ProdProductDAO prodProductDAO = (ProdProductDAO)SpringBeanProxy.getBean("prodProductDAO");
		List<OrdOrderItemProd> items = orderItemProdDAO.selectByOrderId(objectId);
		for (OrdOrderItemProd ordOrderItemProd : items) {
			if(order.hasSelfPack() && ordOrderItemProd.hasDefault()){
				//如果该订单是超级自由行订单,默认类别不发短信
				continue;
			}
			 if (ordOrderItemProd.isNeedSendSms()) {
				ProdProduct product = prodProductDAO.selectByPrimaryKey(ordOrderItemProd.getProductId());
				content = product.getSmsContent();
				if(order.hasSelfPack()){
					visitDate = DateUtil.getFormatDate(ordOrderItemProd.getVisitTime(), "yyyy-MM-dd");
				}else{
					String format="yyyy-MM-dd";
					if(order.hasTodayOrder()){
						latestUseTime = DateUtil.getFormatDate(order.getLatestUseTime(), "HH:mm");
					}
					visitDate = DateUtil.getFormatDate(order.getVisitTime(), format);
				}
				sendTime = ordOrderItemProd.getVisitTime();
				smsList.add(super.createSingleSms());
			}
		}
		return smsList;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		log.info("OrderId:"+objectId+",Order Channel:"+order.getChannel()+",NORM_PAYMENT_SUCC");
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.NORM_PAYMENT_SUCC.name());
	}

}
