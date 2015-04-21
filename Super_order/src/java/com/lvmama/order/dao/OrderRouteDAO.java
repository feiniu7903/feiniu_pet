package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.OrdOrderRoute;

/**
 * 订单关于团的附加表OrderRoute实现类.
 * @author liwenzhan
 * @version 1.0 2011-9-20
 * 
 */
public interface OrderRouteDAO {

	/**
	 * 增加订单关于团的附加表记录.
	 * @param record
	 * @return
	 */
	Long insertRoute(OrdOrderRoute record);

	/**
	 * 修改订单关于团的附加表记录.
	 * @param record
	 * @return
	 */
	int updateRoute(OrdOrderRoute record);
	
	
	/**
	 * 根据订单ID查询订单团附加表.
	 * 
	 * @param orderId
	 *            订单ID.
	 * @return OrdOrderRoute 订单团附加表.
	 */
	 OrdOrderRoute queryOrdOrderRouteByOrderId(final Long orderId);

}