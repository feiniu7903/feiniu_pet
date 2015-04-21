package com.lvmama.order.service;

import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;

/**
 * 创建产品订单工厂接口.
 * 
 * <pre>
 * </pre>
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 */
public interface IBuildOrderFactory {
	/**
	 * 选择订单服务实现.
	 * 
	 * @param productType
	 *            销售产品类型
	 * @return 产品订单{@link IProductOrder}
	 */
	IProductOrder chooseOrderServiceImpl(final OrdOrderItemProd orderItemProd);
}
