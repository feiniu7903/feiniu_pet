package com.lvmama.order.service;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;

/**
 * 销售产品订单子项层级接口.
 * 
 * <pre>
 * </pre>
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 */
public interface IProductOrder extends IOrdOrderItemMeta {
	/**
	 * 修改销售产品订单子项.
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 * @return 销售产品订单子项{@link OrdOrderItemProd}
	 */
	OrdOrderItemProd modifyOrderInfo(final OrdOrder order,OrdOrderItemProd ordOrderItemProd);

	/**
	 * 保存额外数据.<br>
	 * 需要进行额外CRUD操作时所要实现的方法
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 * @return boolean true代表保存额外数据成功，false代表保存额外数据失败
	 */
	boolean saveAdditionData(OrdOrderItemProd ordOrderItemProd);
}
