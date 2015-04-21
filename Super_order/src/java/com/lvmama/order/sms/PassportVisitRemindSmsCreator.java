package com.lvmama.order.sms;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

/**
 * 二维码游玩提醒
 * @author chenkeke
 *
 */
public class PassportVisitRemindSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator{
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private OrderItemProdDAO orderItemProdDAO = (OrderItemProdDAO)SpringBeanProxy.getBean("orderItemProdDAO");
	private ProdProductDAO prodProductDAO = (ProdProductDAO)SpringBeanProxy.getBean("prodProductDAO");
	private OrdOrder order;
	private String content;
	private boolean sendSms;
	private boolean timingFlag;
	/**
	 * 二维码游玩提醒
	 * @param orderId
	 * @param mobile
	 */
	public PassportVisitRemindSmsCreator(Long orderId, String mobile,boolean timingFlag){
		this.objectId = orderId;
		this.mobile = mobile;
		this.timingFlag = timingFlag;
		order = orderServiceProxy.queryOrdOrderByOrderId(this.objectId);
	}

	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		List<OrdOrderItemProd> itemProds=orderItemProdDAO.selectByOrderId(objectId);
		Set<Long> productIdSet=new HashSet<Long>();
		for(OrdOrderItemProd itemProd:itemProds){
			if(itemProd.isNeedSendSms()){
				productIdSet.add(itemProd.getProductId());
			}
		}
		for (Long productId : productIdSet) {
			ProdProduct product = prodProductDAO.selectByPrimaryKey(productId);
			if (content==null) {
				content = product.getSmsContent();
			}else{
				content = content + "," + product.getSmsContent();
			}
			if(sendSms==false){
				sendSms = "true".equals(product.getSendSms());
			}
		}
		data.put("content", content);
		return data;
		
	}
	@Override
	public ComSms createSingleSms() {
		ComSms comSms = super.createSingleSms();
		if(comSms!=null && timingFlag){
			//游玩前一天17点10秒发送
			Date sendTime = DateUtil.toDate(DateUtil.formatDate(DateUtil.dsDay_Date(order.getVisitTime(), -1), "yyyyMMdd")+"170010", "yyyyMMddHHmmss");
			comSms.setSendTime(sendTime);
		}
		//是否发送短信：	是 否  内容为空时也不发
		if(order==null || sendSms==false || content==null || "".equals(content.trim())){
			return null;
		}
		return comSms;
	}
	@Override
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.PASSPORT_VISIT_REMIND.name());
	}

}
