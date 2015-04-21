package com.lvmama.back.sweb.offlinepay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author liwenzhan
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "unpayOrderList", location = "/WEB-INF/pages/back/offlinePay/searchOrder.jsp")
})
public class SearchOrderAction extends BackBaseAction {

	
	
	/**
	 * 支付方式.
	 */
	private String payStatus;
	
	private String orderId;
	
	private String userName;
	
	/**
	 * 开始日期.
	 */
	private Date beginTime;
	/**
	 * 结束日期.
	 */
	private Date endTime;
	
	/**
	 * 订单服务
	 */
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private Map<String, Object> queryPars=new HashMap<String, Object>();
	private List<OrdOrder> orderList = new ArrayList<OrdOrder>();
	/**
	 * 
	 * @return
	 */
	@Action(value = "/offlinePay/doUnpayOrder")
	public String doUnpayOrder(){
		return "unpayOrderList";
	}

	/**
	 * 
	 * @return
	 */
	@Action(value = "/offlinePay/doQueryOrder")
	public String doQueryOrder(){
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderStatus orderStatusForQuery = new OrderStatus();
		PageIndex pageIndex = new PageIndex();
		//orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		if(!"ONLINE".equalsIgnoreCase(payStatus)) {
			   orderStatusForQuery.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
		}
		compositeQuery.setStatus(orderStatusForQuery);
		if (orderId!=null&&!orderId.equals(""))
		compositeQuery.getOrderIdentity().setOrderId(Long.parseLong(orderId.trim()));
		if (queryPars.get("userId")!=null&&!queryPars.get("userId").equals(""))
			compositeQuery.getOrderIdentity().setUserId((String)queryPars.get("userId"));
		if (beginTime!=null)
			compositeQuery.getOrderTimeRange().setCreateTimeStart(beginTime);
		if (endTime!=null)
			compositeQuery.getOrderTimeRange().setCreateTimeEnd(endTime);
		pageIndex.setBeginIndex(1);
		pageIndex.setEndIndex(20);
		compositeQuery.setPageIndex(pageIndex);
		compositeQuery.getExcludedContent().setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER);
		//compositeQuery.getExcludedContent().setPaymentStatus(Constant.PAYMENT_STATUS.PAYED);
		orderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		return "unpayOrderList";
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Map<String, Object> getQueryPars() {
		return queryPars;
	}

	public void setQueryPars(Map<String, Object> queryPars) {
		this.queryPars = queryPars;
	}

	public List<OrdOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrdOrder> orderList) {
		this.orderList = orderList;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

}
