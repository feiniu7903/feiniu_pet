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
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author shihui
 * 
 *         密码券取消激活成功发送短信
 * */
public class AperiodicCancelActivateSmsCreator extends AbstractOrderSmsCreator
		implements SingleSmsCreator {
	private static final Log log = LogFactory
			.getLog(AperiodicActivateSmsCreator.class);
	private OrdOrder order;
	private String[]orderItemMetaIds;
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy
			.getBean("orderServiceProxy");

	public AperiodicCancelActivateSmsCreator(Long orderId, String mobile, String[] orderItemMetaIds) {
		this.objectId = orderId;
		this.mobile = mobile;
		this.orderItemMetaIds = orderItemMetaIds;
		// 要拿到订单里相关的子项及子子项的全部信息
		order = orderServiceProxy.queryOrdOrderByOrderId(objectId);
	}

	@Override
	Map<String, Object> getContentData() {
		Long metaId = null;
		try{
			metaId = Long.parseLong(orderItemMetaIds[0]);
		}catch(Exception e) {
			return null;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		OrderItemMetaAperiodicService orderItemMetaAperiodicService = SpringBeanProxy.getBean(OrderItemMetaAperiodicService.class, "orderItemMetaAperiodicService");
		OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicService.selectOrderAperiodicByOrderItemMetaId(metaId);
		if(aperiodic == null) {
			return null;
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
		data.put("passwordCertificate", aperiodic.getPasswordCertificate());
		data.put("orderId", objectId);
		return data;
	}

	@Override
	ProdChannelSms getSmsTemplate() {
		log.info("OrderId:" + objectId + ",Order Channel:" + order.getChannel()
				+ ",APER_SUPPLIER_CANCEL");
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(
				order.getChannel(),
				Constant.SMS_TEMPLATE.APER_SUPPLIER_CANCEL.name());
	}
}
