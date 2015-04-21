package com.lvmama.order.service;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.vo.ord.BuyInfo;

/**
 * 订单创建服务.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.service.po.BuyInfo
 * @see com.lvmama.ord.service.po.Person
 */
public interface OrderCreateService {
	/**
	 * 创建订单.
	 *
	 * <pre>
	 * 前台下单时使用
	 * </pre>
	 *
	 * @param buyInfo
	 *            购买信息
	 *
	 * @return 创建的订单
	 */
	OrdOrder createOrder(BuyInfo buyInfo);

	/**
	 * 创建订单.
	 *
	 * <pre>
	 * 后台下单时使用
	 * </pre>
	 *
	 * @param buyInfo
	 *            购买信息
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 创建的订单
	 */
	OrdOrder createOrder(BuyInfo buyInfo, String operatorId);

	/**
	 * 创建订单.
	 *
	 * <pre>
	 * 后台废单重下时使用
	 * </pre>
	 *
	 * @param buyInfo
	 *            购买信息
	 * @param orderId
	 *            原订单ID
	 * @param operatorId
	 *            操作人ID
	 *
	 * @return 创建的订单
	 */
	OrdOrder createOrder(BuyInfo buyInfo, Long orderId,
			String operatorId);

//	boolean isContainPassport(Long metaProductId);
}
