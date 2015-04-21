package com.lvmama.back.message;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.service.visa.VisaApprovalService;
import com.lvmama.comm.vo.Constant;

public class VisaApprovalProcesser implements MessageProcesser {
	private static final Log LOG = LogFactory.getLog(VisaApprovalProcesser.class);
	@Autowired
	private OrderService orderServiceProxy;
	@Autowired
	private ProdProductService prodProductService;
	@Autowired
	private VisaApprovalService visaApprovalService;
	
	@Override
	public void process(Message message) {
		LOG.info("VisaApprovalProcesser start....");
		if ( message.isOrderPaymentMsg()){
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if (order.isPaymentSucc() && !order.isCanceled()) {
				//根据订单的主产品的签证属性来自动导入签证	
				if (order.getMainProduct().getProductType().equals(Constant.PRODUCT_TYPE.ROUTE.name())) {
					ProdProduct product = prodProductService.getProdProduct(order.getMainProduct().getProductId());
					generateVisaApproval(order, (ProdRoute) product);
				}
				
			}
		}
		
		if (message.isOrderCancelMsg()) {
			/**
			 * @TODO 作废消息的处理
			 */
		}
	}
	
	/**
	 * 产生签证审核记录
	 * @param order 订单
	 * @param meta 订单子项
	 * @param metaProductOther 销售产品
	 */
	private void generateVisaApproval(final OrdOrder order, final ProdRoute product) {
		if (null == order 
				|| null == product 
				|| StringUtils.isBlank(product.getVisaType())
				|| StringUtils.isBlank(product.getCountry())
				|| StringUtils.isBlank(product.getCity())) {
			LOG.warn("OrdOrder, OrdOrderItemProd, Country, VisaType or City is null!");
			return;
		}
		
		boolean flag = visaApprovalService.createVisaApproval(order, product.getProductName(), product.getCountry(), product.getVisaType(), product.getCity(), 20);
		if (flag) {
			debug("订单:" + order.getOrderId() + "创建签证审核资料成功!");
		} else {
			debug("订单:" + order.getOrderId() + "创建签证审核资料失败!");
		}
	}
	
	/**
	 * 打印调试信息
	 * @param message 调试信息
	 */
	private void debug(final String message) {
		if (StringUtils.isNotBlank(message) && LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}
}
