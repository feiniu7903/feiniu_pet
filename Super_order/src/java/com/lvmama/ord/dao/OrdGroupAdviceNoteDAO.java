package com.lvmama.ord.dao;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;

public class OrdGroupAdviceNoteDAO extends BaseIbatisDAO{
	/**
	 * 根据订单号查询销售产品
	 */
	public OrdOrderItemProd getOrderItemProdByOrderId(Long orderId){
		return (OrdOrderItemProd)super.queryForObject("ORD_GROUP_ADVICE_NOTE.getOrderItemProdByOrderId",orderId);
	}
	/**
	 * 更据订单号查询出团通知书状态
	 */
	public OrdOrderRoute getOrderRouteByOrderId(Long orderId){
		return (OrdOrderRoute)super.queryForObject("ORD_GROUP_ADVICE_NOTE.getOrderRouteByOrderId",orderId);
	}
	
	/**
	 * 更新出团通知书状态
	 */
	public void updateOrderGroupWordStatus(Long orderId, String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("status", status);
		super.update("ORD_GROUP_ADVICE_NOTE.updateOrderGroupWordStatus",map);
	}
}
