package com.lvmama.back.sweb.ord;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ProceedToursService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.vo.Constant;

public class OrdProceedToursAction extends BaseAction {
	private static final long serialVersionUID = 537702329260034742L;
	
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;	
	/**
	 * 订单集合列表
	 */
	private List<OrdOrder> ordersList = new ArrayList<OrdOrder>();
	
	private ProceedToursService proceedToursService;
	
	
	
	/**
	 * 读取团取消的所有订单
	 * @return
	 */
	@Action(value = ("/ord/order_proceedTours_list"), results = @Result(name = "order_proceedTours_list", location = "/WEB-INF/pages/back/ord/order_proceedTours_list.jsp"))
	public String doCancelGroupOrders(){
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderStatus orderStatusForQuery = new OrderStatus();
//		List<Tours> toursRelate = new ArrayList<Tours>();
		PageIndex pageIndex = new PageIndex();		
		Long totalRecords = 0L;
		List<SortTypeEnum> sortTypeList = new ArrayList<SortTypeEnum>();
		sortTypeList.add(SortTypeEnum.ORDER_ID_DESC);
		
//		Map<String,Object> parameters = new HashMap<String,Object>();
//		parameters.put("status", Constant.PROCEED_TOURS_STATUS.CANCEL.name());
//		parameters.put("valid", "Y");
//		List<ProceedTours> proceedTours = proceedToursService.query(parameters);
		
		pagination = initPagination();
		
		List<String> groups=new java.util.Vector<String>();
		groups.add(Constant.TRAVEL_GROUP_STATUS.CANCEL.name());
		
			//订单状态为正常
			orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
			//订单资源已审核
			orderStatusForQuery.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
			//订单已支付
			orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
	
			compositeQuery.setStatus(orderStatusForQuery);
			compositeQuery.setTypeList(sortTypeList);
//			compositeQuery.setToursRelate(toursRelate);
			
			compositeQuery.setTravelGroupStatus(groups);
			
			totalRecords = orderServiceProxy
					.compositeQueryOrdOrderCount(compositeQuery);
			
			pagination.setTotalRecords(totalRecords);
			
			pageIndex.setBeginIndex(pagination.getFirstRow());
			pageIndex.setEndIndex(pagination.getLastRow());
			pagination
					.setActionUrl("/ord/order_proceedTours_list.do?");
	
			compositeQuery.setPageIndex(pageIndex);
	
			ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		
		

		return "order_proceedTours_list";
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<OrdOrder> ordersList) {
		this.ordersList = ordersList;
	}

	public ProceedToursService getProceedToursService() {
		return proceedToursService;
	}

	public void setProceedToursService(ProceedToursService proceedToursService) {
		this.proceedToursService = proceedToursService;
	}
	
	
}
