/**
 * 
 */
package com.lvmama.order.service.impl;

import static com.lvmama.comm.utils.UtilityTool.isNotNull;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.service.IProductOrder;

/**
 * @author yangbin
 *
 */
public class TrafficBuildImpl implements IProductOrder {
	
	private OrderItemMetaDAO orderItemMetaDAO;

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.IOrdOrderItemMeta#modifyOrdOrderItemMeta(com.lvmama.comm.bee.po.ord.OrdOrderItemMeta)
	 */
	@Override
	public OrdOrderItemMeta modifyOrdOrderItemMeta(
			OrdOrderItemMeta ordOrderItemMeta) {
		return ordOrderItemMeta;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.IProductOrder#modifyOrderInfo(com.lvmama.comm.bee.po.ord.OrdOrder, com.lvmama.comm.bee.po.ord.OrdOrderItemProd)
	 */
	@Override
	public OrdOrderItemProd modifyOrderInfo(OrdOrder order,
			OrdOrderItemProd ordOrderItemProd) {
		ordOrderItemProd.setOughtPay(ordOrderItemProd.getPrice()
				* ordOrderItemProd.getQuantity());
		return ordOrderItemProd;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.order.service.IProductOrder#saveAdditionData(com.lvmama.comm.bee.po.ord.OrdOrderItemProd)
	 */
	@Override
	public boolean saveAdditionData(OrdOrderItemProd ordOrderItemProd) {
		// 酒店单房型订单
		if (isNotNull(ordOrderItemProd.getOrdOrderItemMetas())) {
			for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemProd.getOrdOrderItemMetas()) {
				OrdOrderItemMeta  newOrdOrderItemMeta=this.modifyOrdOrderItemMeta(ordOrderItemMeta);
				orderItemMetaDAO.updateByPrimaryKey(newOrdOrderItemMeta);
			}
		}
		return true;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

}
