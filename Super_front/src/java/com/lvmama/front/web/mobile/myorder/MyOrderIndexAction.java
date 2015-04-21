package com.lvmama.front.web.mobile.myorder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

@Results( {
		@Result(location = "/WEB-INF/pages/mobile/myorder/index.ftl", type = "freemarker"),
		@Result(name = "ERROR", location = "/404.jsp", type = "dispatcher"),
		@Result(name = "list", location = "/WEB-INF/pages/mobile/myorder/list.ftl", type = "freemarker"),
		@Result(name = "cancel_success", location = "/WEB-INF/pages/mobile/myorder/cancel_success.ftl", type = "freemarker") })
/**
 * 我的订单操作类
 * 
 * @author shihui
 */
public class MyOrderIndexAction extends BaseAction {
	private static final long serialVersionUID = 9119413133335836267L;

	/**
	 * 订单服务
	 */
	private OrderService orderServiceProxy;
	/**
	 * 订单列表
	 */
	private List<OrdOrder> ordersList;
	/**
	 * 订单Id
	 */
	private Long orderId;
	/**
	 * 订单详情
	 */
	private OrdOrder ordOrder;
	/**
	 * 页面标题
	 */
	private String title;
	/**
	 * 跳向页面
	 * */
	private boolean toPage = true;
	/**
	 * 是否线上支付
	 */
	private boolean online = true;
	
	private String page = "1";
	/**每页显示数量*/
	private final int pageSize = 12;
	Page<?> pageConfig;
	/**
	 * 是否取出数量
	 * */
	private boolean isGetCount = false;
	private Map<String, String> countMap;

	
	
	@Action("/m/myorder/list")
	public String execute() {
		if(getUserId()==null){
			try {
				this.getResponse().sendRedirect("http://login.lvmama.com/nsso/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
		isGetCount = true;
		countMap = new HashMap<String, String>();
		countMap.put("unpaidOrders", unpaidOrders());
		countMap.put("approvingOrders", approvingOrders());
		countMap.put("paidOrders", paidOrders());
		return SUCCESS;
		}
	}

	/**
	 * 待支付订单
	 */
	@Action("/m/myorder/unpaidOrders")
	public String unpaidOrders() {
		if(getUserId()==null){
			try {
				this.getResponse().sendRedirect("http://login.lvmama.com/nsso/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
		OrderStatus status = new OrderStatus();
		status.setPaymentStatus(Constant.PAYMENT_STATUS.UNPAY.name());
		status.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		//已经审核通过或者不需要资源确认
		status.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED
				.name());
		status.setOrderResourceStatus(Constant.ORDER_RESOURCE_STATUS.AMPLE.name());
		OrderContent content = new OrderContent();
		//线上支付
		if (online) {
			content.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		} else {//支付给供应商
			content.setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
		}
		if(isGetCount){
			CompositeQuery compositeQuery = getCompositeQuery(status, content);
			Long num1 = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
			content.setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
			compositeQuery = getCompositeQuery(status, content);
			Long num2 = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
			return num1+num2+"";
		}else {
			queryOrders(status, content, "/m/myorder/unpaidOrders.do?");
			title = "unpaid";
			return "list";
		}
		}
	}

	/**
	 * 审核中订单
	 */
	@Action("/m/myorder/approvingOrders")
	public String approvingOrders() {
		if(getUserId()==null){
			try {
				this.getResponse().sendRedirect("http://login.lvmama.com/nsso/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
		OrderStatus status = new OrderStatus();
		status.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.UNVERIFIED
				.name());
		status.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		if(isGetCount){
			CompositeQuery compositeQuery = getCompositeQuery(status, null);
			return orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery)+"";
		}else {
			queryOrders(status, null, "/m/myorder/approvingOrders.do?");
			title = "approving";
			return "list";
		}}
	}

	/**
	 * 已付款订单
	 */
	@Action("/m/myorder/paidOrders")
	public String paidOrders() {
		if(getUserId()==null){
			try {
				this.getResponse().sendRedirect("http://login.lvmama.com/nsso/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
		OrderStatus status = new OrderStatus();
		status.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		status.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		if(isGetCount){
			CompositeQuery compositeQuery = getCompositeQuery(status, null);
			return orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery)+"";
		}else {
			queryOrders(status, null, "/m/myorder/paidOrders.do?");
			title = "paid";
			return "list";
		}}
	}

	/**
	 * 已完成订单
	 */
	@Action("/m/myorder/finishedOrders")
	public String finishedOrders() {
		if(getUserId()==null){
			try {
				this.getResponse().sendRedirect("http://login.lvmama.com/nsso/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
		OrderStatus status = new OrderStatus();
		status.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		status.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
		queryOrders(status, null, "/m/myorder/finishedOrders.do?");
		title = "finished";
		return "list";
		}
	}

	/**
	 * 已取消的订单
	 */
	@Action("/m/myorder/canceledOrders")
	public String canceledOrders() {
		if(getUserId()==null){
			try {
				this.getResponse().sendRedirect("http://login.lvmama.com/nsso/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
		OrderStatus status = new OrderStatus();
		status.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		queryOrders(status, null, "/m/myorder/canceledOrders.do?");
		title = "canceled";
		return "list";
		}
	}
	

	/**
	 * 获取综合查询条件
	 * */
	public CompositeQuery getCompositeQuery(OrderStatus status, OrderContent content) {
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.setStatus(status);
		if(content != null){
			compositeQuery.setContent(content);
		}
		OrderIdentity orderIdentity = new OrderIdentity();
		
		orderIdentity.setUserId(getUserId());
		compositeQuery.setOrderIdentity(orderIdentity);
		return compositeQuery;
	}
	
	/**
	 * 根据不同状态查询订单
	 */
	public void queryOrders(OrderStatus status, OrderContent content, String url) {
		CompositeQuery compositeQuery = getCompositeQuery(status, content);
		Long count= orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
		pageConfig = new Page(count, pageSize, Long.valueOf(this.page));
		pageConfig.setUrl(url);
		int beginIndex = Integer.parseInt((pageConfig.getStartRows()+1)+""); 
		int endIndex = Integer.parseInt((pageConfig.getPageSize())+"");
		compositeQuery.getPageIndex().setBeginIndex(beginIndex);
		compositeQuery.getPageIndex().setEndIndex(endIndex);
		compositeQuery.getQueryFlag().setQuerySupplier(false);
		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		
	}

	/**
	 * 订单详情
	 */
	@Action(value = "/m/myorder/orderDetail", results = @Result(name = "detail", location = "/WEB-INF/pages/mobile/myorder/detail.ftl"))
	public String orderDetail() {
		if(getUserId()==null){
			try {
				this.getResponse().sendRedirect("http://login.lvmama.com/nsso/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
		ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		return "detail";
		}
	}

	/**
	 * 支付订单
	 */
	@Action(value = "/m/myorder/payOrder", results = @Result(name = "pay", location = "/WEB-INF/pages/mobile/myorder/pay.ftl"))
	public String payOrder() {
		// 跳向支付页面
		if (toPage) {
			ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			return "pay";
		} else {
			// 支付订单方法调用
			return unpaidOrders();
		}
	}

	/**
	 * 取消订单
	 */
	@Action(value = "/m/myorder/cancelOrder", results = @Result(name = "cancel", location = "/WEB-INF/pages/mobile/myorder/cancel.ftl"))
	public String cancelOrder() {
		if(getUserId()==null){
			try {
				this.getResponse().sendRedirect("http://login.lvmama.com/nsso/login");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
		if (toPage) {
			ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			return "cancel";
		} else {
			// 取消订单方法调用
			orderServiceProxy.cancelOrder(orderId, "30", getUserId());
			return "cancel_success";
		}
		}
	}

	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<OrdOrder> ordersList) {
		this.ordersList = ordersList;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrdOrder getOrdOrder() {
		return ordOrder;
	}

	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setToPage(boolean toPage) {
		this.toPage = toPage;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isOnline() {
		return online;
	}

	public Page<?> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(Page<?> pageConfig) {
		this.pageConfig = pageConfig;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	public Map<String, String> getCountMap() {
		return countMap;
	}
}