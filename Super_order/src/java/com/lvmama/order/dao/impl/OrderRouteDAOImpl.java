package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.order.dao.OrderRouteDAO;

/**
 * 订单关于团的附加表OrderRoute实现类.
 * 
 * @author liwenzhan
 * @version Super一期 11/09/11
 * @since Super一期
 * @see com.lvmama.BaseIbatisDao
 */
public final class OrderRouteDAOImpl extends BaseIbatisDAO implements
		OrderRouteDAO {
	/**
	 * 增加订单关于团的附加表记录.
	 */
	@Override
	public Long insertRoute(final OrdOrderRoute record) {
		Object routeKey = super.insert(
				"ORD_ORDER_ROUTE.insert", record);
		return (Long) routeKey;
	}

	/**
	 * 修改订单关于团的附加表记录.
	 */
	@Override
	public int updateRoute(final OrdOrderRoute record) {
		int rows = super.update(
				"ORD_ORDER_ROUTE.updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * 根据订单ID查询订单团附加表.
	 * 
	 * @param orderId
	 *            订单ID.
	 * @return OrdOrderRoute 订单团附加表.
	 */
	public OrdOrderRoute queryOrdOrderRouteByOrderId(final Long orderId) {
		return (OrdOrderRoute) super.queryForObject(
				"ORD_ORDER_ROUTE.queryOrdOrderRoute", orderId);
	}
}
