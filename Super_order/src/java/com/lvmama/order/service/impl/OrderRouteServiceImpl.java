package com.lvmama.order.service.impl;


import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderRouteDAO;
import com.lvmama.order.service.OrderRouteService;

/**
 * 订单关于团的附加表OrderRoute实现类.
 * @author liwenzhan.
 * @version 1.0 2011-9-23
 * @see com.lvmama.ord.po.OrdOrderRoute;
 * @see com.lvmama.order.dao.OrderRouteDAO;
 */
public class OrderRouteServiceImpl extends OrderServiceImpl implements OrderRouteService {
	/**
	 * 订单附加表属性DAO.
	 */
	protected OrderRouteDAO orderRouteDAO;
	
	
	/**
	 * 根据订单ID查询订单团附加表.
	 * 
	 * @param orderId
	 *            订单ID.
	 * @return OrdOrderRoute 订单团附加表.
	 */
	public OrdOrderRoute queryOrdOrderRouteByOrderId(final Long orderId){
		return this.orderRouteDAO.queryOrdOrderRouteByOrderId(orderId);
	}
	
	/**
	 * 增加订单关于团的附加表记录.
	 * @param orderRoute
	 */
	@Override
	public boolean insertOrderRoute(final OrdOrderRoute orderRoute) {
		Long row = orderRouteDAO.insertRoute(orderRoute);
		if(row == 1){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 修改订单关于团的附加表记录.
	 * @param orderRoute
	 * @return 
	 */
	@Override
	public boolean updateOrderRoute(final OrdOrderRoute orderRoute) {
		int row = orderRouteDAO.updateRoute(orderRoute);
		if(row == 1){
			insertLog(orderRoute.getOrderRouteId(), "ORD_ORDER_ROUTE", orderRoute.getOrderId(), "ORD_ORDER", "", 
					"修改订单内容", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), 
					"修改订单团附加表OrderRoute内容,状态为" + Constant.ECONTRACT_STATUS.CONFIRM.name());
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 获取订单附加表属性DAO.
	 * @return
	 */
	@Override
	public OrderRouteDAO getOrderRouteDAO() {
		return orderRouteDAO;
	}
	/**
	 * 设置订单附加表属性DAO.
	 * @param orderRouteDAO
	 */
	@Override
	public void setOrderRouteDAO(OrderRouteDAO orderRouteDAO) {
		this.orderRouteDAO = orderRouteDAO;
	}
}
