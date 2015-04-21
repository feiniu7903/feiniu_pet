package com.lvmama.order.service.impl;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.order.service.IProductOrder;

/**
 * 其他销售产品订单创建服务.
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 * @see com.lvmama.order.po.OrderInfoDTO
 * 
 */
public final class OtherBuildImpl implements IProductOrder {
	/**
	 * 修改销售产品订单子项.
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 * @return 销售产品订单子项{@link OrdOrderItemProd}
	 */
	@Override
	public OrdOrderItemProd modifyOrderInfo(final OrdOrder order,
			final OrdOrderItemProd ordOrderItemProd) {
		// 产品订单应付金额，为酒店单房型计算订单总金额使用
		ordOrderItemProd.setOughtPay(ordOrderItemProd.getPrice()
				* ordOrderItemProd.getQuantity());
		return ordOrderItemProd;
	}

	/**
	 * 保存额外数据.<br>
	 * 需要进行额外CRUD操作时所要实现的方法
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 * @return boolean true代表保存额外数据成功，false代表保存额外数据失败
	 */
	@Override
	public boolean saveAdditionData(final OrdOrderItemProd ordOrderItemProd) {
		return true;
	}

	/**
	 * 修改采购产品订单子项.
	 * 
	 * @param ordOrderItemMeta
	 *            采购产品订单子项
	 * @return 采购产品订单子项{@link OrdOrderItemMeta}
	 */
	@Override
	public OrdOrderItemMeta modifyOrdOrderItemMeta(
			final OrdOrderItemMeta ordOrderItemMeta) {
		return ordOrderItemMeta;
	}
}
