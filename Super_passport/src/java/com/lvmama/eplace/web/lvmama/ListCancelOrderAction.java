package com.lvmama.eplace.web.lvmama;

import java.util.Date;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;

/**
 * 
 * @author luoyinqi
 *
 */

public class ListCancelOrderAction extends ZkBaseAction {

	private String reason;
	private Long orderId;
	private Long orderItemMetaId;
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private EPlaceService eplaceService;
	
	public void doBefore()throws Exception {
		if(!(orderId>0)){
			alert("订单号错误");
		}
	}
	
	public void cancelOrder() throws Exception{
		if(reason!=null && !"".equalsIgnoreCase(reason)){
			String operatorId = this.getSessionUser().getUserId().toString();
			orderServiceProxy.cancelOrder(orderId, reason, operatorId);
			
			PassPortLog passPortLog = new PassPortLog();
			passPortLog.setContent("通过E景通系统废单");
			passPortLog.setCreateDate(new Date());
			passPortLog.setOrderId(orderId);
			passPortLog.setOrderItemMetaId(orderItemMetaId);
			passPortLog.setPassPortUserId(this.getUser().getPassPortUserId());
			eplaceService.addPassPortLog(passPortLog);
			
			alert("废单成功");
			refreshParent("search");
			this.closeWindow();
		}else{
			alert("废单理由不能为空");
		}
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
 
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	
}
