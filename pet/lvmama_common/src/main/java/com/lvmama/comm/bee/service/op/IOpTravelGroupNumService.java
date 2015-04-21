/**
 * 
 */
package com.lvmama.comm.bee.service.op;

import com.lvmama.comm.bee.po.ord.OrdOrder;

/**
 * @author yangbin
 *
 */
public interface IOpTravelGroupNumService {

	/**
	 * 更新未支付的人数.
	 * @param order
	 */
	void updateGroupPayNot(OrdOrder order);
	
	/**
	 * 更新团的部分支付人数.
	 * @param order
	 */
	void updateGroupPayPart(OrdOrder order);
	/**
	 * 取消订单时根据订单去回退订单当中的人数.
	 * @param order
	 */
	void rollbackGroupNum(OrdOrder order);

	/**
	 * 取出订单当中路线当中对应团的人数
	 * @param order 操作的订单实体
	 * @return 对应的人数，如果不是团订单返回0;
	 */
	Long getOrderQuantity(final OrdOrder order);
}
