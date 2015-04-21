package com.lvmama.comm.bee.service.ord;

import java.util.Map;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.PriceInfo;

public interface OrderPriceService {

	/**
	 * 订单价格计算.
	 *
	 * <pre>
	 * 前后台下单时使用
	 * </pre>
	 *
	 * @param buyInfo
	 *            购买信息
	 *
	 * @return PriceInfo
	 */
	PriceInfo countPrice(BuyInfo buyInfo);
	
	/**
	 * 查询订单总金额.
	 *
	 * @param params
	 *            用户ID，订单状态
	 * @return 总金额
	 * 
	 */
	public float queryOrdersAmountByParams(Map<String, Object> params);
}
