package com.lvmama.back.web.product;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.spring.SpringBeanProxy;

public class DetailsToursAction extends BaseAction {
	private static final long serialVersionUID = -2896145450283330745L;
	
	private Long productId;
	private Date visitDate;
	
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	/**
	 * 订单集合列表
	 */
	private List<OrdOrder> ordersList;	
	
	
	@Override
	public void doBefore() {
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		OrderIdentity orderIdentity = new OrderIdentity();
		PageIndex pageIndex = new PageIndex();
		
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(100);
		
		if (null != productId) {
			orderIdentity.setProductid(productId);
		}
		if (null != visitDate) {
			orderTimeRange.setOrdOrderItemProdVisitTimeStart(visitDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.visitDate);
			cal.add(Calendar.DATE, 1);
			orderTimeRange.setOrdOrderItemProdVisitTimeEnd(cal.getTime());
		}
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setPageIndex(pageIndex);
		
		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		
		System.out.println("size:" + ordersList.size());
	}
	
	public String getItemProductName(List<OrdOrderItemProd> ordOrderItemProds) {
		StringBuffer buffer = new StringBuffer();
		for (OrdOrderItemProd prod : ordOrderItemProds) {
			buffer.append(prod.getProductName() + "\n");
		}
		System.out.println("111" + buffer.toString());
		return buffer.toString();
	}
	
	public String getItemProductQuantity(List<OrdOrderItemProd> ordOrderItemProds) {
		StringBuffer buffer = new StringBuffer();
		for (OrdOrderItemProd prod : ordOrderItemProds) {
			buffer.append(prod.getQuantity() + "\n");
		}
		System.out.println("2222" + buffer.toString());
		return buffer.toString();
	}	
	
	//setter and getter
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}
	
}

