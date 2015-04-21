package com.lvmama.back.sweb.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.job.PriceUpdateJob;
import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.SendContractEmailService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.spring.SpringBeanProxy;

public class PriceUpdateJobTest extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private SendContractEmailService sendContractEmailService;
	

	
	@Action("/test/priceUpdateJob")
	public void initOrderInfo() throws Exception {
		String orderIds = this.getRequest().getParameter("orderIds");
		String cancelReason = this.getRequest().getParameter("cancelReason");
		if (StringUtils.isBlank(orderIds) ||  StringUtils.isBlank(cancelReason) || StringUtils.isNotBlank(getOperatorName()))  {
			this.getResponse().getWriter().write("请输入取消原因,订单号或者先登录");
			return;
		}
		java.util.StringTokenizer st = new java.util.StringTokenizer(orderIds, ",");
		while (st.hasMoreTokens()) {
			Long orderId = Long.parseLong(st.nextToken());
			boolean isCanceled = orderServiceProxy.cancelOrder(orderId, cancelReason, this.getOperatorName());
			if(isCanceled){
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
				sendContractEmailService.sendCancelContractSms(order);
			}
		}
		this.getResponse().getWriter().write("搞定!");
		
	}
	


	public SendContractEmailService getSendContractEmailService() {
		return sendContractEmailService;
	}
	public void setSendContractEmailService(
			SendContractEmailService sendContractEmailService) {
		this.sendContractEmailService = sendContractEmailService;
	}
	
	

}
