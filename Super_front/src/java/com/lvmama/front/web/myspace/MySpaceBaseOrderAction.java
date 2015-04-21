/**
 * 
 */
package com.lvmama.front.web.myspace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdUserOrder;
import com.lvmama.comm.bee.service.ord.IOrdUserOrderService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vst.service.VstOrdOrderService;

/**
 * @author lancey
 *
 */
public class MySpaceBaseOrderAction extends SpaceBaseAction{

	protected IOrdUserOrderService ordUserOrderService;
	
	protected OrderService orderServiceProxy;
	
	protected VstOrdOrderService vstOrdOrderService;
	
	/**
	 * 根据用户ID获取用户订单列表
	 * 
	 * @return
	 */
	protected List<OrdUserOrder> queryUserOrderList(Page pageConfig) {
		List<OrdUserOrder> ordUserOrderList = null;
		if (getUserId() != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", getUser().getId());
			long startIndex = pageConfig.getStartRows();
			long endIndex = pageConfig.getStartRows() - 1 + pageConfig.getPageSize();
			params.put("start", startIndex);
			params.put("end", endIndex);
			params.put("orderby", "CREATE_TIME");
			params.put("order", "desc");
			
			ordUserOrderList = ordUserOrderService.queryOrdUserOrderListByParams(params);
		}
		
		return ordUserOrderList;
	}
	
	/**
	 * 将OrdUserOrder列表按BEE订单对象和VST订单对象分离
	 * 
	 * @param ordUserOrderList
	 * @param beeOrderList
	 * @param vstOrderList
	 */
	protected void separateBeaAndVstOrder2List(List<OrdUserOrder> ordUserOrderList, List<OrdUserOrder> beeOrderList, List<OrdUserOrder> vstOrderList) {
		if (ordUserOrderList != null && ordUserOrderList.size() > 0) {
			for (OrdUserOrder userOrder : ordUserOrderList) {
				if (userOrder != null) {
					if (OrdUserOrder.BIZ_TYPE.BIZ_BEE.name().equals(userOrder.getBizType())) {
						if (beeOrderList != null) { 
							beeOrderList.add(userOrder);
						}
					} else if (OrdUserOrder.BIZ_TYPE.BIZ_VST.name().equals(userOrder.getBizType())) {
						if (vstOrderList != null) { 
							vstOrderList.add(userOrder);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 将VST系统的订单对象置入OrdUserOrder对象中。
	 * 
	 * @param beeOrderList
	 */
	protected void fillBeeOrderByOrdUserOrder(List<OrdUserOrder> beeOrderList) {
		if (beeOrderList != null && beeOrderList.size() > 0) {
			List<Long> orderIdList = new ArrayList<Long>();
			for (OrdUserOrder userOrder : beeOrderList) {
				if (userOrder != null && userOrder.getOrderId() != null) {
					orderIdList.add(userOrder.getOrderId());
				}
			}
			
			if (orderIdList.size() > 0) {
				CompositeQuery compositeQuery = new CompositeQuery();
				compositeQuery.getQueryFlag().setQuerySupplier(false);
				OrderIdentity orderIdentity = new OrderIdentity();
				orderIdentity.setOrderIds(orderIdList);
				compositeQuery.setOrderIdentity(orderIdentity);
				compositeQuery.getPageIndex().setEndIndex(orderIdList.size() + 1);
				
				List<OrdOrder> orderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
				if (orderList != null && orderList.size() > 0) {
					for (OrdOrder order : orderList) {
						if (order != null) {
							OrdUserOrder userOrder = findOrdUserOrderByOrderIdFromList(beeOrderList, order.getOrderId(), OrdUserOrder.BIZ_TYPE.BIZ_BEE.name());
							if (userOrder != null) {
								userOrder.setOrder(order);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据老系统和新系统的订单对象ID查找OrdUserOrder对象
	 * 
	 * @param userOrderList
	 * @param orderId
	 * @param bizType
	 * @return
	 */
	private OrdUserOrder findOrdUserOrderByOrderIdFromList(List<OrdUserOrder> userOrderList, Long orderId, String bizType) {
		OrdUserOrder retUserOrder = null;
		if (orderId != null && bizType != null && userOrderList != null && userOrderList.size() > 0) {
			for (OrdUserOrder userOrder : userOrderList) {
				if (userOrder != null) {
					if(orderId.equals(userOrder.getOrderId()) && bizType.equals(userOrder.getBizType())) {
						retUserOrder = userOrder;
						break;
					}
				}
			}
		}
		
		return retUserOrder;
	}
	
	/**
	 * 将VST系统的订单对象置入OrdUserOrder对象中。
	 * 
	 * @param vstOrderList
	 */
	protected void fillVstOrderByOrdUserOrder(List<OrdUserOrder> vstOrderList) {
		if (vstOrderList != null && vstOrderList.size() > 0) {
			List<Long> orderIdList = new ArrayList<Long>();
			for (OrdUserOrder userOrder : vstOrderList) {
				if (userOrder != null && userOrder.getOrderId() != null) {
					orderIdList.add(userOrder.getOrderId());
				}
			}
			
			if (orderIdList.size() > 0) {
				List<Map<String, Object>> orderMapList = vstOrdOrderService.queryVstOrdorderByOrderIdList(orderIdList);
				if (orderMapList != null && orderMapList.size() > 0) {
					for (Map<String, Object> orderMap : orderMapList) {
						if (orderMap != null) {
							OrdUserOrder userOrder = findOrdUserOrderByOrderIdFromList(vstOrderList, (Long)orderMap.get("orderId"), OrdUserOrder.BIZ_TYPE.BIZ_VST.name());
							if (userOrder != null) {
								userOrder.setVstOrder(orderMap);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据OrdUserOrder列表获取老系统的OrdOrder列表
	 * 
	 * @param userOrderList
	 * @return
	 */
	protected List<OrdOrder> getBeeOrdOrderListFromUserOrderList(List<OrdUserOrder> userOrderList) {
		List<OrdOrder> beeOrderList = Collections.emptyList();
		if (userOrderList != null && userOrderList.size() > 0) {
			beeOrderList = new ArrayList<OrdOrder>();
			for (OrdUserOrder userOrder: userOrderList) {
				if (userOrder != null) {
					if (OrdUserOrder.BIZ_TYPE.BIZ_BEE.name().equals(userOrder.getBizType())
							&& userOrder.getOrder() != null) {
						beeOrderList.add(userOrder.getOrder());
					}
				}
			}
		}
		
		return beeOrderList;
	}

	public void setOrdUserOrderService(IOrdUserOrderService ordUserOrderService) {
		this.ordUserOrderService = ordUserOrderService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}
	
	
}
