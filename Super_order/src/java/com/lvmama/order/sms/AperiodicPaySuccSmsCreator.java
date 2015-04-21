package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author shihui
 * 
 *         密码券订单支付成功后发送短信
 * */
public class AperiodicPaySuccSmsCreator extends AbstractOrderSmsCreator
		implements SingleSmsCreator {
	private static final Log log = LogFactory
			.getLog(AperiodicPaySuccSmsCreator.class);
	private OrdOrder order;
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy
			.getBean("orderServiceProxy");
	private OrdTmallMapService ordTmallMapService = (OrdTmallMapService) SpringBeanProxy
			.getBean("ordTmallMapService");

	public AperiodicPaySuccSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
		// 要拿到订单里相关的子项及子子项的全部信息
		order = orderServiceProxy.queryOrdOrderByOrderId(objectId);
	}

	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		ProdProductService prodProductService = SpringBeanProxy.getBean(ProdProductService.class, "prodProductService");
		ProdProduct product = prodProductService.getProdProduct(order.getMainProduct().getProductId());
		data.put("content", "," + product.getSmsContent());
		OrderItemMetaAperiodicService orderItemMetaAperiodicService = SpringBeanProxy.getBean(OrderItemMetaAperiodicService.class, "orderItemMetaAperiodicService");
		OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicService.selectFirstOrderAperiodicByOrderId(objectId);
		if(aperiodic != null) {
			data.put("passwordCertificate", aperiodic.getPasswordCertificate());
		}
		String validContent = "";
		String dateFormat = "yyyy-MM-dd";
		for(OrdOrderItemProd ooip : order.getOrdOrderItemProds()) {
			validContent += ooip.getZhBranchName()
					+ ":"
					+ DateUtil.getFormatDate(ooip.getValidBeginTime(),
							dateFormat)
					+ "至"
					+ DateUtil
							.getFormatDate(ooip.getValidEndTime(), dateFormat);
			if(StringUtils.isNotEmpty(ooip.getInvalidDateMemo())) {
				validContent += "(" + ooip.getInvalidDateMemo() + ")";
			}
			validContent += "、";
		}
		data.put("validContent", validContent.substring(0, validContent.length()-1));
		data.put("aheadBookingDays", product.getAheadBookingDays());
		//淘宝需要取产品名称和数量
		if(StringUtils.isNotEmpty(order.getChannel()) && Constant.CHANNEL.TAOBAL.name().equalsIgnoreCase(order.getChannel())) {
			data.put("productName", product.getProductName());
			OrdTmallMap map = ordTmallMapService.selectByLvOrderId(objectId);
			if(map!=null) {
				data.put("buyNum", map.getBuyNum());
			}
		}
		return data;
	}

	@Override
	ProdChannelSms getSmsTemplate() {
		//门票
		if (order.isTicket()) {
			log.info("OrderId:" + objectId + ",Order Channel:" + order.getChannel()
					+ ",TICKET_APER_PAYMENT_SUCC");
			return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(
					order.getChannel(),
					Constant.SMS_TEMPLATE.TICKET_APER_PAYMENT_SUCC.name());
		//酒店或线路
		} else if (order.isHotel() || order.isRoute()) {
			log.info("OrderId:" + objectId + ",Order Channel:" + order.getChannel()
					+ ",HOTEL_ROUTE_APER_PAYMENT_SUCC");
			return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(
					order.getChannel(),
					Constant.SMS_TEMPLATE.HOTEL_ROUTE_APER_PAYMENT_SUCC.name());
		
		}
		return null;
	}
}
