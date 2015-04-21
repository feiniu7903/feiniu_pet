package com.lvmama.order.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdProductDAO;

public class BeforePerformSmsCreator extends AbstractOrderSmsCreator implements MultiSmsCreator {
	
	private String content;
	private OrdOrder order;
	private PerformTargetService performTargetService = (PerformTargetService)SpringBeanProxy.getBean("performTargetService");
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	
	public BeforePerformSmsCreator(Long orderId, String mobile) {
		this.objectId = orderId;
		this.mobile = mobile;
		order = orderServiceProxy.queryOrdOrderByOrderId(objectId);
	}
	
	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orderId", objectId);
		data.put("content", content);
		data.put("orderChannel", orderChannel);
		return data;
	}
	
	public List<ComSms> createSmsList() {
		List<ComSms> smsList = new ArrayList<ComSms>();
		ProdProductDAO prodProductDAO = (ProdProductDAO)SpringBeanProxy.getBean("prodProductDAO");
		List<OrdOrderItemProd> items = order.getOrdOrderItemProds();
		for (OrdOrderItemProd ordOrderItemProd : items) {
			if(order.hasSelfPack() && hasPssport(ordOrderItemProd)){
				//如果该订单是超级自由行订单,并且该子项中 关联的采购产品 是二维码产品
				continue;
			}
			
			if (ordOrderItemProd.isNeedSendSms()) {
				ProdProduct product = prodProductDAO.selectByPrimaryKey(ordOrderItemProd.getProductId());
				content = product.getSmsContent();
				ComSms comSms = super.createSingleSms();
				if(comSms!=null){
					comSms.setSendTime(DateUtil.getDateBeforeHours(ordOrderItemProd.getVisitTime(), 5));
					smsList.add(comSms);
				}
			}
		}
		return smsList;
	}
	
	/**判断该订单中的该子项是否是二维码产品
	 * @param ordOrderItemProd
	 * @return
	 */
	private boolean hasPssport(OrdOrderItemProd ordOrderItemProd){
		for (OrdOrderItemMeta itemMeta : ordOrderItemProd.getOrdOrderItemMetas()) {
			List<SupPerformTarget> targets = this.performTargetService.findSuperSupPerformTargetByMetaProductId(itemMeta.getMetaProductId());
			for (SupPerformTarget supPerformTarget : targets) {
				if (Constant.CCERT_TYPE.DIMENSION.name().equals(supPerformTarget.getCertificateType())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	@Override
	ProdChannelSms getSmsTemplate() {
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.BEFORE_PERFORM.name());
	}

}
