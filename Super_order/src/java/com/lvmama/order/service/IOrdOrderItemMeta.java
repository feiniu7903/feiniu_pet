package com.lvmama.order.service;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;

/**
 * 采购产品订单子项层级接口.
 * 
 * <pre>
 * </pre>
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 */
public interface IOrdOrderItemMeta {
	/**
	 * 修改采购产品订单子项.
	 * 
	 * @param ordOrderItemMeta
	 *            采购产品订单子项
	 * @return 采购产品订单子项{@link OrdOrderItemMeta}
	 */
	OrdOrderItemMeta modifyOrdOrderItemMeta(OrdOrderItemMeta ordOrderItemMeta);
}
