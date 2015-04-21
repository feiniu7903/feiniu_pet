package com.lvmama.back.web.ord;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.SendContractEmailService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.ExcludedContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.ResponseMessage;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
/**
 * 超级订单废单
 * @author songlianjun
 *
 */
public class SuperOrderCancelAction  extends BaseAction{
	
	private static Logger logger = Logger.getLogger(SuperOrderCancelAction.class);
	private List<OrdOrder>   orderList;
	private Long orderId;
	private String cancelResson;
	protected OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private SendContractEmailService sendContractEmailService;
	/**
	 * 加载订单
	 */
	public void loadOrders(Map<String,Object> param){
		String stauts = (String)param.get("stauts");
		if (orderId==null||orderId.equals("")){
			alert("输入订单号");
			return;
		}
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderStatus orderStatusForQuery = new OrderStatus();
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		OrderContent orderContent = new OrderContent();
		//orderContent.setPaymentTarget(com.lvmama.common.vo.Constant.PAYMENT_TARGET.TOLVMAMA.name());
		OrderIdentity orderIdentity = new OrderIdentity();
		ExcludedContent orderExcludedContent = new ExcludedContent();
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(1);
		pageIndex.setEndIndex(10);
		orderIdentity.setOrderId(orderId);
		if("normal".equals(stauts)){
			orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		}
		if("cancel".equals(stauts)){
			orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		}
		
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setPageIndex(pageIndex);
		
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setContent(orderContent);
		compositeQuery.setStatus(orderStatusForQuery);
		compositeQuery.setExcludedContent(orderExcludedContent);
		compositeQuery.setPageIndex(pageIndex);
		orderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
	}
	/**
	 * 超级废单
	 */
	public void cancelOrder() {
		
		boolean isCanceled = orderServiceProxy.cancelOrder(orderId, this.getCancelResson(), this.getOperatorName());
		if(isCanceled){
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			sendContractEmailService.sendCancelContractSms(order);
		}
		logger.info("super canel order: orderId=" + orderId + " cancelReason="
				+ getCancelResson() + " operator=" + this.getOperatorName());
		this.refreshParent("search");
		this.closeWindow();
		
	};
	
	public void restoreOrder(Map<String,Object> param) throws InterruptedException{
		final Long ordId = (Long)param.get("orderId");
		final String name = this.getOperatorName();
		Messagebox.show("确定要恢复吗", "提示信息", Messagebox.YES | Messagebox.NO,
				Messagebox.ERROR, new EventListener() {
					public void onEvent(Event evt) {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							ResponseMessage responseMessage = orderServiceProxy.restoreOrder(ordId,name);
							alert(responseMessage.getResponse());
							refreshComponent("search");
							break; // the Yes button is pressed
						case Messagebox.NO:
							break; // the No button is pressed
						}
					}
				});
		
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCancelResson() {
		return cancelResson;
	}

	public void setCancelResson(String cancelResson) {
		this.cancelResson = cancelResson;
	}

	public List getOrderList() {
		return orderList;
	}
	public SendContractEmailService getSendContractEmailService() {
		return sendContractEmailService;
	}
	public void setSendContractEmailService(
			SendContractEmailService sendContractEmailService) {
		this.sendContractEmailService = sendContractEmailService;
	}
	
}
